import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z2_LocalActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(Z2_Response.class, res -> {
                    System.out.println("Received response: "+res.responseText);
                })
                .match(String.class, msg -> {
                    System.out.println("Input message: "+msg);
                    context().actorSelection("akka://remote_system@127.0.0.1:2552/user/remote")
                        .tell(new Z2_Message(msg, self().path().toString()), getSelf());
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
