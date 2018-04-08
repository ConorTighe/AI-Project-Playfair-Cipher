package ie.gmit.sw.ai;

import java.util.List;
/***
 * 
 * @author Conor Tighe
 * This class is resopsible for the encypting/decrypting calls, displaying the matrix for the user and setting the key for the SA iterations.
 */
public class Playfair {

	/***
	 * Takes a PlayfairKey class for building the matrix.
	 * @return encyption results
	 * @return decryption results
	 * @return matrix on screen
	 * @return set new key to try on matrix
	 */
	
	// Return current key
	private PlayfairKey key;
	
	// Pass class key to playfair
	public Playfair (PlayfairKey key)
	{
		this.key=key;
		
	}
	// Pass String key to playfair and then call above constructor 
	public Playfair (String key)
	{
		this.key=new PlayfairKey(key);
	}
	// handles the encryption of text
	public String encrypt(String in)
	{
		// new string
		StringBuilder toReturn = new StringBuilder();
		// use utility call to get a hold of relevant digrams for text
		List<String> digraphs = TextUtils.getDigraphs(in);
		// iterate through digrams
		for (String digraph:digraphs)
		{
			// add new shifted chars to string builder
			toReturn.append(key.encrypt(digraph));
		}
		// send back to user
		return toReturn.toString();
	}
	
	// handles the decryption of text
	public String decrypt(String in)
	{
		// new string
		StringBuilder toReturn = new StringBuilder();
		// use utility call to get a hold of relevant digrams for code
		List<String> digraphs = TextUtils.getDigraphs(in);
		// iterate through digrams
		for (String digraph:digraphs)
		{
			// add new shifted chars to string builder
			toReturn.append(key.decrypt(digraph));
		}
		// send back to user
		return toReturn.toString();
	}

	// Show matrix on screen
	public void showMatrix() {
		System.out.println("Matrix:");
		System.out.println(this.key.toString());
	}
	
	// set playfair key
	public void setKey(PlayfairKey key) {
		this.key = key;
	}

}