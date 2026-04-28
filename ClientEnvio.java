import java.io.*; // Importa classes do pacote java.io.*, usado para entrada e saída de daods (InputStream, OutputStream)
import java.net.*; // Importa classes do pacote java.net, usado para comunicação em rede (Sockets)
import java.util.Scanner; // Importa a classe Scanner, que serve para ler dados digitados pelo usuário no teclado.

public class ClientEnvio {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("127.0.0.1", 9002); // Cria um socket para se conectar a um servidor
        OutputStream out = s.getOutputStream(); // Serve pra enviar dados para o servidor
        Scanner LER = new Scanner(System.in);

        // Lê o nome do usuário e envia pro servidor
        System.out.print("Nome: ");
        String nome = LER.nextLine();
        out.write(nome.getBytes()); // Envia o nome para o servidor (nome.getBytes() trasforma o nome em Bytes, out.write envia os Bytes pela rede)

        // Envia o que o usuário digita pro servidor
        while (true) { // Esse while serve pro cliente poder ficar mandando várias mensagens pro servidor
            String msg = LER.nextLine();
            out.write(msg.getBytes());
        }
    }
}
