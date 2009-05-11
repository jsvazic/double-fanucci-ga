package com.arm.fanucci;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import com.arm.genetic.Chromosome;

/**
 * Class modeling a Fanucci <i>slot</i>, which is a collection of at most 4 
 * cards.  Slots in Double Fanucci are defined for Body, Mind, Spirit and the 
 * Fanucci Gambit.  Additional slots are available for sidekicks as well.
 * 
 * @author jsvazic
 *
 */
public class FanucciChromosome extends Chromosome {
	private Set<Card> hand;
	private FanucciPopulation population;
	
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
	public FanucciChromosome(FanucciPopulation population, Set<Card> cards) {
		if (cards == null) {
			throw new NullPointerException("'cards' cannot be null.");
		}
		
		this.population = population;
		this.hand = new TreeSet<Card>(cards);
	}
	
	/**
	 * Method to retrieve the weight of the given slot hand.  Note that only 
	 * slots with at most 2 suits will have a valid value.  More than two
	 * suits will return a zero weight.  Also note that the maximum point 
	 * value a slot can have is 100. 
	 * 
	 * @return The fitness of the given hand, measured in terms of the 
	 * distance from the ideal hand of 100 points.  
	 */
	@Override
	public double getFitness() {
		// Do not let more than 3 cards from a single suit in.
		short dominantGroup  = IFanucci.GROUP_UNKNOWN;
		short lastGroup      = IFanucci.GROUP_UNKNOWN;
		short lastSuit       = IFanucci.SUIT_UNKNOWN;
		short suitCount      = 0;
		short groupCount     = 0;
		short bestGroupValue = 0;
		short[] groupValues  = new short[6];
		
		for (Card c : hand) {
			if (c.getGroup() != lastGroup) {
				if (lastGroup != IFanucci.GROUP_UNKNOWN && 
						groupValues[lastGroup] > bestGroupValue) {
					
					bestGroupValue = groupValues[lastGroup];
					dominantGroup  = lastGroup;
				}
				lastGroup  = c.getGroup();
				++groupCount;
			} else {
				groupValues[lastGroup] += c.getValue();
			}
			
			// Discourage more than 2 groups.
			if (groupCount > 2) {
				return Double.MAX_VALUE;
			}
			
			// Discourage more than 2 cards of the same suit
			if (c.getSuit() != lastSuit) {
				lastSuit  = c.getSuit();
				suitCount = 1;
			} else {
				if (++suitCount > 2) {
					return Double.MAX_VALUE;
				}
			}
		}
		
		// Get the dominant group
		double value = 0.0;
		for (Card c : hand) {
			if (c.getGroup() == dominantGroup) {
				value += c.getValue();
			} else {
				double modifier = getModifier(dominantGroup, c.getGroup());
				short cardValue = c.getValue();
				value += (cardValue - (cardValue * modifier));
			}
		}		
		
		// Remember, there is a maximum value of 100 for any given hand.
		return (value > 100.0) ? 0.0 : (100 - value);
	}

	@Override
	public Chromosome mate(Chromosome mate) {
		Card[] parent1   = getCards();
		Card[] parent2 = ((FanucciChromosome) mate).getCards();
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
		FanucciChromosome child = new FanucciChromosome(population, cards);

		return child;
	}

	@Override
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
	public boolean equalsChromosome(Chromosome c) {
		if (!(c instanceof FanucciChromosome)) {
			return false;
		}
		
		FanucciChromosome fc = (FanucciChromosome) c; 
		
		if (hand.size() != fc.hand.size()) {
			return false;
		}
		
		for (Card card : hand) {
			if (!fc.hand.contains(card)) {
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
		Set<Card> set = new TreeSet<Card>();
		
		// Add all the cards in the current deck to the set
		for (Card c : population.getDeck()) {
			set.add(c);
		}
		
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
		if (firstGroupId == secondGroupId) {
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

}