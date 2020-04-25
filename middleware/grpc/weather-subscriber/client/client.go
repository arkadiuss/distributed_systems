package main

import (
	ws "client/grpc/weatherservice"
	"context"
	"google.golang.org/grpc"
	"log"
)

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
	stream, err := client.Subscribe(ctx, &ws.SubscribeRequest{City:"test"})
	if err != nil {
		log.Fatalf("fail to subscribe: %v", err)
	}
	for {
		notification, err := stream.Recv()
		if err != nil {
			log.Fatalf("fail to recv: %v", err)
		}
		log.Printf("received message: %v \n", notification.Message)
	}
}