package Playfair;

import java.util.Scanner;

public class Runner {

	public static void main(String[] args) {
		System.out.print("Playfair Cipher Project - G00314417");
		boolean SENTINAL = true;
		Scanner sc = new Scanner(System.in);
		
		while(SENTINAL != false) {
			
			System.out.println("Enter a number to select an option:");
			System.out.println("1. Would you like to encrypt a word?");
			System.out.println("2. Would you like to decrypt a word?");
			System.out.println("3. QUIT");
			int choice = sc.nextInt();
			
			if(choice == 1) {
				System.out.println("Encyption selected!");
				Encryptor en = new Encryptor();
		        System.out.println("Enter a keyword:");
		        String keyword = sc.next();
		        en.setKey(keyword);
		        en.KeyGen();
		        System.out
		                .println("Enter word to encrypt: (Make sure length of message is even)");
		        String key_input = sc.next();
		        if (key_input.length() % 2 == 0)
		        {
		            System.out.println("Encryption: " + en.encryptMessage(key_input));
		        }
		        else
		        {
		            System.out.println("Message length should be even");
		        }
			}else if(choice == 2) {
				System.out.println("Decyption selected!");
			}else if(choice == 3) {
				System.out.println("Closing Playfair...");
				sc.close();
				SENTINAL = false;
			}else {
				System.out.println("Try again");
			}
			
		}//while end
	}

}
