package ie.gmit.sw.ai;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/***
 * 
 * @author Conor Tighe
 * This is the class that handles that calculation of the frequencies using the 4gram diagrams
 */
public class QuadgramFrequency implements FrequencyPlan {
	
	/***
	 * Uses the singlton design pattern for the 4gram diagrams 
	 * @return fitness results
	 * @void Inform user of txt file used
	 */
	
	private double minValue = Double.POSITIVE_INFINITY;
	// make this class a singlton
	private static QuadgramFrequency instance;
	public static QuadgramFrequency getInstance() {
		if (instance==null) instance = new QuadgramFrequency();
		return instance;
	}
	private Map<String,Double> scores;
	// where the quad scores get calculated
	private QuadgramFrequency ()
	{
		try {
			Map<String,Double> frequencies = FrequencyParser.getQuadgramFrequencies();
			scores = new HashMap<String, Double>();
			for (Map.Entry<String, Double> entry : frequencies.entrySet())
			{
				double val =  Math.log(entry.getValue());
				scores.put(entry.getKey(), val);
				if (minValue > val) minValue = val;
			}
			//System.out.println(minValue);
			//System.out.println(scores);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	// return the fitness of the frequencies
	public double getTextFitness(String text) {
		//getGram();
		double result = 0;
		String preptext = TextUtils.prepareText(text);
		for (int i=0;i<preptext.length()-3;i++)
		{
			String quadgram = preptext.substring(i, i+4);
			if (scores.containsKey(quadgram))
			result += scores.get(quadgram);
			else result += minValue;
		}
		return result;
	}
	@Override
	public void getGram() {
		System.out.println("Scoring with the 4grams..");
	}
}