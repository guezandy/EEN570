package A0;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

/**
 * 
 * @author Andrew Rodriguez
 * accepts response from TCPDateClient and then echos the date back
 * Keeps track of how many messages sent
 * Closes on receiving of "CLOSE"
 * 1: Initialize ServerSocket Object
 * 2: Put the server into a waiting state
 * 3: Set up input and output streams
 * 4: Send and receive data (sends back the date)
 * 5: Close the connection after completion
 *
 */
public class TCPDateServer {
	private static ServerSocket servSocket;
	private static final int PORT = 1234;
	
	public static void main(String[] args) {
		System.out.println("Opening port");
		try {
			//1: Initialize ServerSocket Object
			servSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			System.out.println("Unable to connect to host on port:"+PORT+"\n"+e.getMessage());
			System.exit(1);
		}
		
		do {
			handleClient();
		} while(true);
	}
	
	private static void handleClient() {
		//2: Put the server into a waiting state
		Socket link = null;
		
		try {
			//2: Put the server into a waiting state
			link = servSocket.accept();
			//3: Set up input and output streams
			Scanner input = new Scanner(link.getInputStream());
			//3: Set up input and output streams
			PrintWriter output = new PrintWriter(link.getOutputStream(), true);
			int numMessages = 0 ;
			
			//4: Send and receive data
			String message = input.nextLine();
			while(!message.equals("CLOSE")) {
				System.out.println("Message Received");
				numMessages++;
				//4: Send and receive data
				//output.println("Messages "+numMessages+": "+ message);
				Date date = new Date();
				output.println("Server says: "+date);
				message = input.nextLine();
			}
			output.println(numMessages + " messages received.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				System.out.println("\nClosing port");
				//5: Close the connection after completi
				link.close();
 			} catch (IOException e) {
 				System.out.println("Unable to disconnect");
 				System.exit(1);
 			}
		}
	}
}