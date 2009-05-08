package com.arm.fanucci;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
	private Map<Short, Set<Card>> hand;
	
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
	public FanucciChromosome(Set<Card> cards) {
		if (cards == null) {
			throw new NullPointerException("'cards' cannot be null.");
		}
		
		// We'll never have more than 4 unique suits, so pre-allocate 
		// the space.
		this.hand = new HashMap<Short, Set<Card>>(4);
		
		// Group the cards by their respective suits
		for (Card card : cards) {
			short suit = card.getSuit();
			Set<Card> set;
			if (this.hand.containsKey(suit)) {
				set = this.hand.get(suit);
			} else {
				set = new TreeSet<Card>();
				this.hand.put(suit, set);
			}
			set.add(card);
		}
	}
	
	/**
	 * Method used to retrieve the dominant suit for the slot.
	 * 
	 * @return The dominant suit for the hand, or <code>SUIT_UNKNOWN</code>
	 * if there was an issue determining the suit.
	 * 
	 * @see com.arm.fanucci.IFanucci
	 */
	private short getDominantSuite() {
		short bestSuite = IFanucci.SUIT_UNKNOWN;
		short bestValue = 0;
		for (Short suit : hand.keySet()) {
			Iterator<Card> it = hand.get(suit).iterator();
			short total = 0;
			
			// We only care about the top 2 cards in the suit
			for (int i = 0; i < 2 && it.hasNext(); i++) {
				total += it.next().getValue();
			}
			
			if (total > bestValue) {
				bestSuite = suit;
				bestValue = total;
			}
		}
		
		return bestSuite;
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
		double value = 0.0;
		// Get the dominant suit
		short dominantSuit = getDominantSuite();
		
		// Discourage more than three suits for a single slot
		if (hand.size() > 3) {
			return Double.MAX_VALUE;
		}

		int totalCards = 0;
		// Iterate over the remaining suits and get their total values
		for (Short suit : hand.keySet()) {
			totalCards += hand.get(suit).size();
			double modifier = 0.0;
			if (suit != dominantSuit) {
				modifier = FanucciUtil.getModifier(
						FanucciUtil.getGroupId(dominantSuit), 
						FanucciUtil.getGroupId(suit));
			}
			double suitValue = 0.0;
			Iterator<Card> it = hand.get(suit).iterator();
			
			// Only the top two cards in the suit count
			for (int i = 0; i < 2 && it.hasNext(); i++) {
				suitValue += it.next().getValue();
			}
			
			value += suitValue - (suitValue * modifier);
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
		return new FanucciChromosome(cards);
	}

	@Override
	public void mutate() {
		// Randomly mutate a card by removing it from the set and replacing 
		// it with a new card.
		Card[] myArr = getCards();
		Card[] oArr  = Deck.getInstance().getDifference(myArr).toArray(
				new Card[0]);
		
		if (oArr.length < 1) {
			return;
		}
		
		Card toRemove = myArr[rand.nextInt(myArr.length)];
		Card toAdd = oArr[rand.nextInt(oArr.length)];
		
		hand.get(toRemove.getSuit()).remove(toRemove);
		if (hand.containsKey(toAdd.getSuit())) {
			hand.get(toAdd.getSuit()).add(toAdd);
		} else {
			Set<Card> set = new TreeSet<Card>();
			set.add(toAdd);
			hand.put(toAdd.getSuit(), set);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("------------------------------------\n");
		sb.append("Hand value - ").append((100 - getFitness())).append(':');
		sb.append('\n');
		for (Set<Card> cards : hand.values()) {
			Iterator<Card> it = cards.iterator();
			if (it.hasNext()) {
				sb.append('\t').append(it.next());
			}
			while (it.hasNext()) {
				sb.append(", ").append(it.next());
			}
			sb.append('\n');
		}
		
		return sb.toString();
	}
	
	/**
	 * Helper method to return all the cards in a given hand as an array.
	 * 
	 * @return The cards in the given hand as an array.
	 */
	public Card[] getCards() {
		Set<Card> cards = new TreeSet<Card>();
		for (Set<Card> s : hand.values()) {
			cards.addAll(s);
		}
		
		return cards.toArray(new Card[0]);
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
		
		for (Short key : hand.keySet()) {
			if (!fc.hand.containsKey(key)) {
				return false;
			}
			if (hand.get(key).size() != fc.hand.get(key).size()) {
				return false;
			}
			for (Card card : hand.get(key)) {
				if (!(fc.hand.get(key).contains(card))) {
					return false;
				}
			}
		}
		
		return true;
	}
}