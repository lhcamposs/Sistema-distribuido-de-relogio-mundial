package TcpClockSimple;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Luan Henrique Campos
 * @date 20/03/2026
 * @summary Cliente TCP que conecta ao servidor de Relógio Mundial, envia requisição e exibe resposta.
 */

public class TcpClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int port = 5001;

        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Digite o ID da região (ex: Europe/London): ");
            String regionId = scanner.nextLine();

            out.println(regionId);
            String response = in.readLine();
            System.out.println("Resposta do Servidor: " + response);

        } catch (Exception e) {
            System.out.println("Erro de conexão: " + e.getMessage());
        }
    }
}
