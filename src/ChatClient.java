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
    }

    private class TFListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String str = tf.getText();
            ta.setText(str);
            tf.setText("");
        }
    }
    public void connect() throws IOException {
        Socket s = new Socket("192.168.203.1", 8888);
        System.out.println("connected");
        DataOutputStream dos=new DataOutputStream(s.getOutputStream());
        dos.writeUTF("Hello!");
    }
    public static void main(String[] args) throws IOException {
        new ChatClient().launchFrame();
    }
}
