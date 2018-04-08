package ie.gmit.sw.ai;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

/***
 * 
 * @author Conor Tighe
 * Here is where we set up the playfair matirx(J is the constant that we remove leaving us with 25 letters) 
 * base on the string passed to this classes constructor, if the key is valid then we can
 * also do the the work for encrypting and decrypting here by swapping the characters, rows and columns 
 * Child keys work better with the SA when done with a string so I used that which resulted in better SA, This class can take both
 */
public class PlayfairKey {

	/***
	 * Takes a PlayfairKey class for building the matrix.
	 * @return GET key as string
	 * @return Add characters from matrix to map
	 * @return return matrix characters
	 * @return ecrypts by using our character matrix to manipulate the positions
	 * @return decrypts by using our character matrix to manipulate the positions
	 * @return display matrix
	 * @return Create child with key
	 * @return Create child with matrix(tried to implement this but no where near as accurate as the key version)
	 * @void switch characters around
	 * @void switch rows around
	 * @void switch columns around
	 */

	private char matrix[];

	private String keyVal;
	private Map<Character, Integer> charToIndex;

	// use this to get a hold of the original key string we used to create the matrix
	public String getKeyVal() {
		return keyVal;
	}

	// This method creates playfair matrix based on what the key is
	public PlayfairKey(String key) {
		this.keyVal = key;
		key = TextUtils.prepareKey(key);
		Set<Character> available = new TreeSet<Character>();
		for (char i = 'A'; i <= 'Z'; i++)
			available.add(i);
		available.remove(PlayfairConstants.EQUAL_CHAR2);
		matrix = new char[25];
		int matrixInd = 0;
		for (char c : key.toCharArray()) {
			if (available.contains(c)) {
				available.remove(c);
				matrix[matrixInd++] = c;
			}
		}
		for (char c : available)
			matrix[matrixInd++] = c;
		charToIndex = new HashMap<Character, Integer>();
		populateCharToIndexMap();
	}

	// Constructor to create key from matrix
	public PlayfairKey(char matrix[]) {
		if (matrix.length != 25)
			throw new IllegalArgumentException(
					"The size of the matrix must be 25");
		for (char c : matrix)
			if (!Character.isUpperCase(c))
				throw new IllegalArgumentException(
						"Matrix may only contain uppercase alphabetic chars and can not contain char : "
								+ PlayfairConstants.EQUAL_CHAR2);
		this.matrix = matrix;
		charToIndex = new HashMap<Character, Integer>();
		populateCharToIndexMap();
	}

	// populates index map
	private void populateCharToIndexMap() {
		for (int i = 0; i < 25; i++)
			charToIndex.put(matrix[i], i);
	}

	// return index of character passed
	private int getCharToIndex(char c) {
		return charToIndex.get(c);
	}

	// ecrypts by using our character matrix to manipulate the positions
	public String encrypt(String in) {
		StringBuilder sb = new StringBuilder();
		// check if input valid for playfair cipher
		if (in.length() != 2)
			throw new IllegalArgumentException(
					"Input string must be of length 2");
		// store values of stirng pos in chars
		char ac = in.charAt(0);
		char bc = in.charAt(1);
		// convert those values to indexs
		int a = getCharToIndex(ac);
		int b = getCharToIndex(bc);
		// does it fit 5x5? if so manipulate places
		if ((a / 5) == (b / 5)) {
			int y = a / 5;
			int ax = a % 5;
			int bx = b % 5;
			ax = (ax + 1) % 5;
			bx = (bx + 1) % 5;
			sb.append(matrix[y * 5 + ax]);
			sb.append(matrix[y * 5 + bx]);
			return sb.toString();
		}
		// does it fit less then 5x5? if so manipulate places
		if ((a % 5) == (b % 5)) {
			int x = a % 5;
			int ay = a / 5;
			int by = b / 5;
			ay = (ay + 1) % 5;
			by = (by + 1) % 5;
			sb.append(matrix[ay * 5 + x]);
			sb.append(matrix[by * 5 + x]);
			return sb.toString();
		}
		int ax = a % 5;
		int ay = a / 5;
		int bx = b % 5;
		int by = b / 5;
		sb.append(matrix[ay * 5 + bx]);
		sb.append(matrix[by * 5 + ax]);
		return sb.toString();
	}

