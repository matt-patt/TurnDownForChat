package client;
import java.util.Scanner;
public class Encrypt {
	private String seed;
	private int key;
	private int encType; //1 = caesar, 2 = vigenere
	
	public Encrypt(String seed){ // used for modCaesar
		this.seed = seed;
		this.encType = 2; 
	}
	
	public Encrypt(char key){ // used for caesar
		this.encType = 1;
		this.key = Character.getNumericValue(key)-9; // sets the key to 1 if given 'a', etc
	}
	
	public String encrypt(String plainText){
		String cypherText = "";
		if (encType == 1)
			cypherText = caesarEnc(plainText);
		if (encType == 2)
			cypherText = vigenereEnc(plainText);
		return cypherText;
	}
	
	public String decrypt(String cypherText){
		String plainText = "";
		if (encType == 1)
			plainText = caesarDec(cypherText);
		if (encType == 2)
			plainText = vigenereDec(cypherText);
		return plainText;
	}
	
	// ignores non alphanumeric characters
	private String caesarEnc(String plainText){
		String cipherText = "";
		for (int i = 0;i<plainText.length();i++){
			char toEnc = plainText.charAt(i);
			if(Character.isLowerCase(toEnc)) {
				for(int j = 0;j<key;j++){
					toEnc++;
					if(toEnc>122) // ensures character stays as same case and as text
						toEnc = 97;
				}
				cipherText+=toEnc;
			}
			else if (Character.isUpperCase(toEnc)){
				for(int j = 0;j<key;j++){
					toEnc++;
					if(toEnc>90) // ensures character stays as same case and as text
						toEnc = 65;
				}
				cipherText+=toEnc;
			}
			else if (Character.isDigit(toEnc)){
				for(int j = 0;j<key;j++){
					toEnc++;
					if(toEnc>57) // ensures character stays as number
						toEnc = 48;
				}
				cipherText+=toEnc;
			}
			else
				cipherText+=toEnc; // if special character don't encrypt it, but still broadcast it
		}
		return cipherText;
	}
	

	private String caesarDec(String cipherText){
		String plainText = "";
		for (int i = 0;i<cipherText.length();i++){
			char toEnc = cipherText.charAt(i);
			if(Character.isLowerCase(toEnc)) {
				for(int j = 0;j<key;j++){
					toEnc--;
					if(toEnc<97) // ensures character stays as same case and as text
						toEnc = 122;
				}
				plainText+=toEnc;
			}
			else if (Character.isUpperCase(toEnc)){
				for(int j = 0;j<key;j++){
					toEnc--;
					if(toEnc<65) // ensures character stays as same case and as text
						toEnc = 90;
				}
				plainText+=toEnc;
			}
			else if (Character.isDigit(toEnc)){
				for(int j = 0;j<key;j++){
					toEnc--;
					if(toEnc<48) // ensures character stays as number
						toEnc = 57;
				}
				plainText+=toEnc;
			}
			else // if special character don't encrypt it, but still broadcast it
				plainText+=toEnc;
		}
		return plainText;
	}
	
	
	// uses a rotating key on the plainText
	private String vigenereEnc(String plainText){
		String cipherText = "";
		int index = 0;
		for (int i = 0;i<plainText.length();i++){
			key = Character.getNumericValue(seed.charAt(index))-9;
			char toEnc = plainText.charAt(i);
			cipherText+=caesarEnc(Character.toString(toEnc));
			index++;
			if (index >= seed.length())
				index = 0;
		}
		return cipherText;
	}
	
	//finds the root of the cipherText using the index values used to encrypt the message
	private String vigenereDec(String cipherText){
		String plainText = "";
		int index = 0;
		for (int i = 0;i<cipherText.length();i++){
			key = Character.getNumericValue(seed.charAt(index))-9;
			char toEnc = cipherText.charAt(i);
			plainText+=caesarDec(Character.toString(toEnc));
			index++;
			if (index >= seed.length())
				index = 0;
		}
		return plainText;
	}
}