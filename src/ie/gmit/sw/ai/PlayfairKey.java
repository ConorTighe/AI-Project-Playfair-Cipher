package ie.gmit.sw.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/***
 * 
 * @author Conor Tighe
 * Here is where we set up the playfair matirx(J is the constant that we remove leaving us with 25 letters) 
 * base on the string passed to this classes constructor, if the key is valid then we can
 * also do the the work for encrypting and decrypting here by swapping the characters, rows and columns 
 */
public class PlayfairKey {

	/***
	 * Takes a PlayfairKey class for building the matrix.
	 * @void Add characters from matrix to map
	 * @return return matrix characters
	 * @return ecrypts by using our character matrix to manipulate the positions
	 * @return decrypts by using our character matrix to manipulate the positions
	 * @return display matrix
	 * @return Create child matrix of playfair cipher
	 * @void switch characters around
	 * @void switch rows around
	 * @void switch columns around
	 */
	
	private char matrix[];

	private Map<Character, Integer> charToIndex;

	// This method creates playfair matrix based on what the key is
	public PlayfairKey(String key) {
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

	// This method checks if matrix is valid and then passes it to out character map
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

	// generate a random key
	public static PlayfairKey getRandomKey() {
		ArrayList<Character> chars = new ArrayList<Character>();
		for (char i = 'A'; i <= 'Z'; i++)
			if (i != PlayfairConstants.EQUAL_CHAR2)
				chars.add(i);
		Collections.shuffle(chars);
		char carray[] = new char[chars.size()];
		for (int i = 0; i < chars.size(); i++)
			carray[i] = chars.get(i);
		return new PlayfairKey(carray);
	}

	// Create child matrix of playfair cipher
	public PlayfairKey makeChildKey() {
		// mimic current matrix
	    char[] newMatrix = matrix.clone();
		Random r = new Random();
		int cas = r.nextInt(50);
		int a,b;
		// manipulate child depending on generate case
		switch (cas){
			// swap the rows of characters in the matrix(random rows)
			case 0:
				a = r.nextInt(5);
				b = r.nextInt(5);
				while (a==b) b=r.nextInt(5);
				swapRows(newMatrix, a, b);
				break;
			// swap the columns of characters in the matrix(random columns)
			case 1:
				a = r.nextInt(5);
				b = r.nextInt(5);
				while (a==b) b=r.nextInt(5);
				swapColumns(newMatrix, a, b);
				break;
			// create new matrix based on current but flip everything back 1 space
			case 2:
				for(int i=0;i<25;i++) newMatrix[i] = matrix[24-i];
				break;
			// flip rows of matrix based on current values of nested loop
			case 3:
				for(int k=0;k<5;k++) for(int j=0;j<5;j++) newMatrix[k*5 + j] = matrix[(4-k)*5+j];
				break;
			case 4:
				// flip columns of matrix based on current values of nested loop
				for(int k=0;k<5;k++) for(int j=0;j<5;j++) newMatrix[j*5 + k] = matrix[(4-j)*5+k];
				break;
			default:
				// simply swap single characters around 
				a = r.nextInt(25);
				b = r.nextInt(25);
				while (a == b)
					b = r.nextInt(25);// randomize positions
				swapChars(newMatrix, a, b);
				break;
		}
		
		return new PlayfairKey(newMatrix);
	}
	// switch characters around
	private void swapChars (char[] matrix, int a, int b)
	{
		char tmp = matrix[a];
		matrix[a] = matrix[b];
		matrix[b] = tmp;
	}
	// switch rows around
	private void swapRows(char[] matrix,int a,int b)
	{
		for (int i=0;i<5;i++)
		{
			swapChars(matrix, a*5+i, b*5+i);
		}
	}
	// switch columns around
	private void swapColumns(char[] matrix,int a, int b)
	{
		for (int i=0;i<5;i++)
		{
			swapChars(matrix, i*5+a, i*5+b);
		}
	}
}