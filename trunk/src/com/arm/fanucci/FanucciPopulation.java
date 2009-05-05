package com.arm.fanucci;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
	private Importer importer;
	
	/**
	 * Default constructor. Used to initialize the simulation.
	 * 
	 * @param size The size of the population.
	 */
	public FanucciPopulation(int size, Importer importer) {
		this.population = new ArrayList<Chromosome>(size);
		this.importer = importer;
		generateInitialPopulation(size);
	}
	
	/**
	 */
	protected void generateInitialPopulation(int populationSize) {
		try {
			Card[] allCards = importer.getAllCards().toArray(new Card[0]);
			for (int i = 0; i < populationSize; i++) {							
				Set<Card> hand = new TreeSet<Card>();
				
				// Randomly pick a suit.
				int idx = rand.nextInt(allCards.length);
				short suit = allCards[idx].getSuit();
				
				// Add the top two cards for the suit.
				Iterator<Card> it = importer.getCardsForSuit(suit).iterator();
				for (int j = 0; j < 2 && it.hasNext(); j++) {
					hand.add(it.next());
				}
				
				// Add the top two cards for any other cards from the same 
				// group
				short group   = FanucciUtil.getGroupId(suit);
				short[] suits = FanucciUtil.getSuitsForGroup(group);
				for (short suitId : suits) {
					if (suitId == suit) {
						continue;
					}
					it = importer.getCardsForSuit(suitId).iterator();
					for (int j = 0; j < 2 && it.hasNext() && 
							hand.size() < 4; j++) {
						
						hand.add(it.next());
					}
					if (hand.size() == 4) {
						break;
					}
				}
				
				// Randomly add any remaining cards as required.
				for (int j = 0; j < (3 - hand.size()); j++) {
					// Randomly add cards
					idx = rand.nextInt(allCards.length);
					hand.add(allCards[idx]);
				}
				
				population.add(new FanucciChromosome(hand, importer));
			}
			Collections.sort(population);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}