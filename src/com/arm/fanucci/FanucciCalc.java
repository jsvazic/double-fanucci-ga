package com.arm.fanucci;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
		int maxHands = simOptions.getMaxSlots();
		int maxIterations = simOptions.getMaxIterations();
		float elitismRate = simOptions.getElitismRate();
		float mutationRate = simOptions.getMutationRate();
		int maxRepeatCount = simOptions.getMaxRepeatCount();
		if (maxRepeatCount < 1 || maxRepeatCount > maxIterations) {
			maxRepeatCount = maxIterations;
		}
		
		List<Chromosome> chromosomeList = new LinkedList<Chromosome>();
		
		for (int i = 0; i < maxHands && deck.size() > 0; i++) {
			Card[] cardArr = deck.toArray(new Card[0]);
			Population population =  new Population(
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
			Chromosome bestChromosome = population.getBestChromosome();
			chromosomeList.add(bestChromosome);
			for (Card c : bestChromosome.getCards()) {
				deck.remove(c);
			}
		}
		
		Collections.sort(chromosomeList);
		return chromosomeList.toArray(new Chromosome[0]);
	}
}