package TcpClockMultithread;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Luan Henrique Campos
 * @date 20/03/2026
 * @summary Servidor TCP Concorrente (Multithread) para sistema de Relógio Mundial.
 */

public class TcpMultiServer {
    public static void main(String[] args) {
        int port = 5002;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor TCP (Multithread) iniciado na porta " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket);
                Thread thread = new Thread(handler);
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}