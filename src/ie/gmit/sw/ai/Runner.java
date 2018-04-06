package ie.gmit.sw.ai;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Runner {
	
	public static void main(String[] args) {
		System.out.println("Playfair Cipher Project - G00314417");
		System.out.println("-------------------------------------");
		boolean SENTINAL = true;
		Scanner sc = new Scanner(System.in);
		
		while(SENTINAL != false) {
			// Main menu for user
			System.out.println("Enter a number to select an option:");
			System.out.println("1. Would you like to encrypt a word?");
			System.out.println("2. Would you like to apply SA to input(2grams)?");
			System.out.println("3. Would you like to apply SA to input(4grams)?");
			System.out.println("4. Would you like to apply SA to a text file(2grams)?");
			System.out.println("5. Would you like to apply SA to a text file(4grams)?");
			System.out.println("6. Would you like to decrypt a word?");
			System.out.println("7. QUIT");
			int choice = sc.nextInt();
			
			if(choice == 1) {
				/* This option creates a cipher table with a key input by the user,
				 * it then asks the user for some text to encrypt and prints the encrypted
				 * text on the screen for the user to copy and use*/
				sc.nextLine();
		        System.out.println("Enter a key:");
		        String key = sc.next();
				Playfair playfair = new Playfair(key);
				System.out.println("Enter text to ecrypt:");
				sc.nextLine();
				String text = sc.nextLine();
				String result = playfair.encrypt(text);
				try {
					writeResult("CipherText",result);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else if(choice == 2) {
				System.out.println("SA selected!");
				try
				{
					/* This option will use Simulated Annealing to attempt to break playfair cipher encrypted code
					 * thats provided by the users keyboard, it will use the 2grams text file which should provide a more
					 * accurate result and algorithm frequency*/
					sc.nextLine();
					System.out.println("Enter key to create table:");
			        String solution = sc.nextLine();
			        System.out.println(solution);
					Playfair sol = new Playfair(solution);
					
					System.out.println("Enter a Max Temp Field:");
					double maxTemp = sc.nextDouble();
			        System.out.println("Enter tempStepField:");
			        double step = sc.nextDouble();
			        System.out.println("Enter iterationsField:");
			        int iterationsOnTemp = sc.nextInt();
					SimulatedAnnealing sa = new SimulatedAnnealing(maxTemp, step, iterationsOnTemp);
					sc.next();
					String cipherText = sc.nextLine();
					
					System.out.println("SA Solution: " + sa.getFitness(cipherText, sol));
					
					PlayfairKey key = sa.findKey(cipherText);
					Playfair pf = new Playfair(key);
					String plainText = pf.decrypt(cipherText);
					System.out.println(plainText);
					System.out.println("Maximum fitness found : " + sa.getFitness(cipherText, pf));
					try {
						writeResult("SA attempt(2Grams) PlainText",plainText);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				catch (NumberFormatException e)
				{
					e.printStackTrace();
				}
			}else if(choice == 3) {
				System.out.println("SA selected!");
				try
				{
					/* This option will use Simulated Annealing to attempt to break playfair cipher encrypted code
					 * thats provided by the users keyboard, it will use the 4grams text file to calculate the
					 * result and algorithm frequency*/
					sc.nextLine();
					System.out.println("Set key to create cipher for SA to solve:");
			        String solution = sc.nextLine();
			        System.out.println(solution);
					Playfair sol = new Playfair(solution);
					
					System.out.println("Enter max temperature:");
					double maxTemp = sc.nextDouble();
			        System.out.println("Enter temperature steps:");
			        double step = sc.nextDouble();
			        System.out.println("Enter total iterations:");
			        int iterationsOnTemp = sc.nextInt();
			        System.out.println(maxTemp+ " " + step + " " + iterationsOnTemp);
					SimulatedAnnealing sa = new SimulatedAnnealing(maxTemp, step, iterationsOnTemp);
					sc.nextLine();
					String cipherText = sc.nextLine();
					System.out.println(cipherText);
					
					System.out.println("SA Solution: " + sa.getQuadFitness(cipherText, sol));
					
					PlayfairKey key = sa.findKey(cipherText);
					Playfair pf = new Playfair(key);
					String plainText = pf.decrypt(cipherText);
					System.out.println(plainText);
					System.out.println("Maximum fitness found : " + sa.getQuadFitness(cipherText, pf));
					try {
						writeResult("SA(4Grams) attempt PlainText",plainText);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				catch (NumberFormatException e)
				{
					e.printStackTrace();
				}
			}else if(choice == 4) {
				System.out.println("SA selected!");
				try
				{
					/* This option will use Simulated Annealing to attempt to break playfair cipher encrypted code
					 * thats provided by a text file in the MyFiles folder, it will use the 2grams text file which should provide a more
					 * accurate result and algorithm frequency*/
					sc.nextLine();
					System.out.println("Set key to create cipher for SA to solve:");
			        String solution = sc.nextLine();
			        System.out.println(solution);
					Playfair sol = new Playfair(solution);
					
					System.out.println("Enter max temperature:");
					double maxTemp = sc.nextDouble();
			        System.out.println("Enter temperature steps:");
			        double step = sc.nextDouble();
			        System.out.println("Enter total iterations:");
			        int iterationsOnTemp = sc.nextInt();
			        System.out.println(maxTemp+ " " + step + " " + iterationsOnTemp);
					SimulatedAnnealing sa = new SimulatedAnnealing(maxTemp, step, iterationsOnTemp);
					
					System.out.print("Enter the name of the txt file you want to encrypt: ");
					sc.nextLine();
					String fileName = sc.nextLine();
					String cipherText = readFile(fileName);
					System.out.println(cipherText);
					
					System.out.println("SA Solution: " + sa.getFitness(cipherText, sol));
					
					PlayfairKey key = sa.findKey(cipherText);
					Playfair pf = new Playfair(key);
					String plainText = pf.decrypt(cipherText);
					System.out.println(plainText);
					System.out.println("Maximum fitness found : " + sa.getFitness(cipherText, pf));
					try {
						writeResult("SA(2Grams) attempt PlainText",plainText);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				catch (NumberFormatException e)
				{
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(choice == 5) {
				System.out.println("SA selected!");
				try
				{
					/* This option will use Simulated Annealing to attempt to break playfair cipher encrypted code
					 * thats provided by a text file from the MyFiles folder, it will use the 4grams text file to calculate the
					 * result and algorithm frequency*/
					sc.nextLine();
					System.out.println("Set key to create cipher for SA to solve:");
			        String solution = sc.nextLine();
			        System.out.println(solution);
					Playfair sol = new Playfair(solution);
					
					System.out.println("Enter max temperature:");
					double maxTemp = sc.nextDouble();
			        System.out.println("Enter temperature steps:");
			        double step = sc.nextDouble();
			        System.out.println("Enter total iterations:");
			        int iterationsOnTemp = sc.nextInt();
			        System.out.println(maxTemp+ " " + step + " " + iterationsOnTemp);
					SimulatedAnnealing sa = new SimulatedAnnealing(maxTemp, step, iterationsOnTemp);
					
					System.out.print("Enter the name of the txt file you want to encrypt: ");
					sc.nextLine();
					String fileName = sc.nextLine();
					String cipherText = readFile(fileName);
					System.out.println(cipherText);
					
					System.out.println("SA Solution: " + sa.getQuadFitness(cipherText, sol));
					
					PlayfairKey key = sa.findKey(cipherText);
					Playfair pf = new Playfair(key);
					String plainText = pf.decrypt(cipherText);
					System.out.println(plainText);
					System.out.println("Maximum fitness found : " + sa.getQuadFitness(cipherText, pf));
					try {
						writeResult("SA(4Grams) attempt PlainText",plainText);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				catch (NumberFormatException e)
				{
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(choice == 6) {
				/* This option allows the user to manually decode the cipher text by entering a key*/
			    sc.nextLine();
		        System.out.println("Enter a key:");
		        String key = sc.next();
				Playfair playfair = new Playfair(key);
				System.out.println("Enter text to decrypt:");
				sc.nextLine();
				String text = sc.next();
				text = text.toUpperCase().replaceAll("[^A-Za-z0-9 ]", "");
				String plainText =playfair.decrypt(text);
				try {
					writeResult("PlainText",plainText);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(choice == 7) {
				System.out.println("Closing Playfair...");
				sc.close();
				SENTINAL = false;
			}else {
				System.out.println("Try again");
			}
			sc.nextLine();
		}//while end
	}

	static String readFile(String fileName) throws IOException {
		
		System.out.println("file:" + fileName);
	    BufferedReader br = new BufferedReader(new FileReader("MyFiles\\" + fileName + ".txt"));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	        	//System.out.println(line);
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
	
	static void writeResult(String txtType, String result) throws IOException {
		System.out.println(result);
		//Print the result to result.txt in MyFiles
		try (PrintWriter out = new PrintWriter("MyFiles\\result.txt")) {
		    out.println(txtType + ": " + result);
		    System.out.println("result written to result.txt in MyFiles");
		}
	}
}

