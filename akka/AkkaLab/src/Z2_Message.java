import java.io.Serializable;

public class Z2_Message implements Serializable {
    public final String message;
    public final String senderPath;

    public Z2_Message(String message, String senderPath) {
        this.message = message;
        this.senderPath = senderPath;
    }
}
