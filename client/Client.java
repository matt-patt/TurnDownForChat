package client;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.DataOutputStream;

import javax.swing.*;


//client connects to server
public class Client extends JFrame implements Runnable{
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private String name;
	private Encrypt enc;
	private JTextField userText;
	private JTextArea chatWindow;
	
	
	public Client(String addr, int port, String name, Encrypt enc){
		super(name + "'s Turn Down For Chat");
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent event){
					send(event.getActionCommand());
					userText.setText("");
				}
			}
		);

		add(userText, BorderLayout.SOUTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow), BorderLayout.CENTER);

		setSize(500, 400);
		setVisible(true);
		
		this.enc = enc;
		boolean connected = false;
		this.name = name;
		//Thread listen = new Thread(this); // thread for receiving messages
		while (!connected){
			try {
				socket = new Socket(addr, port);
				//listen.start();
				connected = true;
				userText.setEditable(true);
			} catch (IOException e) {
				showMessage("CANNOT CONNECT TO SERVER!");
				try { // wait for one second before retrying connection
					Thread.sleep(1000); 
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		try { // after socket is created setup in and out streams
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		chatWindow.setEditable(false);
	}
	
	public void runClient(){ // runs client receiving and sending 
		Thread listen = new Thread(this);
		listen.start(); // starts thread listening for incoming messages
	}
	private void receive(){ // receives and decrypts messages
		try {
			showMessage(enc.decrypt(in.readUTF()) + "\n");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	private void send(String msg){ // sends encrypted messages
		try {
			out.writeUTF(enc.encrypt(name + " - " + msg));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	private void showMessage(final String m){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					chatWindow.append(m);
				}
			}
		);
	}
	
	public void run() {
		while(true)
			receive();
		
	}

	public static void main(String[] args) throws IOException{
		ClientSetup setup = new ClientSetup();
		Encrypt enc = null;
		
		String name = setup.getName();
		String pass = setup.getPassword();
		int encType = setup.getCipher();
		if (encType == 1){
			enc = new Encrypt(pass.charAt(0)); // if user supplies more than one char take the first one
		}
		
		if (encType == 2){
			enc = new Encrypt(pass);
		}
		Client client = new Client("localhost", 12345, name, enc);
		client.runClient(); 
	}


}
