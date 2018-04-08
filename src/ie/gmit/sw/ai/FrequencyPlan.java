package ie.gmit.sw.ai;
/***
 * 
 * @author Conor Tighe
 * This is the interface we will base the 2gram and 4gram classes on for the frequency in the text files, we need 
 * to know the fitness results along with the grams file name that we are attempting to receive, since we get the user
 * to provide the file so we will let them know the .txt file the program is currently looking for.
 */
public interface FrequencyPlan {
	// Return fitness results
	public double getTextFitness (String text);
	// Tell user the name of the digrams file we are using
	public void getGram ();
}