	// decrypts by using our character matrix to manipulate the positions
	public String decrypt(String in) {
		StringBuilder sb = new StringBuilder();
		// check if input valid for playfair cipher
		if (in.length() != 2)
			throw new IllegalArgumentException(
					"Input string must be of length 2");
		// store values of stirng pos in chars
		char ac = in.charAt(0);
		char bc = in.charAt(1);
		// convert those values to indexs
		int a = getCharToIndex(ac);
		int b = getCharToIndex(bc);
		// does it fit 5x5? if so manipulate places
		if ((a / 5) == (b / 5)) {
			int y = a / 5;
			int ax = a % 5;
			int bx = b % 5;
			ax--;
			if (ax < 0)
				ax += 5;
			bx--;
			if (bx < 0)
				bx += 5;
			sb.append(matrix[y * 5 + ax]);
			sb.append(matrix[y * 5 + bx]);
			return sb.toString();
		}
		// does it fit less than 5x5? if so manipulate places
		if ((a % 5) == (b % 5)) {
			int x = a % 5;
			int ay = a / 5;
			int by = b / 5;
			ay--;
			if (ay < 0)
				ay += 5;
			by--;
			if (by < 0)
				by += 5;
			sb.append(matrix[ay * 5 + x]);
			sb.append(matrix[by * 5 + x]);
			return sb.toString();
		}
		int ax = a % 5;
		int ay = a / 5;
		int bx = b % 5;
		int by = b / 5;
		sb.append(matrix[ay * 5 + bx]);
		sb.append(matrix[by * 5 + ax]);
		return sb.toString();
	}

	@Override // display matrix
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sb.append(matrix[i * 5 + j] + " ");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("\n");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	// use fisher shuffle to move key around
	public static String fisherShuffle(char[] key){
		 int index;
		 Random random = ThreadLocalRandom.current();
		 for (int i = key.length - 1; i > 0; i--) {
			 index = random.nextInt(i + 1);
			 if (index != i) {
				 key[index] ^= key[i];
				 key[i] ^= key[index];
				 key[index] ^= key[i];
			 }
		 }
		return new String(key);
    }

	// generate a random key
	public static PlayfairKey getRandomKey() {
		ArrayList<Character> chars = new ArrayList<Character>();
		for (char i = 'A'; i <= 'Z'; i++)
			if (i != PlayfairConstants.EQUAL_CHAR2)
				chars.add(i);
		char carray[] = new char[chars.size()];
		for (int i = 0; i < chars.size(); i++)
			carray[i] = chars.get(i);
		String randKey = fisherShuffle(carray);
		PlayfairKey p = new PlayfairKey(randKey);
		return p;
	}

	// make new key off the string value in this class
	public PlayfairKey makeChildKey() {
	    String newKey = getKeyVal();
	    //System.out.println("newKey: " + newKey);
		SecureRandom r = new SecureRandom();
		//Random integer value
		int num = r.nextInt(100);
		
		if(num >= 0 && num < 2) {
			newKey = swapRows(newKey, r.nextInt(4), r.nextInt(4));
			//System.out.print("newKey1:" + newKey);
		} else if ( num >= 2 && num < 4) {
			newKey = swapCols(newKey, r.nextInt(4), r.nextInt(4));
			//System.out.print("newKey2:" + newKey);
		} else if ( num >= 4 && num < 6) {
			newKey = flipRows(newKey);
			//System.out.print("newKey3:" + newKey);
		} else if ( num >= 6 && num < 8) {
			newKey = flipCols(newKey);
			//System.out.print("newKey4:" + newKey);
		} else if ( num >= 8 && num < 10) {
			newKey = new StringBuffer(newKey).reverse().toString();
			//System.out.print("newKey5:" + newKey);
		} else {
			//System.out.println("newKey6: " + newKey);
			//System.out.println(newKey.length()-1);
			int a = r.nextInt(newKey.length()-1);
			int b = r.nextInt(newKey.length()-1);
			b = (a == b) ? (b == newKey.length()-1) ? b - 1 : b + 1 : r.nextInt(newKey.length()-1);
			char[] res = newKey.toCharArray();
			char tmp = res[a];
			res[a] = res[b];
			res[b] = tmp;
			newKey = String.copyValueOf(res);
			return new PlayfairKey(newKey);
		} // end if else for % of time do x
		
		return new PlayfairKey(newKey);
		
	}
	
