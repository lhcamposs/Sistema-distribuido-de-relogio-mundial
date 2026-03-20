package udpClock;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class UdpClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int port = 5000;

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            socket.setSoTimeout(5000);

            System.out.print("Digite o ID da região (ex: America/Sao_Paulo): ");
            String regionId = scanner.nextLine();
            byte[] requestData = regionId.getBytes();

            InetAddress address = InetAddress.getByName(serverAddress);
            DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, address, port);
            socket.send(requestPacket);

            byte[] buffer = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);

            socket.receive(responsePacket);
            String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
            System.out.println("Resposta do Servidor: " + response);

        } catch (SocketTimeoutException e) {
            System.out.println("Erro: Servidor ocupado ou offline.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
