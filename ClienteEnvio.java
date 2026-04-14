import java.io.*;
import java.net.*;

public class ClienteEnvio {
    public static void main(String[] args) throws Exception {

        Socket s = new Socket("127.0.0.1", 9002);
        OutputStream out = s.getOutputStream();

        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Digite mensagens:");

        while (true) {
            String msg = teclado.readLine();
            out.write((msg + "\n").getBytes());
        }
    }
}