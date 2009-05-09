package com.arm.fanucci;

import java.util.Set;

import com.arm.genetic.Chromosome;

/**
 * Main driver for the Hello World Genetic Algorithms simulation.
 * 
 * @author jsvazic
 */
public class FanucciCalc {	
	private SimulatorOptions simOptions;
	
	/**
	 * Default constructor.
	 * 
	 * @param options The options for the simulation.
	 */
	public FanucciCalc(SimulatorOptions options) {
		simOptions = options;
	}
	
	/**
	 * Method to execute the genetic algoritm simulation for the given
	 * <code>Deck</code>.
	 * 
	 * @param deck The <code>Deck</code> containing the Fanucci cards to be
	 * used for the simulation.
	 * 
	 * @return The unique optimal solution for each hand.
	 */
	public Chromosome[] execute(Set<Card> deck) {
		int maxHands = simOptions.getMaxHands();
		int maxIterations = simOptions.getMaxIterations();
		float elitismRate = simOptions.getElitismRate();
		float mutationRate = simOptions.getMutationRate();
		int maxRepeatCount = simOptions.getMaxRepeatCount();
		if (maxRepeatCount < 1 || maxRepeatCount > maxIterations) {
			maxRepeatCount = maxIterations;
		}
		
		Chromosome[] arr = new Chromosome[maxHands];
		
		for (int i = 0; i < maxHands && deck.size() > 0; i++) {
			Card[] cardArr = deck.toArray(new Card[0]);
			FanucciPopulation population =  new FanucciPopulation(
					cardArr, simOptions.getPopulationSize());

			Chromosome best = population.getBestChromosome();
			double lastFitness = best.getFitness(); 
			int count = 1;
			for (int j = 0; j < maxIterations && count < maxRepeatCount; j++) {				
				// Evolve the population
				population.evolve(elitismRate, mutationRate);
				
				// Get the best chromosome and see how many times this level
				// of fitness has been encountered.  Break out of the evolution
				// early if we see the same fitness level more often than not.
				best = population.getBestChromosome();
				if (best.getFitness() == lastFitness) {
					++count;
				} else {
					lastFitness = best.getFitness();
					count = 1;
				}
			}
			
			// Save the best hand so far and adjust the remaining cards for 
			// the next hand.
			arr[i] = population.getBestChromosome();
			for (Card c : ((FanucciChromosome) arr[i]).getCards()) {
				deck.remove(c);
			}
		}
		
		return arr;
	}
}