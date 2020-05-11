import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z2_RemoteActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(Z2_Message.class, msg -> {
                    String responseText = msg.message.toUpperCase();
                    System.out.println("Received message: "+msg.message+". Uppercased: "+responseText);
                    context().actorSelection(sender().path())
                            .tell(new Z2_Response(responseText), getSelf());
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
