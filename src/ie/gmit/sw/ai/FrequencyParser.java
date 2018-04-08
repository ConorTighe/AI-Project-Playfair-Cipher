package ie.gmit.sw.ai;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
/***
 * 
 * @author Conor Tighe
 * This is the class for parsing the gram values from the text file into a format suitable for our SA process
 */
public class FrequencyParser {

	/***
	 * handles the files path and the parsing of the text file it leads to.
	 * @return frequencies that are stored in the gram file requested 
	 * @return the 2 gram text file
	 * @return the 4 gram text file
	 * @throws FileNotFoundException
	 */
	// Turn the file stream to a sting that we can iterate through
	public static String convertStreamToString(InputStream is) {
		@SuppressWarnings("resource")
		Scanner s = new Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	// Use the file path to remove the values from the grams files so we can start applying SA using the digrams
	private static Map<String,Double> getFrequencies(String path) throws FileNotFoundException {
		Map<String,Double> frequencies = new HashMap<String, Double>();
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
		String allText = convertStreamToString(in);
		Map<String,Long> count = new HashMap<String,Long>();
		long totalCnt = 0;
		for (String current : allText.split("\n"))
		{
			String [] split = current.split(" ");
			double tempcnt = Double.parseDouble(split[1]);
			long cnt = (long)tempcnt;
			count.put(split[0], cnt);
			totalCnt+=cnt;
		}
		for (Entry<String, Long> entry : count.entrySet())
		{
			frequencies.put(entry.getKey(), (entry.getValue()+0.0)/totalCnt);
		}
		return frequencies;
	}
	// Return 2grams.txt
	public static Map<String,Double> getBigramFrequencies() throws FileNotFoundException
	{
		return getFrequencies("2grams.txt");
	}
	// Return 4grams.txt
	public static Map<String,Double> getQuadgramFrequencies() throws FileNotFoundException
	{
		return getFrequencies("4grams.txt");
	}
}