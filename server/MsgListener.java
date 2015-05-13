package server;

import java.util.ArrayList;

// listens for messages from a Connection.  
// A different MsgListener object is created for each Connection
public class MsgListener implements Runnable{
	private Connection conn;
	
	public MsgListener(Connection conn){
		this.conn = conn;
	}

	@Override
	public void run() {
		while(true){ // listens for new messages as long as the Connection is alive
			String msg = conn.read();
			conn.dataIn(msg);
		}
	}
}
