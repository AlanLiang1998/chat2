import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private ServerSocket ss = null;
    private List<Client> clients = new ArrayList<>();
    List<String> uernames = new ArrayList<>();

    public static void main(String[] args) {
        new ChatServer().start();
    }

    private void start() {
        try {
            ss = new ServerSocket(8888);
        } catch (IOException e) {
            System.out.println("连接建立失败！");
        }
        while (true) {
            Socket s = null;
            try {
                s = ss.accept();
                Client c = new Client(s);
                /*String str = c.dis.readUTF();
                if (judgeUsername(str))
                    c.setUername(str);*/
                clients.add(c);
                new Thread(c).start();
            } catch (IOException e) {
                System.out.println("客户端连接失败！");
            }
            System.out.println("有一个客户端连接上了！");
        }
    }

   /* public boolean judgeUsername(String str) {
        for (int i = 0; i < uernames.size(); i++) {
            String uername = uernames.get(i);
            if (str.equals(uername))
                return false;
        }
        return true;
    }*/

    private class Client implements Runnable {
        //String uername = null;
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;

        public Client(Socket s) {
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
            } catch (IOException e) {
                System.out.println("流错误！");
            }
        }
/*
        public String getUername() {
            return uername;
        }

        public void setUername(String uername) {
            this.uername = uername;
        }*/

        public void sendAll(String str) {
            for (int i = 0; i < clients.size(); i++) {
                Client c = clients.get(i);
                try {
                    c.dos.writeUTF(str);
                } catch (IOException e) {
                    System.out.println("写出错误！");
                }
            }
        }

        public void run() {
            while (true) {
                try {
                    String str = dis.readUTF();
                    System.out.println(str);
                    sendAll(str);
                } catch (SocketException e) {
                    System.out.println("有一个客户端关闭了！");
                    clients.remove(this);
                    break;
                } catch (IOException e) {
                    System.out.println("读入错误");
                }
            }
        }
    }

}
