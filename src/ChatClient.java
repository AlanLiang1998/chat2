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
    private TextField tf = new TextField();
    private TextArea ta = new TextArea();
    private Socket s = null;
    private DataOutputStream dos = null;
    private DataInputStream dis = null;

    private void launchFrame() {
        setTitle("chat");
        setLocation(100, 100);
        setSize(600, 400);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        ta.setFont(new Font("宋体", Font.PLAIN, 24));
        tf.setFont(new Font("宋体", Font.PLAIN, 24));
        add(ta, BorderLayout.NORTH);
        add(tf, BorderLayout.SOUTH);
        tf.addActionListener(new TFListener());
        setVisible(true);
        connect();
        new Thread(new RecevieThread()).start();
    }

    private class TFListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String str = tf.getText();
            tf.setText("");
            try {
                dos.writeUTF(str);
            } catch (IOException e1) {
                System.out.println("写出错误！");
            }
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

    public static void main(String[] args) {
        new ChatClient().launchFrame();
    }
}
