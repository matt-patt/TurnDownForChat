package server;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Server {
	private ServerSocket server;
	private ArrayList<Connection> connections; // ArrayList used because auto left shift; won't have a connection at [1] not open, while one at [7] is if the user disconnects
	
	public Server(int port, int maxConnections){ // Initializes server with an array of connections and a port
		connections = new ArrayList<Connection>(maxConnections);
		ConnectListener connListen = new ConnectListener(port, this);
		Thread lstnConn = new Thread(connListen);
		lstnConn.start(); // starts thread listening for new connections
		System.out.println("Server Initialized");
	}

	//broadcast string to all connected clients
	protected void writeData(String toWrite){
		if (!connections.isEmpty()){
			for (int i = 0;i<connections.size();i++){
				try {
					connections.get(i).write(toWrite);
				} catch (IOException e) { // if client disconnects remove them from the list of connections
					connections.remove(i);
					System.out.println("Removing Connection at " + i);
				}
			}
		}
	}
	
	protected void addConnection(Socket sock){
		connections.add(new Connection(sock, this));
		//System.out.println("New Connection! - " + connections.get(connections.size()-1).getSocket());
	}
	
	
	//launches server
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException{
		Scanner keyboard = new Scanner(System.in);
		Server svr = new Server(12345, 50);
	}
}