package ie.gmit.sw.ai;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class QuadgramFrequency implements FrequencyPlan {
	private double minValue = Double.POSITIVE_INFINITY;
	private static QuadgramFrequency instance;
	public static QuadgramFrequency getInstance() {
		if (instance==null) instance = new QuadgramFrequency();
		return instance;
	}
	private Map<String,Double> scores;
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
			System.out.println(minValue);
			//System.out.println(scores);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public double getTextFitness(String text) {
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
}