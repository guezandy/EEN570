package A0;

import java.io.IOException;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 
 * @author Andrew Rodriguez
 * Asks user for a message, sends it over TCP to TCPEchoServer and waits to receive response.
 * 1: Initialize ServerSocket Object
 * 2: Set up input and output streams
 * 3: Send and receive data
 * 4: Close the connection after completion
 */

public class TCPEchoClient {
	private static InetAddress host;
	private static final int PORT = 1234;
	
	public static void main(String[] args) {
		try {
			host = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("Host id not found");
			System.exit(1);
		}
		accessServer();
	}
	
	public static void accessServer() {
		//1: Initialize ServerSocket Object
		Socket link = null;
		
		try {
			//1: Initialize ServerSocket Object
			link = new Socket(host, PORT);
			//2: Set up input stream
			Scanner input = new Scanner(link.getInputStream());
			//2: Set up output stream
			PrintWriter output = new PrintWriter(link.getOutputStream(), true);
			
			//get keyboard entry
			Scanner userEntry = new Scanner(System.in);
			
			String message, response;
			do {
				System.out.println("Enter message: ");
				message  = userEntry.nextLine();
				// 3: Send and receive data
				output.println(message);
				// 3: Send and receive data
				response = input.nextLine();
				System.out.println("\nServer: "+response);
			} while(!message.equals("CLOSE"));
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
		finally {
			try {
				System.out.println("\nClosing connection");
				//4: Close the connection after completion
				link.close();
			} catch(IOException e) {
				System.out.println("Unable to disconnect");
				System.exit(1);
			}
		}
	}
}