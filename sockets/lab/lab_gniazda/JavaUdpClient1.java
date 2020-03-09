import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class JavaUdpClient1 {

    public static void main(String args[]) throws Exception
    {
        System.out.println("JAVA UDP CLIENT");
        DatagramSocket socket = null;
        int portNumber = 9008;

        try {
            socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");
            Scanner sc = new Scanner();
            byte[] sendBuffer = sc..getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
            socket.send(sendPacket);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}