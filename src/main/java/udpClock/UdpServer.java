package udpClock;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class UdpServer {
    public static void main(String[] args) {
        int port = 5000;
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Servidor UDP iniciado na porta " + port);
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(requestPacket);

                String regionId = new String(requestPacket.getData(), 0, requestPacket.getLength()).trim();
                String response = getTimeForRegion(regionId);
                byte[] responseData = response.getBytes();

                InetAddress clientAddress = requestPacket.getAddress();
                int clientPort = requestPacket.getPort();
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);

                socket.send(responsePacket);
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
