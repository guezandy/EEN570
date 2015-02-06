package A0;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
/**
 * 
 * @author Andrew Rodriguez
 * Emulates a server to receive the data and time, basically just echos date and time anytime its called.
 *
 */
public class DayTimeServer {
	public static void main(String[] args) {
		ServerSocket server;
		final int PORT_TO_CHECK = 13;
		Socket socket;
		try {
			server = new ServerSocket(PORT_TO_CHECK);
			do {
				socket = server.accept();
				PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
				Date date = new Date();
				output.println(date);
				socket.close();
			} while(true);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}