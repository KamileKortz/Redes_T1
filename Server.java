import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.text.SimpleDateFormat;

public class Server {
    private static List<OutputStream> ouvintes = new ArrayList<>();
    private static Semaphore semaforo = new Semaphore(1);

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(9002);

        while (true) {
            Socket conn = server.accept();
            new Thread(() -> {
                try {
                    InputStream in = conn.getInputStream();
                    OutputStream out = conn.getOutputStream();
                    String ip = conn.getInetAddress().getHostAddress();

                    byte[] buf = new byte[1024];

                    int n = in.read(buf);
                    String nome = new String(buf, 0, n);

                    semaforo.acquire();
                    ouvintes.add(out);
                    semaforo.release();

                    while ((n = in.read(buf)) != -1) {
                        String texto = new String(buf, 0, n);
                        String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());

                        System.out.println("[" + hora + "] " + nome + "(" + ip + "): " + texto);

                        semaforo.acquire();
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