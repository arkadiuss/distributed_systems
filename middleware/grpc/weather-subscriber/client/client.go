package main

import (
	ws "client/grpc/weatherservice"
	"context"
	"fmt"
	"google.golang.org/grpc"
	"log"
	"math"
	"strings"
	"time"
)

type Backoff interface {
	nextVal() int
	reset()
}

type ExpBackoff struct {
	initTime int
	waitTime int
}

func (b * ExpBackoff) nextVal() int {
	b.waitTime = int(math.Min(1.5*float64(b.waitTime), 10000))
	return b.waitTime
}

func (b * ExpBackoff) reset() {
	b.waitTime = b.initTime
}

func inputListWithPrompt(name string, prompts []string) []string {
	fmt.Printf("%s (%s, all, none):\n", name, strings.Join(prompts, ","))
	var valsText string
	fmt.Scanf("%s", &valsText)
	if valsText == "all" {
		return prompts
	} else if valsText == "none" {
		return make([]string, 0)
	}
	return strings.Split(valsText, ",")
}

func inputRange(name string) *ws.Range {
	fmt.Printf("Please declare range of %s (x y): \n", name)
	var min, max int32
	fmt.Scanf("%d %d", &min, &max)
	return &ws.Range{
		Min: min,
		Max: max,
	}
}

func inputFactor(factors []string) *ws.Factor {
	fmt.Printf("Specify factor name (%s):\n", strings.Join(factors, ","))
	var factorName string
	fmt.Scanf("%s", &factorName)
	return &ws.Factor{
		Name:  factorName,
		Range: inputRange(factorName),
	}
}

func dataInput(availableCities []string, availableFactors []string) *ws.SubscribeRequest {
	cities := inputListWithPrompt( "Cities", availableCities)
	var factorsCount int
	var factors []*ws.Factor
	fmt.Printf("How many factors you want?\n")
	fmt.Scanf("%d", &factorsCount)
	println(factorsCount)
	for i := 0; i < factorsCount; i++ {
		factors = append(factors, inputFactor(availableFactors))
	}
	return &ws.SubscribeRequest{
		Cities:cities,
		Factors:factors,
	}
}

func wait(backoff Backoff) {
	time.Sleep(time.Duration(backoff.nextVal()) * time.Millisecond)
}

func subscribeWithRetries(client ws.WeatherServiceClient, ctx context.Context, request *ws.SubscribeRequest, backoff Backoff) {
	for {
		stream, err := client.Subscribe(ctx, request)
		if err != nil {
			log.Printf("fail to subscribe: %v \n", err)
			wait(backoff)
			continue
		}
		backoff.reset()
		for {
			notification, err := stream.Recv()
			if err != nil {
				log.Printf("fail to recv: %v", err)
				wait(backoff)
				break
			}
			log.Printf("received message: %v \n", notification.Message)
		}
	}
}

func main() {
	var opts []grpc.DialOption
	opts = append(opts, grpc.WithInsecure())
	opts = append(opts, grpc.WithBlock())
	conn, err := grpc.Dial("localhost:50051", opts...)
	if err != nil {
		log.Fatalf("fail to dial: %v", err)
	}
	defer conn.Close()
	client := ws.NewWeatherServiceClient(conn)
	ctx, _ := context.WithCancel(context.Background())

	cities, _ := client.GetCities(ctx, &ws.ValueRequest{})
	factors, _ := client.GetFactors(ctx, &ws.ValueRequest{})

	request := dataInput(cities.Values, factors.Values)

	subscribeWithRetries(client, ctx, request, &ExpBackoff{waitTime:100, initTime:100})
}