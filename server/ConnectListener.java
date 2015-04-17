package server;

import java.io.IOException;
import java.net.ServerSocket;

public class ConnectListener implements Runnable{
	private ServerSocket server;
	private Server srv;
	private int port;
	
	public ConnectListener(int port, Server srv){
		this.srv = srv;
		this.port = port;
	}
	
	public void run(){ // listens for new connections
		try{
			server = new ServerSocket(port);
		} catch(IOException e){
			e.printStackTrace();
		}
		while(true) {
			try {
				srv.addConnection(server.accept());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
