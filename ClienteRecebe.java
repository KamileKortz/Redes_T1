import java.io.*;
import java.net.*;

public class ClienteRecebe {
    public static void main(String[] args) throws Exception {

        Socket s = new Socket("127.0.0.1", 9002);
        InputStream in = s.getInputStream();
        byte[] buf = new byte[1024];

        while (true) {
            int n = in.read(buf);
            if (n > 0) {
                System.out.print(new String(buf, 0, n));
            }
        }
    }
}