import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

public class ChatClient extends Frame {
    public void launchFrame() {
        setTitle("chat");
        setLocation(100, 100);
        setSize(800, 600);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) throws IOException {
        new ChatClient().launchFrame();
        Socket s=new Socket("192.168.203.1",8888);
        System.out.println("connected!");
    }
}