	// Generate key by passing matrix in this class
	public PlayfairKey makeChildKeyFromMatrix() {
		// mimic current matrix
	    char[] newMatrix = matrix.clone();
		Random r = new Random();
		int num = r.nextInt(100);
		int a,b;
		// manipulate child depending on generate case
		if(num >= 0 && num < 2) { // swap the rows of characters in the matrix
				a = r.nextInt(5);
				b = r.nextInt(5);
				while (a==b) b=r.nextInt(5);
				swapMatrixRows(newMatrix, a, b);
		} else if ( num >= 2 && num < 4) {// swap the columns of characters in the matrix
				a = r.nextInt(5);
				b = r.nextInt(5);
				while (a==b) b=r.nextInt(5);
				swapMatrixColumns(newMatrix, a, b);
		} else if ( num >= 4 && num < 6) { // create new matrix based on current but flip rows
				for(int i=0;i<25;i++) newMatrix[i] = matrix[24-i];
				
		} else if ( num >= 6 && num < 8) { // create new matrix based on current but flip columns
				for(int k=0;k<5;k++) for(int j=0;j<5;j++) newMatrix[k*5 + j] = matrix[(4-k)*5+j];
				
		} else if ( num >= 8 && num < 10) {// reverse matrix
				for(int k=0;k<5;k++) for(int j=0;j<5;j++) newMatrix[j*5 + k] = matrix[(4-j)*5+k];
				
		} else {
				// simply swap single characters around 
				a = r.nextInt(25);
				b = r.nextInt(25);
				while (a == b)
					b = r.nextInt(25);// randomize positions
				swapMatrixChars(newMatrix, a, b);
		}
		
		return new PlayfairKey(newMatrix);
	}
	
	// Turn rows upside down
	private String flipRows(String key) {
		String[] rows = new String[5];
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < 5; i++) {
			rows[i] = key.substring(i*5, i*5 + 5);
			rows[i] = new StringBuffer(rows[i]).reverse().toString();
			sb.append(rows[i]);
		}
		return sb.toString();
	}
	
	// Turn columns upside down
	private String flipCols(String key) {
		char[] cols = key.toCharArray();
		int length = key.length() - (key.length()/5);
		
		for(int i = 0; i < key.length() / 5; i++) {
			for(int j = 0; j < key.length() / 5; j++) {
				char tmp = key.charAt(i*5 + j);
				cols[(i*5) + j] =  key.charAt(length + j);
				cols[length + j] =  tmp;
			}
			length -= 5;
		}
		return new String(cols);
	}
	
	//Move rows around
	private String swapRows(String key, int row1, int row2) {	
		return (row1 == row2) ? swapRows(key, new SecureRandom().nextInt(4), new SecureRandom().nextInt(4)) :  rearrange(key, row1, row2, true);
	}
	
	// Move columns around
	private String swapCols(String key, int col1, int col2) {
		return (col1 == col2) ? swapCols(key, new SecureRandom().nextInt(4), new SecureRandom().nextInt(4)) : rearrange(key, col1, col2, false);
	}
	
	// Rearrangement of key
	private String rearrange(String key, int a, int b, boolean row) {
			char[] newKey = key.toCharArray();
			if(row) {
				a *= 5;
				b *= 5;
			} 
			for(int i = 0; i < key.length() / 5 ; i++) {
				int index = (row) ? i : i*5;
				char tmp =  newKey[(index + a)];
				newKey[(index + a)] = newKey[(index + b)];
				newKey[(index + b)] = tmp;				
			}
			return new String(newKey);
	}
	
	// switch characters around in matrix
	private void swapMatrixChars (char[] matrix, int a, int b)
	{
		char tmp = matrix[a];
		matrix[a] = matrix[b];
		matrix[b] = tmp;
	}
	// switch rows around in matrix
	private void swapMatrixRows(char[] matrix,int a,int b)
	{
		for (int i=0;i<5;i++)
		{
			swapMatrixChars(matrix, a*5+i, b*5+i);
		}
	}
	// switch columns around matrix
	private void swapMatrixColumns(char[] matrix,int a, int b)
	{
		for (int i=0;i<5;i++)
		{
			swapMatrixChars(matrix, i*5+a, i*5+b);
		}
	}
}