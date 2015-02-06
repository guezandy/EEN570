package A1;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * 
 * @author Andrew Rodriguez
 * Java offers the ability to render HTML pages as a browser would do. 
 * The class to hold webpage in java is JEditorPane, this class renders HTML formated text for any webpage downloaded using the setPage() method.
 * How to handle hyperlinks:
 * 		If hyperlinks are contained within a downloaded page a HyperlinkEvent is generate when the user clicks on one of these and must be handled by a HyperlinkListener.
 *     The hyperlink event were concerned with is ACTIVATED that occurs when a user clicks a hyperlink
 */

public class GetWebPage extends JFrame implements ActionListener {
	//fields for user to insert the webpage
	private JLabel prompt;
	private JTextField sourceName;
	private JPanel requestPanel;
	
	//holder for the webpage
	private JEditorPane contents;
	
	public static void main(String[] args) {
		GetWebPage frame = new GetWebPage();
		frame.setSize(700,500);
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public GetWebPage() {
		//Initialize and add views
		setTitle("Andrew Rodriguez's Browser");
		requestPanel = new JPanel();
		prompt = new JLabel("Requred URL: ");
		sourceName = new JTextField(25);
		sourceName.addActionListener(this);
		requestPanel.add(prompt);
		requestPanel.add(sourceName);
		add(requestPanel, BorderLayout.NORTH);
		
		contents = new JEditorPane();
		
		contents.setEditable(false);
		//handle the links on the webpage
		LinkListener linkHandler = new LinkListener();
		
		contents.addHyperlinkListener(linkHandler);
		
		add(new JScrollPane(contents), BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent event) {
		showPage(sourceName.getText());
	}
	
	private class LinkListener implements HyperlinkListener {
		public void hyperlinkUpdate(HyperlinkEvent event) {
			if(event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				showPage(event.getURL().toString());
			}
		}
	}
	
	//grabs the url and called method: JEditorPane.showPage(URL);
	private void showPage(String location) {
		try {
			contents.setPage(location);
			sourceName.setText(location);
		} catch(IOException e) {
			JOptionPane.showMessageDialog(this,"Unable to retrieve URL", "Invalid Url", JOptionPane.ERROR_MESSAGE);
		}
	}
}



