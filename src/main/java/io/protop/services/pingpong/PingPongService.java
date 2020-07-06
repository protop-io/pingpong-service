package io.protop.services.pingpong;

import io.grpc.stub.StreamObserver;
import protop.pingpong.Api;
import protop.pingpong.PingPongServiceGrpc;

import java.util.concurrent.atomic.AtomicInteger;

public class PingPongService extends PingPongServiceGrpc.PingPongServiceImplBase {

    @Override
    public StreamObserver<Api.Ping> exchange(StreamObserver<Api.Pong> responseObserver) {
        System.out.println("Connection opened.");
        AtomicInteger count = new AtomicInteger();

        return new StreamObserver<Api.Ping>() {
            @Override
            public void onNext(Api.Ping value) {
                System.out.println("Got ping #" + count.incrementAndGet());
                responseObserver.onNext(Api.Pong.newBuilder().build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error: " + t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
