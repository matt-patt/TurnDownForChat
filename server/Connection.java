package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class Connection{
	private Socket sock;
	private DataOutputStream out;
	private DataInputStream in;
	public String msg;
	private Server srv;
	
	public Connection(Socket sock, Server srv){
		this.sock = sock;
		this.srv = srv;
		try {
			out = new DataOutputStream(sock.getOutputStream());
			in = new DataInputStream(sock.getInputStream());
			out.flush();
			MsgListener msgListen = new MsgListener(this); // thread used to listen for incoming messages
			Thread lstnMsg = new Thread(msgListen);
			lstnMsg.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setSocket(Socket sock){
		this.sock = sock;
	}
	
	public Socket getSocket(){
		return sock;
	}
	
	public String read() {
		msg = "";
		try {
			msg = in.readUTF();
		} catch (IOException e) { // if can't connect to client close that socket, which will trigger it being removed from the arraylist in 
			try {
				sock.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return msg;
	}
	
	protected void dataIn(String in){
		srv.writeData(in);
	}
	public void write(String toWrite) throws IOException{
			out.writeUTF(toWrite);
			out.flush();
	}
}