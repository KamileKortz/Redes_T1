import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {

    static List<Socket> clientes = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        ServerSocket servidor = new ServerSocket(9002);
        System.out.println("Servidor iniciado");

        while (true) {
            Socket conn = servidor.accept();
            clientes.add(conn);
            System.out.println("Novo cliente conectado");

            new Thread(() -> tratarCliente(conn)).start();
        }
    }

    public static void tratarCliente(Socket conn) {
        try {
            InputStream in = conn.getInputStream();
            byte[] buf = new byte[1024];

            while (true) {
                int n = in.read(buf);
                if (n == -1)
                    break;

                String msg = new String(buf, 0, n);
                System.out.println("Recebido: " + msg);

                for (Socket cliente : clientes) {
                    OutputStream out = cliente.getOutputStream();
                    out.write((msg + "\n").getBytes());
                }
            }
            conn.close();
            clientes.remove(conn);

        } catch (Exception e) {
            System.out.println("Erro com cliente");
        }
    }
}