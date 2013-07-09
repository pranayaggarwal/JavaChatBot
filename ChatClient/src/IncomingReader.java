import java.io.BufferedReader;

import javax.swing.JTextArea;

public class IncomingReader implements Runnable {
	BufferedReader reader;
	JTextArea incoming;

	IncomingReader(BufferedReader read, JTextArea area) {
		incoming=area;
		reader = read;
	}

	public void run() {
		String message;
		String c="";
		try {
			while ((message = reader.readLine()) != null) {

				System.out.println(message);
				
				incoming.append(message + "\n");
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
