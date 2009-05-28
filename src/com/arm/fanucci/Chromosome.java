package com.arm.fanucci;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class modeling a Fanucci <i>slot</i>, which is a collection of at most 4 
 * cards.  Slots in Double Fanucci are defined for Body, Mind, Spirit and the 
 * Fanucci Gambit.  Additional slots are available for sidekicks as well.
 * 
 * @author jsvazic
 *
 */
public class Chromosome implements Comparable<Chromosome> {
	private Set<Card> hand;
	private Population population;
	private double fitness;
	private static final Random rand = new Random(System.currentTimeMillis());
	
	/**
	 * Default constructor.  Note that a maximum of 4 cards is allowed per 
	 * slot.
	 * 
	 * @param cards The set of cards for the slot.
	 * 
	 * @throws NullPointerException Thrown if <code>cards</code> is 
	 * <code>null</code>.
	 */
	public Chromosome(Population population, Set<Card> cards) {
		if (cards == null) {
			throw new NullPointerException("'cards' cannot be null.");
		}
		
		this.population = population;
		this.hand = new TreeSet<Card>(cards);
		updateFitness();
	}

	/**
	 * Method to mutate the chromosome.
	 */
	public Chromosome mate(final Chromosome mate) {
		Card[] parent1 = getCards();
		Card[] parent2 = mate.getCards();
		int idx1 = (int) (parent1.length / 2);
		int idx2 = (int) (parent2.length / 2);
		
		// Take a random sample from each parent, and merge them into a 
		// new set.
		Set<Card> cards = new TreeSet<Card>();
		for (int i = 0; i < idx1; i++) {
			cards.add(parent1[i]);
		}
		
		for (int i = idx2; i < parent2.length; i++) {
			cards.add(parent2[i]);
		}
		
		// Trim the number of cards if necessary.  We're already sorting 
		// based on group, value and suit so just remove the last few
		// items from the set.
		if (cards.size() > 4) {
			Iterator<Card> it = cards.iterator();
			int idx = 0;
			while (it.hasNext()) {
				it.next();
				// Fancy logic here.  Increment the counter after
				// comparing it to the remove condition.
				if (idx++ > 3) {
					it.remove();
				}
			}
		}
		
		// Return the new "child" from the mated chromosomes.
		Chromosome child = new Chromosome(population, cards);

		return child;
	}

	/**
	 * Method used to mate this chromosome with another, producing an 
	 * offspring.
	 * 
	 * @param mate The <code>Chromosome</code> to mate with.
	 * 
	 * @return The resulting <code>Chomosome</code> after mating.
	 */
	public void mutate() {
		// Randomly mutate a card by removing it from the set and replacing 
		// it with a new card.
		Card[] myArr = getCards();
		Card[] oArr  = getDifference(myArr);
		
		if (oArr.length < 1) {
			return;
		}
		
		hand.remove(myArr[rand.nextInt(myArr.length)]);
		hand.add(oArr[rand.nextInt(oArr.length)]);
		
		updateFitness();
	}
	
	/**
	 * Helper method used to update the fitness calculation of the 
	 * chromosome.
	 */
	private void updateFitness() {
		// Do not let more than 3 cards from a single suit in.
		short dominantGroup  = IFanucci.GROUP_UNKNOWN;
		short lastGroup      = IFanucci.GROUP_UNKNOWN;
		short lastSuit       = IFanucci.SUIT_UNKNOWN;
		short suitCount      = 0;
		short groupCount     = 0;
		short bestGroupCount = 0;
		Map<Short, Short> groupValues = new HashMap<Short, Short>(2);
		
		for (Card c : hand) {
			if (c.group != lastGroup) {
				lastGroup = c.group;
				if (lastGroup != IFanucci.GROUP_FACE_CARDS) {
					groupValues.put(lastGroup, c.value);
					++groupCount;
					
					// Discourage more than 2 groups
					if (groupCount > 2) {
						fitness = Double.MAX_VALUE;
						return;
					}
					
					if (c.value > bestGroupCount) {
						dominantGroup = lastGroup;
						bestGroupCount = c.value;
					}
				} else {
					groupValues.put(lastGroup, (short) 25);
				}
			} else {
				if (c.group != IFanucci.GROUP_FACE_CARDS) {
					short v = (short) (groupValues.get(lastGroup) + c.value);
					groupValues.put(lastGroup, v);
					if (v > bestGroupCount) {
						dominantGroup  = lastGroup;
						bestGroupCount = v;
					}
				} else {
					groupValues.put(lastGroup, 
							(short) (groupValues.get(lastGroup) + 25));
				}
			}
						
			// Discourage more than 2 cards of the same suit 
			// (except face cards)
			if (c.suit != lastSuit) {
				lastSuit  = c.suit;
				suitCount = 1;
			} else {
				if (lastSuit != IFanucci.SUIT_FACE_ALL && ++suitCount > 2) {
					fitness = Double.MAX_VALUE;
					return;
				}
			}
		}
				
		// Calculate the values.
		double value = 0.0;
		for (Short groupId : groupValues.keySet()) {
			double modifier = getModifier(dominantGroup, groupId);
			short gValue = groupValues.get(groupId);
			value += (gValue - (gValue * modifier));
		}
		
		// Remember, there is a maximum value of 100 for any given hand.
		fitness = (value > 100.0) ? 0.0 : (100 - value);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<Card> it = hand.iterator();
		if (it.hasNext()) {
			sb.append('\t').append(it.next());
		}
		while (it.hasNext()) {
			sb.append(", ").append(it.next());
		}
		sb.append('\n');
		
		return sb.toString();
	}
	
