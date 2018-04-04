package ie.gmit.sw.ai;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Runner {
	
	public static void main(String[] args) {
		System.out.println("Playfair Cipher Project - G00314417");
		System.out.println("-------------------------------------");
		boolean SENTINAL = true;
		Scanner sc = new Scanner(System.in);
		
		while(SENTINAL != false) {
			
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
				sc.nextLine();
		        System.out.println("Enter a key:");
		        String key = sc.next();
				Playfair playfair = new Playfair(key);
				System.out.println("Enter text to ecrypt:");
				sc.nextLine();
				String text = sc.next();
				
				System.out.println(playfair.encrypt(text));
			}else if(choice == 2) {
				System.out.println("SA selected!");
				try
				{
					sc.nextLine();
					System.out.println("Enter cipher text to solve:");
			        String solution = sc.nextLine();
			        System.out.println(solution);
					Playfair sol = new Playfair(solution);
					
					System.out.println("Enter a Max Temp Field:");
					double maxTemp = sc.nextDouble();
			        System.out.println("Enter tempStepField:");
			        double step = sc.nextDouble();
			        System.out.println("Enter iterationsField:");
			        int iterationsOnTemp = sc.nextInt();
			        System.out.println(maxTemp+ " " + step + " " + iterationsOnTemp);
					SimulatedAnnealing sa = new SimulatedAnnealing(maxTemp, step, iterationsOnTemp);
					sc.nextLine();
					String cipherText = sc.nextLine();
					
					System.out.println("SA Solution: " + sa.getFitness(cipherText, sol));
					
					PlayfairKey key = sa.findKey(cipherText);
					Playfair pf = new Playfair(key);
					String plainText = pf.decrypt(cipherText);
					System.out.println(plainText);
					System.out.println("Maximum fitness found : " + sa.getFitness(cipherText, pf));
				}
				catch (NumberFormatException e)
				{
					e.printStackTrace();
				}
			}else if(choice == 3) {
				System.out.println("SA selected!");
				try
				{
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
				}
				catch (NumberFormatException e)
				{
					e.printStackTrace();
				}
			}else if(choice == 4) {
				System.out.println("SA selected!");
				try
				{
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
					System.out.println("Maximum fitness found : " + sa.getQuadFitness(cipherText, pf));
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
				}
				catch (NumberFormatException e)
				{
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(choice == 6) {
				sc.nextLine();
			    System.out.println("Please input the keyword for the Playfair cipher.");
			    String key = sc.nextLine();
			    key = key.toUpperCase().replaceAll("[^A-Za-z0-9 ]", "");
			    System.out.println("Please enter text you want decoded.");
			    String text = sc.nextLine();
			    Decrypt decrypt = new Decrypt(key, text);
			    String msg = decrypt.decode();
			    System.out.println("Results:");
			    System.out.println(msg);
			}else if(choice == 7) {
				System.out.println("Closing Playfair...");
				sc.close();
				SENTINAL = false;
			}else {
				System.out.println("Try again");
			}
			
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
}

