package server;

import java.util.ArrayList;

public class MsgListener implements Runnable{
	private Connection conn;
	
	public MsgListener(Connection conn){
		this.conn = conn;
	}

	@Override
	public void run() {
		while(true){
			String msg = conn.read();
			conn.dataIn(msg);
//			if(connections.size()!=0){
//				for (int i = 0;i<connections.size();i++){
//					
//					String msg = connections.get(i).read();
//					svr.writeData(msg);
//				}
//			}
//			else{
//				System.out.println("Error, no clients connected!");
//				try {
//					Thread.sleep(3000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
		}
	}
}
