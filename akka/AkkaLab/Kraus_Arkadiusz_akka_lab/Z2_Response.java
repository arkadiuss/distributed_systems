import java.io.Serializable;

public class Z2_Response implements Serializable {
    public final String responseText;

    public Z2_Response(String responseText) {
        this.responseText = responseText;
    }
}
