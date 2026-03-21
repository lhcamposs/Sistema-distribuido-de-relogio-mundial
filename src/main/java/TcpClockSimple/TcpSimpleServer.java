package TcpClockSimple;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Luan Henrique Campos
 * @date 20/03/2026
 * @summary Servidor TCP sequencial (Single-Thread) para sistema de Relógio Mundial.
 */

public class TcpSimpleServer {
    public static void main(String[] args) {
        int port = 5001;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor TCP (Single-Thread) iniciado na porta " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String regionId = in.readLine();
                    if (regionId != null) {
                        out.println(getTimeForRegion(regionId.trim()));
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao processar cliente: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTimeForRegion(String regionId) {
        try {
            ZoneId zone = ZoneId.of(regionId);
            ZonedDateTime time = ZonedDateTime.now(zone);
            return time.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss z"));
        } catch (Exception e) {
            return "Erro: Região inválida (" + regionId + ")";
        }
    }
}
