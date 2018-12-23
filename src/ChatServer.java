import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ChatServer {
    ServerSocket ss = null;

    public static void main(String[] args) throws IOException {
        new ChatServer().start();
    }

    public void start() throws IOException {
        ss = new ServerSocket(8888);
        while (true) {
            Socket s = ss.accept();
            Client c = new Client(s);
            new Thread(c).start();
            System.out.println("A client connected!");
        }
    }

    private class Client implements Runnable {
        Socket s = null;
        DataInputStream dis = null;

        public Client(Socket s) throws IOException {
            this.s = s;
            dis = new DataInputStream(s.getInputStream());
        }

        public void run() {
            while (true) {
                try {
                    String str = dis.readUTF();
                    System.out.println(str);
                } catch (SocketException e) {
                    System.out.println("A client closed");
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
