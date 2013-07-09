import java.io.IOException;


public class ChatServer {
	public static void main(String[] args) throws IOException {
		Server d = new Server();
		d.go();
		// Thread t=new Thread(d);
		// t.start();
	}

}
