package com.arm.fanucci;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import com.arm.genetic.Chromosome;
import com.arm.genetic.Population;

/**
 * Class modeling a Genetic Algorithm population for the Hello World 
 * simulation.
 *  
 * @author jsvazic
 */
public class FanucciPopulation extends Population {
	private static final Random rand = new Random(System.currentTimeMillis());
	private Card[] allCards;
	
	/**
	 * Default constructor. Used to initialize the simulation.
	 * 
	 * @param size The size of the population.
	 */
	public FanucciPopulation(Card[] deck, int size) {
		this.population = new ArrayList<Chromosome>(size);
		this.allCards = deck;
		generateInitialPopulation(size);
	}
	
	/**
	 */
	protected void generateInitialPopulation(int populationSize) {
		try {
			for (int i = 0; i < populationSize; i++) {							
				Set<Card> hand = new TreeSet<Card>();
				for (int j = 0; j < 4; j++) {
					// Randomly add cards
					int idx = rand.nextInt(allCards.length);
					hand.add(allCards[idx]);
				}
				
				population.add(new FanucciChromosome(this, hand));
			}
			Collections.sort(population);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method to retrieve all the <code>Card</code>s available to the 
	 * current population.
	 * 
	 * @return The <code>Card</code>s available to the current population.
	 */
	public Card[] getDeck() {
		return allCards;
	}
}