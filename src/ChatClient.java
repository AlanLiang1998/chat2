import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClient extends Frame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    private TextField tf = new TextField();
    private TextArea ta = new TextArea();
    private Socket s = null;
    private DataOutputStream dos = null;
    private DataInputStream dis = null;
    String uername = null;

    private void launchFrame() {
        setTitle("多人聊天系统");
        setLocation(100, 100);
        setSize(WIDTH, HEIGHT);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
                disconnect();
            }
        });
        ta.setFont(new Font("宋体", Font.PLAIN, 24));
        tf.setFont(new Font("宋体", Font.PLAIN, 24));
        add(ta, BorderLayout.NORTH);
        add(tf, BorderLayout.SOUTH);
        tf.addActionListener(new TFListener());
        setVisible(true);
        connect();
        register();
        new Thread(new RecevieThread()).start();
    }

    public void register() {
        String str = JOptionPane.showInputDialog("请输入用户名：");
        if (str != null)
            this.uername = str;
        else
            this.uername = "user";
      /*  try {
            dos.writeUTF(str);
        } catch (IOException e) {
            System.out.println("写出失败");
        }*/
    }

    private void disconnect() {
        try {
            dos.close();
            dis.close();
            s.close();
        } catch (IOException e) {
            System.out.println("客户端关闭失败！");
        }
    }

    private class RecevieThread implements Runnable {

        public void run() {
            while (true) {
                try {
                    String str = dis.readUTF();
                    ta.setText(ta.getText() + str + "\n");
                } catch (IOException e) {
                    System.out.println("读入错误！");
                }

            }
        }
    }

    private void connect() {
        try {
            s = new Socket("192.168.203.1", 8888);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
        } catch (IOException e) {
            System.out.println("连接失败！");
        }
        System.out.println("连接上服务器了！");
    }

    private class TFListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String str = tf.getText();
            tf.setText("");
            //int port = s.getLocalPort();
            try {
                dos.writeUTF(uername + ": " + str);
            } catch (IOException e1) {
                System.out.println("写出错误！");
            }
        }
    }

    public static void main(String[] args) {
        new ChatClient().launchFrame();
    }
}
