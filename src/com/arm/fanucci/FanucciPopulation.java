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
	
	/**
	 * Default constructor. Used to initialize the simulation.
	 * 
	 * @param size The size of the population.
	 */
	public FanucciPopulation(int size) {
		this.population = new ArrayList<Chromosome>(size);
		generateInitialPopulation(size);
	}
	
	/**
	 */
	protected void generateInitialPopulation(int populationSize) {
		System.out.println("Calling generateInitialPopulation()");
		new Exception().printStackTrace();
		System.out.println();
		try {
			Card[] allCards = Deck.getInstance().getAllCards();
			for (int i = 0; i < populationSize; i++) {							
				Set<Card> hand = new TreeSet<Card>();
				for (int j = 0; j < 4; j++) {
					// Randomly add cards
					int idx = rand.nextInt(allCards.length);
					hand.add(allCards[idx]);
				}
				
				population.add(new FanucciChromosome(hand));
			}
			Collections.sort(population);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}