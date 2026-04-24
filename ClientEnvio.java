import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientEnvio {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("127.0.0.1", 9002);
        OutputStream out = s.getOutputStream();
        Scanner LER = new Scanner(System.in);

        System.out.print("Nome: ");
        String nome = LER.nextLine();
        out.write(nome.getBytes());

        while (true) {
            String msg = LER.nextLine();
            out.write(msg.getBytes());
        }
    }
}