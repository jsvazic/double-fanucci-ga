package com.arm.fanucci;

import com.arm.genetic.Chromosome;

/**
 * Main driver for the Hello World Genetic Algorithms simulation.
 * 
 * @author jsvazic
 */
public class FanucciCalc {	
	private SimulatorOptions simOptions;
	
	public FanucciCalc(SimulatorOptions options) {
		simOptions = options;
	}
	
	public Chromosome[] execute(Deck deck) {
		final int maxHands = simOptions.getMaxHands();
		final int maxIterations = simOptions.getMaxIterations();
		final float elitismRate = simOptions.getElitismRate();
		final float mutationRate = simOptions.getMutationRate();
		final int maxRepeatCount = simOptions.getMaxRepeatCount();
		
		Chromosome[] arr = new Chromosome[maxHands];
		
		for (int i = 0; i < maxHands && deck.size() > 0; i++) {
			FanucciPopulation population =  new FanucciPopulation(deck, 
					simOptions.getPopulationSize());

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
				deck.removeCard(c);
			}
		}
		
		return arr;
	}
}
