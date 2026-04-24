import java.io.*;
import java.net.*;

public class ClientRecebimento {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("127.0.0.1", 9002);

        s.getOutputStream().write("Ouvinte-Tela".getBytes());

        InputStream in = s.getInputStream();
        byte[] buf = new byte[1024];
        int n;

        while ((n = in.read(buf)) != -1) {
            System.out.println(new String(buf, 0, n));
        }
    }
}