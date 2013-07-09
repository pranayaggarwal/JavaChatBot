import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;
public class Client {
	ArrayList<String>clientNames=new ArrayList();
	JFrame frame =new JFrame("... Chat Client ...");
	JTextArea incoming;
	JTextField outgoing;
	BufferedReader reader;
	PrintWriter writer;
	Socket sock;

	public void go() {
		
		JPanel mainPanel = new JPanel() ;
		incoming = new JTextArea(15,50);
		
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true) ;
		incoming.setEditable(false);
		JScrollPane qScroller = new JScrollPane(incoming) ;
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS) ;
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		outgoing = new JTextField(20);
		JButton sendButton = new JButton("Send");
		JButton listButton = new JButton("List of all Users");
		sendButton.addActionListener(new SendButtonListener());
		listButton.addActionListener(new listButtonListener());
		mainPanel.add(qScroller) ; 
		mainPanel.add(outgoing); 
		mainPanel.add(sendButton) ;
		mainPanel.add(listButton) ;
		setUpNetworking();
		Thread readerThread=new Thread(new IncomingReader(reader,incoming));
		readerThread.start();
		frame .getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(600,600);
		frame .setVisible(true);
	}

	public void setUpNetworking(){
		try {
			String ip="127.0.0.1";
			ip=JOptionPane.showInputDialog(null,"Enter server ip address","127.0.0.1");
			int port=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter server port number","9000"));
			//int p=Integer.parseInt(port);
			sock = new Socket(ip,port);
			InputStreamReader streamReader = new InputStreamReader(
					sock.getInputStream());
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("Networking Established..");
			String name=JOptionPane.showInputDialog("Enter your name");
			if(name.contentEquals(""))name="Anonymous";
			clientNames.add(name);
			incoming.append("Welcome "+name+" to Pranay's ChatRoom!!\n Press list button to verify your connection !! \n If it is not functioning, Enter QUIT and try again :( ");
			
			writer.println(name) ;
			writer.flush() ;
			
			incoming.append("\n Whenever you wanna quit,enter 'QUIT' \n For sending private messages to any user Enter - '@<receiver> <message>'\nTo send private message to many users,\n Enter '@<receiver1> <receiver2> ...<receiverN>#<message>'\n");

		} catch (IOException e) {

		}
	}
	public class SendButtonListener implements ActionListener
	{
	public void actionPerformed(ActionEvent ev) {
		try {
		
			if((outgoing.getText()).contains("QUIT"))frame.setVisible(false);
				writer.println(outgoing.getText()) ;
				writer.flush() ;
		}
				catch(Exception ex) {
						ex.printStackTrace() ;
				}
		outgoing.setText("");
		outgoing.requestFocus() ;
	}
	

}
	public class listButtonListener implements ActionListener
	{
	public void actionPerformed(ActionEvent ev) {
		try {
			outgoing.setText("list");
			
			writer.println(outgoing.getText()) ;
			writer.flush() ;
		}
				catch(Exception ex) {
						ex.printStackTrace() ;
				}
		
		outgoing.setText("");
		outgoing.requestFocus() ;
	}
	

}


}
