import java.io.*;
import java.net.*;

public class ClientRecebimento {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("127.0.0.1", 9002);

        // Envia "Ouvinte-Tela" como nome quando o cliente conecta
        s.getOutputStream().write("Ouvinte-Tela".getBytes());

        InputStream in = s.getInputStream();
        byte[] buf = new byte[1024];
        int n;
        
        // Recebe todas as mensagens do chat
        while ((n = in.read(buf)) != -1) { 
            System.out.println(new String(buf, 0, n));
        }
    }
}
