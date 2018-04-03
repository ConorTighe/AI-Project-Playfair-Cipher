package Playfair;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class FrequencyParser {

	public static String convertStreamToString(InputStream is) {
		@SuppressWarnings("resource")
		Scanner s = new Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	private static Map<String,Double> getFrequencies(String path) throws FileNotFoundException {
		//System.out.println("path" +path);
		Map<String,Double> frequencies = new HashMap<String, Double>();
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
		String allText = convertStreamToString(in);
		Map<String,Double> count = new HashMap<String, Double>();
		long totalCnt = 0;
		System.out.println("Calculating Frequencies..");
		for (String current : allText.split("\n"))
		{
			String [] split = current.split(" ");
			double cnt = Double.parseDouble(split[1]);
			count.put(split[0], cnt);
			totalCnt+=cnt;
		}
		for (Entry<String, Double> entry : count.entrySet())
		{
			frequencies.put(entry.getKey(), (entry.getValue()+0.0)/totalCnt);
		}
		return frequencies;
	}
	public static Map<String,Double> getBigramFrequencies() throws FileNotFoundException
	{
		System.out.println("Parsing 2gram values..");
		return getFrequencies("2grams.txt");
	}
	public static Map<String,Double> getQuadgramFrequencies() throws FileNotFoundException
	{
		System.out.println("Parsing 4gram values..");
		return getFrequencies("4grams.txt");
	}
}