package A0;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.Random;
/**
 * Generates random characters of data and sends them to a device. When used with UDP, each UDP message sent to the server 
 * causes it to send back a UDP message containing a random number (0 to 512 bytes) of data. When used with TCP, the server 
 * just starts sending characters as soon as a client establishes a connection, and continues until the connection is terminated by the client.
 * 
 * @author Andrew Rodriguez
 * 
 * DOES EVERYTHING THE UDP ECHO DOES EXCEPT IT ECHOS BACK RANDOM CHARACTER
 * 1: Create a DatagramSocket Object
 * 2: Create a buffer for incoming datagrams
 * 3: Create a DatagramPacket object for the incoming datagrams;
 * 4: Accept an incoming datagram
 * 5: Accept the sender's address and port from the packet
 * 6: Retrieve the data from the buffer
 * 7: Create the response datagram
 * 8: Send the response datagram
 * 9: Close the Datagram Socket
 *
 */
public class UDPChargenServer {
	private static final int PORT = 1234;
	private static DatagramSocket datagramSocket;
	private static DatagramPacket inPacket, outPacket;
	private static byte[] buffer;
	private final static int MAX_RETURN_VALUE = 512;
	
	public static void main(String[] args) {
		System.out.println("Opening Port");
		try {
			//1: Create a DatagramSocket Object
			datagramSocket = new DatagramSocket(PORT);
		} catch(SocketException e) {
			System.out.println("Unable to attach to port!");
			System.exit(1);
		}
		handleClient();
	}
	
	private static void handleClient() {
		try {
			String messageIn, messageOut;
			int numMessages = 0;
			
			do {
				//2: Create a buffer for incoming datagrams
				buffer = new byte[256]; 
				// 3: Create a DatagramPacket object for the incoming datagrams;
				inPacket = new DatagramPacket(buffer, buffer.length);
				//4: Accept an incoming datagram
				datagramSocket.receive(inPacket);
				//5: Accept the sender's address and port from the packet
				InetAddress clientAddress = inPacket.getAddress();
				//5: Accept the sender's address and port from the packet
				int clientPort = inPacket.getPort();
				//6: Retrieve the data from the buffer
				messageIn = new String(inPacket.getData(),0,inPacket.getLength());
				System.out.println("Message Received");
				numMessages++;
				
				//picks random value from 0->512
				Random random = new Random();
				int returnValue = random.nextInt(MAX_RETURN_VALUE);
				messageOut = "Server UDPChargen> "+returnValue;
				//7: Create the response datagram
				outPacket = new DatagramPacket(messageOut.getBytes(), messageOut.length(), clientAddress, clientPort);
				//8: Send the response datagram
				datagramSocket.send(outPacket);
			} while(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		finally {
			System.out.println("Closing connection");
			//9: Close the Datagram Socket
			datagramSocket.close();
		}
	}
}