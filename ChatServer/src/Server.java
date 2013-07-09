import java.io.*;
import java.nio.channels.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Server {

	/**
	 * Runs the server.
	 * 
	 * @throws IOException
	 */
	ServerSocket serversoc;
	ArrayList<PrintWriter> Clients;
	ArrayList<String> clientNames;
	ArrayList<String> removeNames;

	public class ClientHandler implements Runnable {
		BufferedReader input;
		int index;
		Socket socket;

		public ClientHandler(Socket Clientsocket, int x) {
			index = x;
			try {
				socket = Clientsocket;
				input = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			String message;
			int flag = 1;
			try {
				while ((message = input.readLine()) != null) {
					if (flag == 1) {

						clientNames.add(message);
						tellName(message, index);
						flag--;
						continue;

					}
					System.out.println("Incoming message from broadcast "
							+ message);
					// System.out.println(message);
					if (message.contentEquals("QUIT"))
						tellLeave(message, index, clientNames.get(index));
					else if (message.contentEquals("list"))
						tellList(message, index, clientNames.get(index));
					else if (message.charAt(0)=='@' && !message.contains("#")) {

						message = message.replace("@", "");
						// System.out.println(message);
						int i = message.indexOf(' ');
						message=message.replace("@","");
						String word = message.substring(0, i);
						String rest = message.substring(i + 1);
						// System.out.println(word);
						// System.out.println(rest);
						tellPrivate(rest, clientNames.get(index), word);
					}
					else if (message.charAt(0)=='@' && message.contains("#")) {
						int i = message.indexOf('#');
						message=message.replace("@","");
						String names = message.substring(0, i-1);
						String rest= message.substring(i);
						tellMultiple(rest, clientNames.get(index), names);
						System.out.println(names+ "  --- "+rest);
					}
					else
						tellEveryone(message, index, clientNames.get(index));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	Server() {
		// Clients = new ArrayList();
		// try {
		// socket=new ServerSocket(9000,2).accept();
		// } catch (IOException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();

		// }
		try {
			int port=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter the port number for opening connections: ","9000"));
			serversoc = new ServerSocket(port);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void go() {

		Clients = new ArrayList<PrintWriter>();
		clientNames = new ArrayList<String>();
		removeNames = new ArrayList<String>();
		try {
			// ServerSocket serversoc = new ServerSocket(9000,1);
			Thread threads[] =new Thread[3];
			int i=0;
			while (i<3) {

				Socket clientsocket = serversoc.accept();
				PrintWriter writer = new PrintWriter(
						clientsocket.getOutputStream());
				Clients.add(writer);
				int index = Clients.indexOf(writer);

				// BufferedWriter out = new BufferedWriter(new
				// OutputStreamWriter(socket.getOutputStream()));

				threads[i] = new Thread(new ClientHandler(clientsocket, index));
				threads[i].start();
				i++;
				System.out.println("...Got a connection...");
				// Thread readerThread=new Thread(new IncomingReader(input2));
				// readerThread.start();
				// out.newLine();
				// out.flush();

				/*
				 * while(true) { String answer = input.readLine();
				 * System.out.println("Client: "+answer); //String question =
				 * JOptionPane.showInputDialog("Enter chat"); String question =
				 * input2.readLine(); out.println(question); //out.newLine();
				 * //out.flush();
				 * 
				 * }
				 */
			}
			if(i==3){
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void tellLeave(String message, int x, String name) {
		// Iterator<PrintWriter> it = Clients.iterator();

		tellEveryone(name + " is leaving..");
		removeNames.add(name);
		Iterator<String> itr = removeNames.iterator();
		while (itr.hasNext()) {
			System.out.println("--" + itr.next());
		}
		// writer.flush();

		// writer.flush();

	}

	public void tellList(String message, int x, String name) {
		PrintWriter write = Clients.get(x);
		Iterator<String> itr = clientNames.iterator();
		// Iterator<String> itr2 = removeNames.iterator();
		try {

			write.println("Here is the list of all online users : ");
			write.flush();
			int i = 1;
			while (itr.hasNext()) {
				// String []remove = (String[]) removeNames.toArray();

				String c = itr.next();
				if (!removeNames.contains(c)) {
					write.println(i + " : " + c);
					write.flush();
					i++;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void tellPrivate(String message, String sendername, String name) {
		Iterator<PrintWriter> it = Clients.iterator();
		Iterator<String> itr = clientNames.iterator();
		PrintWriter writer = null;
		String rec_name = "";
		rec_name = itr.next();
		writer = it.next();
		System.out.println(rec_name + "=" + name);
		// System.out.println(name);
		while (!rec_name.contains(name) && itr.hasNext()) {
			System.out.println(rec_name + "=" + name);
			rec_name = itr.next();
			writer = it.next();
		}

		writer.println("Private message for you by " + sendername + " : "
				+ message);
		writer.flush();
		

	}

	public void tellMultiple(String message, String sendername, String name) {
		if(name.equals("")) return;
		System.out.println(name+"-"+message);
		if(name.contains(" "))
		{
			int i=name.indexOf(' ');
			String receiver=name.substring(0,i);
			String restnames=name.substring(i+1);
			System.out.println(receiver+"+"+restnames);
			tellPrivate(message,sendername,receiver);
			tellMultiple(message,sendername,restnames);
		}
		else
		{
			tellPrivate(message,sendername,name);
		}
		
		
	}

	public void tellEveryone(String message) {
		Iterator<PrintWriter> it = Clients.iterator();
		Iterator<String> itr = clientNames.iterator();
		while (it.hasNext() && itr.hasNext()) {
			try {
				PrintWriter writer = (PrintWriter) it.next();
				writer.println("Broadcasted message by server : " + " : "
						+ message);
				writer.flush();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void tellEveryone(String message, int x, String name) {
		Iterator<PrintWriter> it = Clients.iterator();
		Iterator<String> itr = clientNames.iterator();
		while (it.hasNext() && itr.hasNext()) {
			try {
				PrintWriter writer = (PrintWriter) it.next();
				// String s=clientNames.get(x);
				writer.println("Broadcasted message by " + name + " : "
						+ message);
				writer.flush();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void tellName(String message, int x) {
		Iterator<PrintWriter> it = Clients.iterator();
		while (it.hasNext()) {
			try {
				PrintWriter writer = (PrintWriter) it.next();
				writer.println("Client - " + message
						+ " has entered the room!!");
				writer.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}