package com.arm.fanucci;

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
	
	public int getPopulationSize() {
		return populationSize;
	}
	
	public float getElitismRate() {
		return elitismRate;
	}
	
	public float getMutationRate() {
		return mutationRate;
	}
	
	public int getMaxIterations() {
		return maxIterations;
	}
	
	public int getMaxHands() {
		return maxHands;
	}
	
	public int getMaxRepeatCount() {
		return maxRepeatCount;
	}
	
	public void setPopulationSize(int size) {
		populationSize = size;
	}
	
	public void setElitismRate(float rate) {
		elitismRate = rate;
	}
	
	public void setMutationRate(float rate) {
		mutationRate = rate;
	}
	
	public void setMaxIterations(int count) {
		maxIterations = count;
	}
	
	public void setMaxHands(int count) {
		maxHands = count;
	}
	
	public void setMaxRepeatCount(int count) {
		maxRepeatCount = count;
	}

}
