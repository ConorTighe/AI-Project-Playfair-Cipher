package ie.gmit.sw.ai;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/***
 * 
 * @author Conor Tighe
 * This is the class that handles that calculation of the frequencies using the 2gram diagrams
 */
public class BigramFrequency implements FrequencyPlan{

	/***
	 * Uses the singlton design pattern for the 2gram diagrams 
	 * @return fitness results
	 * @void Inform user of text file used
	 */
	// Make this class a singleton
	private static BigramFrequency instance;
	public static BigramFrequency getInstance() {
		// if none create new BigramFrequency Instance
		if (instance==null) instance = new BigramFrequency();
		return instance;
	}
	private Map<String,Double> scores;
	/* This method id for calculating the bigram frequency scores */
	private BigramFrequency ()
	{
		try {
			// Map for storing parsed frequencies 
			Map<String,Double> frequencies = FrequencyParser.getBigramFrequencies();
			
			// Move though alphabet
			for (char i='A';i<='Z';i++)
			{
				// If NOT a defined PlayfairConstant(Letter Q or J, X for dups)
				if (i!=PlayfairConstants.EQUAL_CHAR2)
				{
					// Build a string and apply estimated CHAR
					StringBuilder sb = new StringBuilder();
					// ADD Q constant
					sb.append(PlayfairConstants.EQUAL_CHAR2);
					sb.append(i);
					double toAdd = frequencies.get(sb.toString());
					frequencies.put(sb.toString(),0.0);
					sb= new StringBuilder();
					// ADD J constant
					sb.append(PlayfairConstants.EQUAL_CHAR1);
					sb.append(i);
					double freq=frequencies.get(sb.toString());
					frequencies.put(sb.toString(), freq+toAdd);
					
					sb = new StringBuilder();
					sb.append(i);
					// ADD J constant
					sb.append(PlayfairConstants.EQUAL_CHAR2);
					toAdd = frequencies.get(sb.toString());
					frequencies.put(sb.toString(),0.0);
					sb= new StringBuilder();
					sb.append(i);
					// ADD Q constant
					sb.append(PlayfairConstants.EQUAL_CHAR1);
					freq=frequencies.get(sb.toString());
					frequencies.put(sb.toString(), freq+toAdd);
				}
				else
				{
					StringBuilder sb = new StringBuilder();
					sb.append(PlayfairConstants.EQUAL_CHAR2);
					sb.append(PlayfairConstants.EQUAL_CHAR2);
					double toAdd = frequencies.get(sb.toString());
					frequencies.put(sb.toString(),0.0);
					sb= new StringBuilder();
					sb.append(PlayfairConstants.EQUAL_CHAR1);
					sb.append(PlayfairConstants.EQUAL_CHAR1);
					double freq=frequencies.get(sb.toString());
					frequencies.put(sb.toString(), freq+toAdd);
				}
					
			}
			for (char i='A';i<='Z';i++)
			{
				if (i!=PlayfairConstants.INSERT_BETWEEN_SAME)
				{
					StringBuilder sb = new StringBuilder();
					sb.append(i);
					sb.append(i);
					double toAdd=frequencies.get(sb.toString());
					
					sb= new StringBuilder();
					sb.append(i);
					sb.append(PlayfairConstants.INSERT_BETWEEN_SAME);
					double freq = frequencies.get(sb.toString());
					frequencies.put(sb.toString(), freq+toAdd);
					
					sb= new StringBuilder();
					sb.append(PlayfairConstants.INSERT_BETWEEN_SAME);
					sb.append(i);
					freq = frequencies.get(sb.toString());
					frequencies.put(sb.toString(), freq+toAdd);
				}
			}
			double sum=0;
			for (Double d: frequencies.values())sum+=d;
			for (Map.Entry<String, Double> entry:frequencies.entrySet())
			{
				entry.setValue(entry.getValue()/sum);
			}
			
			scores = new HashMap<String, Double>();
			for (Map.Entry<String, Double> entry : frequencies.entrySet())
			{
				scores.put(entry.getKey(), Math.log(entry.getValue()));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	// Return fitness results
	public double getTextFitness(String text) {
		double result = 0;
		String preptext = TextUtils.prepareText(text);
		for (int i=0;i<preptext.length()-1;i++)
		{
			String bigram = preptext.substring(i, i+2);
			result += scores.get(bigram);
		}
		return result;
	}
	@Override
	
	public void getGram() {
		System.out.println("Using the 2grams.txt file..");
	}
}