	/**
	 * Helper method to return all the cards in a given hand as an array.
	 * 
	 * @return The cards in the given hand as an array.
	 */
	public Card[] getCards() {		
		return hand.toArray(new Card[0]);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Chromosome)) {
			return false;
		}
		
		Chromosome fc = (Chromosome) o;
		if (hand.size() != fc.hand.size()) {
			return false;
		}
		
		Iterator<Card> it1 = hand.iterator();
		Iterator<Card> it2 = fc.hand.iterator();
		while (it1.hasNext()) {
			if (!it1.next().equals(it2.next())) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Method to get all loaded cards that are not already part of a given 
	 * set.
	 * 
	 * @param cardArr The set of cards to differentiate against.
	 * 
	 * @return The set of cards that were loaded, minus the cards in the 
	 * given set.
	 */
	private Card[] getDifference(Card[] cardArr) {
		Set<Card> set = new TreeSet<Card>(population.getDeck());		
		for (Card c : cardArr) {
			set.remove(c);
		}
		
		return set.toArray(new Card[0]);
	}
	
	/**
	 * Method to retrieve the weight-modifier for a given pair of groups.  The
	 * modifiers will be one of:
	 * <ul>
	 *    <li>0.0</li>
	 *    <li>0.5</li>
	 *    <li>1.0</li>
	 *    <li>1.5</li>
	 * </ul>
	 * depending on whether or not the two group IDs are from the same family,
	 * are allies, neutral or enemies respectively.
	 * 
	 * @param firstGroupId The first group ID to compare.
	 * @param secondGroupId The second group ID to compare.
	 * 
	 * @return The modifier for the given pair of suits. 
	 */
	private static double getModifier(short firstGroupId, short secondGroupId) {
		if (firstGroupId == secondGroupId || 
				firstGroupId == IFanucci.GROUP_FACE_CARDS ||
				secondGroupId == IFanucci.GROUP_FACE_CARDS) {
			
			return 0.0;
		}
		
		int idx = Math.abs(firstGroupId - secondGroupId) % 4;
		switch (idx) {
			case 0:	
			case 2:
				return 0.5; // Allies
			case 1: 
				return 1.5; // Enemies
			case 3: 
				return 1.0; // Neutral
			default:
				return 0.0; // Unknown
		}
	}

	/**
	 * Method to retrieve the fitness level of the chromosome.
	 *  
	 * @return The fitness level of the chromosome.	 */
	public double getFitness() {
		return fitness;
	}
	
	/**
	 * Default implementation that will allow sorting based on the fitness
	 * level with a lower fitness value being "better".
	 *  
	 *  @param o The <code>Chromosome</code> to compare to.
	 *  @return -1 if this <code>Chromosome</code> has a better fitness level,
	 *  1 if the other <code>Chromosome</code> has a better fitness level, 
	 *  or 0 if both <code>Chromosome</code>s have the same fitness level. 
	 */
	public int compareTo(Chromosome o) {
		if (this.equals(o)) {
			return 0;
		}
		
		if (fitness <= o.fitness) {
			return -1;
		} else {
			return 1;
		}
	}
}