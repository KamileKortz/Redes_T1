import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.Semaphore; // Importa a classe Semaphore, que serve pra controlar o acesso de várias threads a um mesmo recurso
import java.text.SimpleDateFormat; // Importa a classe SimpleDateFormat, que serve pra transformar datas em texto formatado

public class Server {

    // lista com os canais de saída de todos os clientes conectados, pra mandar
    // mensagem pra todos
    private static List<OutputStream> ouvintes = new ArrayList<>();

    // Controla o envio de mensagens, só um jogador pode mandar mensagem por vez
    private static Semaphore semaforo = new Semaphore(1);

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(9002);

        // Fica esperando novos clientes. Cria uma thread separada pra cada cliente que
        // entra
        while (true) {
            Socket conn = server.accept(); // server.accept() pausa o programa até alguém conectar, quando um cliente entra cria um Socket que fica guardado em conn
            new Thread(() -> { // Cria uma nova thread pro cliente (a thread permite vários clientes ao mesmo tempo)
                try { // Serve pra evitar que o servidor pare por causa de um "erro" do cliente
                    InputStream in = conn.getInputStream(); // Recebe dados enviados pelo cliente
                    OutputStream out = conn.getOutputStream(); // Envia dados pro o cliente
                    String ip = conn.getInetAddress().getHostAddress(); // Obtém o endereço IP do cliente conectado (conn.getInetAddress() pega o endereço da conexão; getHostAddress() converte para texto)

                    byte[] buf = new byte[1024];

                    // A primeira mensagem recebida é o nome do cliente
                    int n = in.read(buf);
                    String nome = new String(buf, 0, n);

                    // Adiciona o canal de saída desse cliente na lista ouvintes. O acquire() trava
                    // o semáforo e o release() abre
                    semaforo.acquire();
                    ouvintes.add(out);
                    semaforo.release();

                    // Fica escutando mensagens do cliente. Quando o cliente desconecta,
                    // in.read() retorna -1 e o loop encerra
                    while ((n = in.read(buf)) != -1) {
                        String texto = new String(buf, 0, n);
                        String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());

                        System.out.println("[" + hora + "] " + nome + "(" + ip + "): " + texto);

                        semaforo.acquire();

                        // Percorre todos os clientes conectados e envia a mensagem recebida para cada
                        // um deles
                        for (OutputStream ouvinte : ouvintes) {
                            ouvinte.write(("[" + hora + "] " + nome + "(" + ip + "): " + texto).getBytes());
                        }
                        semaforo.release();
                    }
                } catch (Exception e) { // Captura qualquer erro que aconteça dentro do try
                    // O bloco tá vazio, se acontecer erro, o programa só ignora
                }
            }).start(); // Inicia a execução da thread
        }
    }
}
