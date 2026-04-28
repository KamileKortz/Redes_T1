import java.io.*;
import java.net.*;

public class ClientRecebimento {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("127.0.0.1", 9002);

        // Envia "Ouvinte-Tela" como nome quando o cliente conecta
        s.getOutputStream().write("Ouvinte-Tela".getBytes());

        InputStream in = s.getInputStream(); // Serve pra receber dados vindos do servidor
        byte[] buf = new byte[1024]; // Cria um buffer (espaço na memória para armazenar os dados recebidos)
        int n; // Variável usada pra guardar quantos bytes foram lidos do servidor

        // Recebe todas as mensagens do chat
        while ((n = in.read(buf)) != -1) { // in.read(buf) lê os bytes recebidos e coloca dentro do buffer buf
            System.out.println(new String(buf, 0, n)); // Transforma os bytes recebidos em texto
        }
    }
}
