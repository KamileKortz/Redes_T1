import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.text.SimpleDateFormat;

public class Server {

    // lista com os canais de saída de todos os clientes conectados, pra mandar
    // mensagem pra todos)
    private static List<OutputStream> ouvintes = new ArrayList<>();

    // Controla o envio de mensagens, só um jogador pode mandar mensagem por vez
    private static Semaphore semaforo = new Semaphore(1);

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(9002);

        // Fica esperando novos clientes. Cria uma thread separada pra cada cliente que
        // entra
        while (true) {
            Socket conn = server.accept();
            new Thread(() -> {
                try {
                    InputStream in = conn.getInputStream();
                    OutputStream out = conn.getOutputStream();
                    String ip = conn.getInetAddress().getHostAddress();

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
                } catch (Exception e) {
                }
            }).start();
        }
    }
}