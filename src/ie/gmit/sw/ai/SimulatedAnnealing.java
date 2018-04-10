package ie.gmit.sw.ai;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

/***
 * 
 * @author Conor Tighe
 * This is the class that performs the SA and return the fitness score to the user
 */
public class SimulatedAnnealing {
	
	/***
	 * This class is designed to be flexable for the user experimenting with the tempature, cooldown steps and iteration
	 * while allowing us to use the formula for optimizing SA
	 * @return 2 gram fitness
	 * @return 4 gram fitness
	 * @return Playfair key attempt
	 * @return Get max tempature
	 * @void Set max tempature
	 * @return Get step
	 * @void Set step
	 * @return Get iteration count
	 * @void Set iteration count
	 */
	
	// Variables required for SA
	private double maxTemp,step;
	private int iterationsOnTemp;
	private SecureRandom rand;
	
	// Constructor for applying optimized simulated annealing
	public SimulatedAnnealing(String code,int iterationsOnTemp) {
		this.maxTemp = ((10.0 + 0.087 * (code.length() - 84)));
		this.step = this.maxTemp / 3.0;
		System.out.println("Maximum Tempature: " + this.maxTemp);
		System.out.println("Tempature cooldown: "+ this.step);
		this.iterationsOnTemp=iterationsOnTemp;
	}
	
	// Initiate variables for custom simulated annealing
	public SimulatedAnnealing (double maxTemp,double step,int iterationsOnTemp)
	{
		this.maxTemp=maxTemp;
		this.step=step;
		this.iterationsOnTemp=iterationsOnTemp;
	}
	// get 2 grams fitness
	public double getFitness(String cipherText, Playfair playfair)
	{
		String candidate = playfair.decrypt(cipherText);
		return BigramFrequency.getInstance().getTextFitness(candidate);
	}
	// get 4 gram fitness
	public double getQuadFitness(String cipherText, Playfair playfair)
	{
		String candidate = playfair.decrypt(cipherText);
		return QuadgramFrequency.getInstance().getTextFitness(candidate);
	}
	// find key for cipher using ciphered text and grams flag
	public PlayfairKey findKey(String cipherText, int grams)
	{	
		// for store best fitness
		double bestFitness = Double.NEGATIVE_INFINITY;
		
		// current node
		PlayfairKey parent = PlayfairKey.getRandomKey();
		System.out.println("parent:" + parent);
		// store best key so far
		PlayfairKey bestKey = null;
		// new cipher depending on current node
		Playfair playfair= new Playfair(parent);
		// calculate current node fitness
		double parentFitness;
		if(grams == 2) {
			parentFitness = getFitness(cipherText, playfair);
		}else {
			parentFitness = getQuadFitness(cipherText, playfair);
		}
		long startTime = System.currentTimeMillis();
		// move through out nodes using tempature values
		for (double temp = maxTemp; temp>0; temp-=step)
		{
			// printing out the current values
			System.out.println(temp);
			System.out.println(parentFitness);
			//System.out.println(parent);
			for (int it = 0; it<iterationsOnTemp; it++)
			{
				// create child node of parent
				PlayfairKey child = parent.makeChildKeyFromMatrix();
				
				// set new key using child
				playfair.setKey(child);
				// get child fitness
				double childFitness;
				if(grams == 2) {
					childFitness = getFitness(cipherText, playfair);
				}else {
					childFitness = getQuadFitness(cipherText, playfair);
				}
				// calculate delta value
				double df = childFitness - parentFitness;
				boolean takeChild = false;
				// new key better!!!!
				if (df>0) takeChild = true;
				else if (rand.nextDouble() < Math.exp(df/temp))takeChild=true;
				// use this new node!!!
				if (takeChild)
				{
					parentFitness = childFitness;
					parent=child;
				}
				// we have a new best!!!
				if (parentFitness > bestFitness)
				{
					bestFitness = parentFitness;
					bestKey = parent;
					System.out.println("New best parent node!: " + bestKey + "New best parent fitness!: " + bestFitness);
				}
			}
		}
		
		long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
	    System.out.println("SA complete in " + seconds + " seconds");
	    
		// return back our best key 
	    System.out.println("bk:" + bestKey);
		return bestKey;
	}
	
	// get maximun tempature
	public double getMaxTemp() {
		return maxTemp;
	}
	// set maximum tempature
	public void setMaxTemp(double maxTemp) {
		this.maxTemp = maxTemp;
	}
	// get step
	public double getStep() {
		return step;
	}
	// set step
	public void setStep(double step) {
		this.step = step;
	}
	// get total iterations 
	public int getIterationsOnTemp() {
		return iterationsOnTemp;
	}
	// set total iterations 
	public void setIterationsOnTemp(int iterationsOnTemp) {
		this.iterationsOnTemp = iterationsOnTemp;
	}
}
