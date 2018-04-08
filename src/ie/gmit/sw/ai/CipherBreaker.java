package ie.gmit.sw.ai;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * 
 * @author Conor Tighe
 * Handles the main menus and the user key input/file that will be applied to the encryption/decryption.
 * This is also where the user can enter there tempatures,cooldown(steps) and iteration values or choose
 * the optimization formula to generate the tempatures etc.
 * along with the creation of the .txt files containing the results of the playfair calculations
 */
public class CipherBreaker {
	
	/***
	 * If statements and a while to create a command line UI
	 * @return read a file
	 * @return write to a file
	 * @throws IOException
	 */
	
	
	public static void main(String[] args) throws IOException {
		System.out.println("Playfair Cipher Project - G00314417");
		System.out.println("-------------------------------------");
		boolean SENTINAL = true;
		Scanner sc = new Scanner(System.in);
		// this while loop will keep the program running until the user activates the SENTINAL
		while(SENTINAL != false) {
			// Main menu for user
			System.out.println("--------------------------------------------");
			System.out.println("Enter a number to select an option:");
			System.out.println("1. Would you like to encrypt input?");
			System.out.println("2. Would you like to encrypt a text file?");
			System.out.println("3. Would you like to apply SA to input(2grams)?");
			System.out.println("4. Would you like to apply SA to input(4grams)?");
			System.out.println("5. Would you like to apply SA to a text file(2grams)?");
			System.out.println("6. Would you like to apply SA to a text file(4grams)?");
			System.out.println("7. Would you like to decrypt input?");
			System.out.println("8. Would you like to decrypt a text file?");
			System.out.println("9. Would you like to apply optimized SA(2grams)?");
			System.out.println("10. Would you like to apply optimized SA(4grams)?");
			System.out.println("11. Display a matrix without solving.");
			System.out.println("12. QUIT");
			int choice = sc.nextInt();
			System.out.println("--------------------------------------------");
			
			if(choice == 1) {
				/* This option creates a cipher table with a key input by the user,
				 * it then asks the user for some text to encrypt and prints the encrypted
				 * text on the screen for the user to copy and use*/
				sc.nextLine();
		        System.out.println("Enter a key:");
		        String key = sc.next();
				Playfair playfair = new Playfair(key);
				//playfair.showMatrix();
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
				
			}if(choice == 2) {
				/* This option creates a cipher table with a key input by the user,
				 * it then asks the user for some text to encrypt and prints the encrypted
				 * text on the screen for the user to copy and use*/
				sc.nextLine();
		        System.out.println("Enter a key:");
		        String key = sc.next();
				Playfair playfair = new Playfair(key);
				//playfair.showMatrix();
				System.out.print("Enter the name of the txt file you want to encrypt: ");
				sc.nextLine();
				String fileName = sc.nextLine();
				String text = readFile(fileName);
				//System.out.println(text);
				String result = playfair.encrypt(text);
				try {
					writeResult("CipherText",result);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else if(choice == 3) {
				System.out.println("SA selected!");
				try
				{
					/* This option will use Simulated Annealing to attempt to break playfair cipher encrypted code
					 * thats provided by the users keyboard, it will use the 2grams text file which should provide a more
					 * accurate result and algorithm frequency, it will then print the result out to a text file*/
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
					SimulatedAnnealing sa = new SimulatedAnnealing(maxTemp, step, iterationsOnTemp);
					sc.nextLine();
					System.out.println("Enter code you need decrypted:");
					String cipherText = sc.nextLine();
					
					System.out.println("SA fitness: " + sa.getFitness(cipherText, sol));
					
					PlayfairKey key = sa.findKey(cipherText);
					Playfair pf = new Playfair(key);
					String plainText = pf.decrypt(cipherText);
					System.out.println("plain: " + plainText);
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
			}else if(choice == 4) {
				System.out.println("SA selected!");
				try
				{
					/* This option will use Simulated Annealing to attempt to break playfair cipher encrypted code
					 * thats provided by the users keyboard, it will use the 4grams text file to calculate the
					 * result and algorithm frequency, it will then print the result out to a text file*/
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
					System.out.println("Enter code you need decrypted:");
					String cipherText = sc.nextLine();
					
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
			}else if(choice == 5) {
				System.out.println("SA selected!");
				try
				{
					/* This option will use Simulated Annealing to attempt to break playfair cipher encrypted code
					 * thats provided by a text file in the MyFiles folder, it will use the 2grams text file which should provide a more
					 * accurate result and algorithm frequency, it will then print the result out to a text file*/
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
					
					System.out.print("Enter the name of the txt file you want to decrypt: ");
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
			}else if(choice == 6) {
				System.out.println("SA selected!");
				try
				{
					/* This option will use Simulated Annealing to attempt to break playfair cipher encrypted code
					 * thats provided by a text file from the MyFiles folder, it will use the 4grams text file to calculate the
					 * result and algorithm frequency, it will then print the result out to a text file*/
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
			}else if(choice == 7) {
				/* This option allows the user to manually decode the cipher text by entering a key,
				 *  it will then print the result out to a text file*/
			    sc.nextLine();
		        System.out.println("Enter a key:");
		        String key = sc.next();
				Playfair playfair = new Playfair(key);
				//playfair.showMatrix();
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
			}else if(choice == 8) {
				/* This option allows the user to manually decode the cipher text by entering a key and text file name,
				 *  it will then print the result out to a text file*/
			    sc.nextLine();
		        System.out.println("Enter a key:");
		        String key = sc.next();
				Playfair playfair = new Playfair(key);
				//playfair.showMatrix();
				System.out.println("Enter text file to decrypt:");
				sc.nextLine();
				String fileName = sc.nextLine();
				String text = readFile(fileName);
				//System.out.println(text);
				String plainText =playfair.decrypt(text);
				writeResult("PlainText",plainText);
			}else if(choice == 9) {
				/* This option allows the user to use a formula to get the optimized tempature and apply it to the SA using 2grams,
				 *  it will then print the result out to a text file*/
				sc.nextLine();
				System.out.println("Set key to create cipher for SA to solve:");
		        String solution = sc.nextLine();
		        System.out.println(solution);
				Playfair sol = new Playfair(solution);
				System.out.print("Enter the name of the txt file you want to decrypt: ");
				String fileName = sc.nextLine();
				String text = readFile(fileName);
				System.out.println("Enter total iterations:");
		        int iterationsOnTemp = sc.nextInt();
				SimulatedAnnealing sa = new SimulatedAnnealing(text, iterationsOnTemp);
				
				System.out.println("SA fitness: " + sa.getFitness(text, sol));
				
				PlayfairKey key = sa.findKey(text);
				Playfair pf = new Playfair(key);
				String plainText = pf.decrypt(text);
				System.out.println("plain: " + plainText);
				System.out.println("Maximum fitness found : " + sa.getFitness(text, pf));
				try {
					writeResult("Optimized SA attempt(2Grams) PlainText",plainText);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(choice == 10) {
				/* This option allows the user to use a formula to get the optimized tempature and apply it to the SA using 4grams,
				 *  it will then print the result out to a text file*/
				sc.nextLine();
				System.out.println("Set key to create cipher for SA to solve:");
		        String solution = sc.nextLine();
		        System.out.println(solution);
				Playfair sol = new Playfair(solution);
				System.out.print("Enter the name of the txt file you want to encrypt: ");
				String fileName = sc.nextLine();
				String text = readFile(fileName);
				System.out.println("Enter steps:");
				int iterationsOnTemp = sc.nextInt();
				SimulatedAnnealing sa = new SimulatedAnnealing(text, iterationsOnTemp);
				
				System.out.println("SA fitness: " + sa.getQuadFitness(text, sol));
				
				PlayfairKey key = sa.findKey(text);
				Playfair pf = new Playfair(key);
				String plainText = pf.decrypt(text);
				System.out.println("plain: " + plainText);
				System.out.println("Maximum fitness found : " + sa.getQuadFitness(text, pf));
				try {
					writeResult("Optimized SA attempt(4Grams) PlainText",plainText);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(choice == 11) {
				/* This option allows us to simply display the matrix of a key*/
				sc.nextLine();
		        System.out.println("Enter a key:");
		        String key = sc.next();
				Playfair playfair = new Playfair(key);
				playfair.showMatrix();
			}else if(choice == 12) {
				System.out.println("Closing Playfair...");
				sc.close();
				SENTINAL = false;
			}else {
				System.out.println("press enter to try again");
			}
			sc.nextLine();
		}//while end
	}

	/* Method for reading files from MyFiles the user selects for a operation */
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
	
	/* Method for writing the result to MyFiles */
	static void writeResult(String txtType, String result) throws IOException {
		System.out.println(result);
		//Print the result to result.txt in MyFiles
		try (PrintWriter out = new PrintWriter("MyFiles\\"+txtType+".txt")) {
		    out.println(result);
		    System.out.println("result written to "+txtType+".txt in MyFiles");
		}
	}
}

