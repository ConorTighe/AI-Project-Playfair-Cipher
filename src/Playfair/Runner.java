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
			System.out.println("2. Would you like to apply SA?");
			System.out.println("3. QUIT");
			int choice = sc.nextInt();
			
			if(choice == 1) {
				sc.nextLine();
		        System.out.println("Enter a key:");
		        String key = sc.next();
				Playfair playfair = new Playfair(key);
				System.out.println("Enter text to ecrypt:");
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
					String cipherText = "word";
					
					System.out.println("Solution is on: " + sa.getFitness(cipherText, sol));
					
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
				System.out.println("Closing Playfair...");
				sc.close();
				SENTINAL = false;
			}else {
				System.out.println("Try again");
			}
			
		}//while end
	}

}
