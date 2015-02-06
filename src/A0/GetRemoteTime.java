package A0;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 * @author Andrew Rodriguez
 * Get time from localhost. Has a nice GUI asks you for the port number to enter the host then asks that host for the date and time
 */

public class GetRemoteTime extends JFrame implements ActionListener {
	
	//initialize view parameters
	private JTextField hostInput;
	private JTextArea display;
	private JButton timeButton;
	private JButton exitButton;
	private JPanel buttonPanel;
	private static Socket socket = null;
	
	public GetRemoteTime() {
		hostInput = new JTextField(20);
		add(hostInput, BorderLayout.NORTH);
		display = new JTextArea(10,15);
		
		add(new JScrollPane(display), BorderLayout.CENTER);
		
		buttonPanel = new JPanel();
		
		timeButton = new JButton("Get data and time ");
		timeButton.addActionListener(this);
		buttonPanel.add(timeButton);
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		buttonPanel.add(exitButton);
		
		add(buttonPanel, BorderLayout.SOUTH);
		
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == exitButton) {
			System.exit(0);
		}
		
		String theTime;
		//Get host name from the user
		String host = hostInput.getText();
		final int PORT_TO_CHECK = 13;
		
		try {
			socket = new Socket(host, PORT_TO_CHECK);
			Scanner input = new Scanner(socket.getInputStream());
			theTime = input.nextLine();
			display.append("The data/time at "+host +" is "+theTime+"\n");
			hostInput.setText("");
		} catch (UnknownHostException e) {
			display.append("Host doesnt exist");
			hostInput.setText("");
		} catch (IOException i) {
			display.append(i.getMessage()+"\n");
		}
		
		finally {
			try {
				if(socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				System.out.println("Unable to disconnect!");
				System.exit(1);
			}
		}
	}
	
	public static void main(String[] args) {
		GetRemoteTime frame = new GetRemoteTime();
		frame.setSize(400,300);
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				//check if socket is open on window closing and close it
				if(socket != null) {
					try {
						socket.close();
					} catch(IOException e) {
						System.out.println("Can't close socket error: "+e.getMessage());
						System.exit(1);
					}
				}
				System.exit(0);
			}
		});
	}
	
	
}