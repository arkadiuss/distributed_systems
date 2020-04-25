package main

import (
	"context"
	"google.golang.org/grpc"
	"log"
	"time"

	ws "client/grpc/weatherservice"
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
	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)
	client.Subscribe(ctx, &ws.SubscribeRequest{Name:"test"})
}