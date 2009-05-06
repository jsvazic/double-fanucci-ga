package com.arm.fanucci;

/**
 * Container class modelling the options available for the genetic algorithm
 * simulation.
 * 
 * @author jsvazic
 */
public class SimulatorOptions {
	private int populationSize;
	private float elitismRate;
	private float mutationRate;
	private int maxIterations;
	private int maxHands;
	private int maxRepeatCount;

	/**
	 * Default constructor.
	 */
	public SimulatorOptions() {
	}
	
	/**
	 * Default constructor.  Used to specify all the options for the 
	 * simulator.
	 * 
	 * @param populationSize The size of the population.
	 * @param elitismRate The elitisim rate.
	 * @param mutationRate The mutation rate.
	 * @param maxIterations The maximum number of iterations.
	 * @param maxHands The maximum number of hands to evaluate.
	 * @param maxRepeatCount The maximum repeat count for ideal solutions 
	 * before stopping the iterations.
	 */
	public SimulatorOptions(int populationSize, float elitismRate, 
			float mutationRate, int maxIterations, int maxHands, 
			int maxRepeatCount) {
		
		this.populationSize = populationSize;
		this.elitismRate = elitismRate;
		this.mutationRate = mutationRate;
		this.maxIterations = maxIterations;
		this.maxHands = maxHands;
		this.maxRepeatCount = maxRepeatCount;
	}
	
	/**
	 * Retrieve the population size.
	 * 
	 * @return The population size. 
	 */
	public int getPopulationSize() {
		return populationSize;
	}
	
	/**
	 * Retrieve the elitism rate.
	 * 
	 * @return The elitism size. 
	 */
	public float getElitismRate() {
		return elitismRate;
	}
	
	/**
	 * Retrieve the mutation rate.
	 * 
	 * @return The mutation rate. 
	 */
	public float getMutationRate() {
		return mutationRate;
	}
	
	/**
	 * Retrieve the maximum number of iterations.
	 * 
	 * @return The maximum number of iterations. 
	 */
	public int getMaxIterations() {
		return maxIterations;
	}
	
	/**
	 * Retrieve the maximum number of hands to generate.
	 * 
	 * @return The maximum number of hands to generate. 
	 */
	public int getMaxHands() {
		return maxHands;
	}
	
	/**
	 * Retrieve the maximum repeat count for ideal solutions 
	 * before stopping the iterations.
	 * 
	 * @return The maximum repeat count for ideal solutions 
	 * before stopping the iterations. 
	 */
	public int getMaxRepeatCount() {
		return maxRepeatCount;
	}
	
	/**
	 * Set the population size.
	 * 
	 * @param size The population size.
	 */
	public void setPopulationSize(int size) {
		populationSize = size;
	}
	
	/**
	 * Set the elitism rate.
	 * 
	 * @param rate The elitism rate. 
	 */
	public void setElitismRate(float rate) {
		elitismRate = rate;
	}
	
	/**
	 * Set the mutation rate.
	 * 
	 * @param rate The mutation rate. 
	 */
	public void setMutationRate(float rate) {
		mutationRate = rate;
	}
	
	/**
	 * Set the maximum number of iterations.
	 * 
	 * @param count The maximum number of iterations. 
	 */
	public void setMaxIterations(int count) {
		maxIterations = count;
	}
	
	/**
	 * Set the maximum number of hands to generate.
	 * 
	 * @param count The maximum number of hands to generate. 
	 */
	public void setMaxHands(int count) {
		maxHands = count;
	}
	
	/**
	 * Set the maximum repeat count for ideal solutions 
	 * before stopping the iterations.
	 * 
	 * @param count The maximum repeat count for ideal solutions 
	 * before stopping the iterations. 
	 */
	public void setMaxRepeatCount(int count) {
		maxRepeatCount = count;
	}
}