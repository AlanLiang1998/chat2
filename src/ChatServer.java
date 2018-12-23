import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8888);
        Socket s = ss.accept();
        System.out.println("A client connected!");
        DataInputStream dis = new DataInputStream(s.getInputStream());
        while (true) {
            try {
                String str = dis.readUTF();
                System.out.println(str);
            }catch (SocketException e){
                System.out.println("A client closed");
                break;
            }
        }
    }
}
