package TcpClockMultithread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        String clientIP = clientSocket.getInetAddress().getHostAddress();
        int clientPort = clientSocket.getPort();

        System.out.println("Log -> Thread: " + threadName + " cuidando do cliente: " + clientIP + ":" + clientPort);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String regionId = in.readLine();
            if (regionId != null) {
                out.println(getTimeForRegion(regionId.trim()));
            }
        } catch (Exception e) {
            System.err.println("Erro na thread " + threadName + ": " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getTimeForRegion(String regionId) {
        try {
            ZoneId zone = ZoneId.of(regionId);
            ZonedDateTime time = ZonedDateTime.now(zone);
            return time.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss z"));
        } catch (Exception e) {
            return "Erro: Região inválida (" + regionId + ")";
        }
    }
}