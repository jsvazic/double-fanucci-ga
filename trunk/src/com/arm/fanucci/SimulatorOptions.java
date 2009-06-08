package com.arm.fanucci;

import com.arm.fanucci.ui.SolutionPanel;

/**
 * Container class modelling the options available for the genetic algorithm
 * simulation.
 * 
 * @author jsvazic
 */
public class SimulatorOptions {
	
	/** The default size for the population. */
	public static final int DEFAULT_POPULATION_SIZE = 256;

	/** The elitism rate for the simulation, where: 0.0 &lt; rate &lt; 1.0 */
	public static final float DEFAULT_ELITISM_RATE = 0.1f;

	/** The mutation rate for the simulation, where: 0.0 &lt; rate &lt; 1.0 */
	public static final float DEFAULT_MUTATION_RATE = 0.15f;
	
	/** Maximum number of iterations for the simulation. */
	public static final int DEFAULT_ITERATIONS = 128;
	
	/** Maximum number of hands to generate. */
	public static final int DEFAULT_SLOT_COUNT = 4;
	
	/** Maximum repeat count for a best fit before exiting. */
	public static final int DEFAULT_REPEAT_COUNT = 32;

	private int populationSize;
	private float elitismRate;
	private float mutationRate;
	private int maxIterations;
	private int maxHands;
	private int maxRepeatCount;
	private int[] slotOrder;

	/**
	 * Default constructor.
	 */
	public SimulatorOptions() {
		this(1024, 0.1f, 0.15f, 256, 4, 32, new int[] { SolutionPanel.GAMBIT, 
					SolutionPanel.MIND, SolutionPanel.BODY, 
					SolutionPanel.SPIRIT, SolutionPanel.SIDEKICK });
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
			int maxRepeatCount, int[] slotOrder) {
		
		this.populationSize = populationSize;
		this.elitismRate = elitismRate;
		this.mutationRate = mutationRate;
		this.maxIterations = maxIterations;
		this.maxHands = maxHands;
		this.maxRepeatCount = maxRepeatCount;
		this.slotOrder = new int[slotOrder.length];
		System.arraycopy(slotOrder, 0, this.slotOrder, 0, slotOrder.length);
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
	public int getMaxSlots() {
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
	 * Method used to get the order the slots should be filled in.  The 
	 * constants for the array can be found in the <code>SolutionPanel</code>
	 * class. 
	 * 
	 * @return The order of the slots to fill in.
	 * 
	 * @see com.arm.fanucci.ui.SolutionPanel
	 */
	public int[] getSolutionPanelOrder() {
		return slotOrder;
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
	public void setMaxSlots(int count) {
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
	
	/**
	 * Method used to set the order the slots should be filled in.  The 
	 * constants for the array can be found in the <code>SolutionPanel</code>
	 * class. 
	 * 
	 * @param slotOrder The order of the slots to fill in.
	 * 
	 * @see com.arm.fanucci.ui.SolutionPanel
	 */
	public void setSolutionPanelOrder(int[] slotOrder) {
		this.slotOrder = new int[slotOrder.length];
		System.arraycopy(slotOrder, 0, this.slotOrder, 0, slotOrder.length);
	}

}