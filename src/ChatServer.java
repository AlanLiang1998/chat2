import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    ServerSocket ss = null;
    List<Client> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        new ChatServer().start();
    }

    public void start() throws IOException {
        ss = new ServerSocket(8888);
        while (true) {
            Socket s = ss.accept();
            Client c = new Client(s);
            clients.add(c);
            new Thread(c).start();
            System.out.println("A client connected!");
        }
    }

    private class Client implements Runnable {
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;

        public Client(Socket s) throws IOException {
            this.s = s;
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
        }

        public void sendAll(String str) throws IOException {
            for (int i = 0; i < clients.size(); i++) {
                Client c = clients.get(i);
                c.dos.writeUTF(str);
            }
        }

        public void run() {
            while (true) {
                try {
                    String str = dis.readUTF();
                    System.out.println(str);
                    sendAll(str);
                } catch (SocketException e) {
                    System.out.println("A client closed");
                    clients.remove(this);
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
