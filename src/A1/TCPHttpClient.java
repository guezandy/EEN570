package A1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 
 * @author Andrew Rodriguez
 *
 */

public class TCPHttpClient {

	private static final int PORT = 80;
	
	
	public static void main(String[] args) {
		//1: Initialize ServerSocket Object
		Socket l = null;
		accessServer(l);
	}
	
	public static void accessServer(Socket link) {
		try {
		
			//Ask user to enter input
			Scanner userEntry = new Scanner(System.in);
			System.out.println("Enter host: ");
			String inputHost  = userEntry.nextLine();
//a. Connect to the site provided by the user
			link = new Socket(inputHost, PORT);///testing

			//Initialize interface to send message to the host
			PrintWriter output = new PrintWriter(link.getOutputStream(), true);
//b. Format request message to the site
			String message = "GET / HTTP/1.1\n"
					+ "Host: "+inputHost+"\n"
					+ "Accept: text/*, text/html, text/html;level=1\n"
					+ "Accept-Language: en\n"
					+ "Accept-Encoding: compress, gzip\n"
					+ "Accept-Charset: iso-8859-5, unicode-1-1;q=0.8\n"
					+ "Keep-Alive: timeout=15, max=100\n"
					+ "Connection: keep-alive\n\n";
		
//b. Send message and wait
			output.println(message);
//c. Read the response message
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(link.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			//int timeout = 5000;
			int lineCount = 0;
			while(lineCount < 200) { 
			while ((inputLine = in.readLine()) != "null" /*|| timeout<= 0*/) {
				response.append(inputLine);
				response.append("\n");
				//timeout--;
				break;
			}
			lineCount++;
			}
			in.close();
//d&e Print the information from the response message
			System.out.println("\nServer: "+response);

		
			try {
				System.out.println("\nClosing connection");
//f. Close the connection after completion
				link.close();
			} catch(IOException e) {
				System.out.println("Unable to disconnect");
				System.exit(1);
			}
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
}