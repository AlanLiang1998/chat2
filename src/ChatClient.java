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
    TextField tf = new TextField();
    TextArea ta = new TextArea();
    Socket s = null;
    DataOutputStream dos = null;
    DataInputStream dis = null;

    public void launchFrame() throws IOException {
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
            //ta.setText(str);
            tf.setText("");
            try {
                dos.writeUTF(str);
            } catch (IOException e1) {
                e1.printStackTrace();
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
                    e.printStackTrace();
                }

            }
        }
    }

    public void connect() throws IOException {
        s = new Socket("192.168.203.1", 8888);
        System.out.println("connected");
        dos = new DataOutputStream(s.getOutputStream());
        dis = new DataInputStream(s.getInputStream());
    }

    public static void main(String[] args) throws IOException {
        new ChatClient().launchFrame();
    }
}
