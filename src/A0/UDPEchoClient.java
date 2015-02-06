package A0;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 
 * @author Andrew Rodriguez
 * 1: Create a DatagramSocket Object
 * 2: Create the outgoing datagram
 * 3: Send the datagram message
 * 4: Create a buffer for incoming datagrams
 * 5: Create a datagramPacket object for incoming datagrams
 * 6: Accept an incoming datagram
 * 7: Retrieve the data from the buffer
 * 8: Close the Datagram Socket
 *
 */
public class UDPEchoClient {
	private static InetAddress host;
	private static final int PORT = 1234;
	private static DatagramSocket datagramSocket;
	private static DatagramPacket inPacket, outPacket;
	private static byte[] buffer;
	
	public static void main(String[] args) {
		try {
			host = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("Host not found");
			System.exit(1);
		}
		accessServer();
	}
	
	private static void accessServer() {
		try {
			//1: Create a Datagram object
			datagramSocket = new DatagramSocket();
			Scanner userEntry = new Scanner(System.in);
			String message = "";
			String response = "";
			
			do {
				System.out.println("Enter message: ");
				message = userEntry.nextLine();
				if(!message.equals("CLOSE")) {
					//2: Create the outgoing datagram
					outPacket = new DatagramPacket(message.getBytes(), message.length(), host,PORT);
					//3: Send the datagram message
					datagramSocket.send(outPacket);
					//4: Create a buffer for incoming datagram
					buffer = new byte[256];
					//5: Create a datagramPacket object for incoming datagrams
					inPacket = new DatagramPacket(buffer, buffer.length);
					//6: Accept an incoming datagram
					datagramSocket.receive(inPacket);
					//7: Retrieve the data from the buffer
					response = new String(inPacket.getData(), 0, inPacket.getLength());
					System.out.println("\nServer: "+response);
				} 
			} while(!message.equals("CLOSE"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		finally {
			System.out.println("Closing connection");
			//8: Close the Datagram Socket
			datagramSocket.close();
		}
	}
}