package server;

import java.io.IOException;
import java.net.ServerSocket;

//listens for new Connection attempts and passes them to the Server object
public class ConnectListener implements Runnable{
	private ServerSocket server;
	private Server srv;
	private int port;
	
	public ConnectListener(int port, Server srv){
		this.srv = srv;
		this.port = port;
	}
	
	public void run(){ // listens for new connections
		try{ // creates the ServerSocket to listen for connections
			server = new ServerSocket(port);
		} catch(IOException e){
			e.printStackTrace();
		}
		while(true) {
			try { // if a client attempts to connect tell the server
				srv.addConnection(server.accept());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
