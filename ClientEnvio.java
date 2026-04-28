import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientEnvio {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("127.0.0.1", 9002);
        OutputStream out = s.getOutputStream();
        Scanner LER = new Scanner(System.in);

        // Lê o nome do usuário e envia pro servidor
        System.out.print("Nome: ");
        String nome = LER.nextLine();
        out.write(nome.getBytes());

        // Envia o que o usuário digita pro servidor
        while (true) { 
            String msg = LER.nextLine();
            out.write(msg.getBytes());
        }
    }
}
