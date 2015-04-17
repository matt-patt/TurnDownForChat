package client;

import javax.swing.JOptionPane;

//allows user to enter their data using JOptionPane
public class ClientSetup{
	private int cipher;
	private String name;
	private String password;
	
	public ClientSetup(){
		name = JOptionPane.showInputDialog(null, "Please enter your name","Welcome to Turn Down for Chat!", JOptionPane.PLAIN_MESSAGE);
		password = JOptionPane.showInputDialog(null, "Please enter a password name","Welcome to Turn Down for Chat!", JOptionPane.PLAIN_MESSAGE);
		
		
		String[] options = new String[2];
		options[0] = "Caesar";
		options[1] = "Vigenere";
		cipher = JOptionPane.showOptionDialog(null, "Select your cipher!", "Turn Down For Chat",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
		cipher++;
	}
	
	public int getCipher() {
		return cipher;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public static void main(String[] args) {
		new ClientSetup();
	}
}
