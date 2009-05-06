package com.arm.fanucci;

import java.util.Set;
import java.util.TreeSet;

/**
 * Class used for modelling a double Fanucci deck.
 * 
 * @author jsvazic
 */
public class Deck {

	private Set<Card> cards;
	private static Deck instance;
	
	/**
	 * Default constructor.
	 */
	private Deck() {
		cards = new TreeSet<Card>();
	}
	
	/**
	 * Method used to add a <code>Card</code> to this deck.
	 * 
	 * @param card The <code>Card</code> to add to this deck.
	 */
	public void addCard(Card card) {
		cards.add(card);
	}
	
	/**
	 * Method used to retrieve a singleton instance of the deck.
	 * 
	 * @return A singleton instance of the deck.
	 */
	public static synchronized Deck getInstance() {
		if (instance == null) {
			instance = new Deck();
		}
		
		return instance;
	}
	
	/**
	 * Method to remove a given card.
	 * 
	 * @param c The card to remove from the imported set.
	 */
	public void removeCard(Card c) {
		cards.remove(c);
	}
	
	/**
	 * Method to clear out all cards and reset this deck.
	 */
	public void reset() {
		cards.clear();
	}
	
	/**
	 * Method to retrieve all the cards that were imported.
	 * 
	 * @return The set of all imported cards.
	 */
	public Set<Card> getAllCards() {
		return new TreeSet<Card>(cards);
	}
	
	/**
	 * Method to get all loaded cards that are not already part of a given 
	 * set.
	 * 
	 * @param cards The set of cards to differentiate against.
	 * 
	 * @return The set of cards that were loaded, minus the cards in the 
	 * given set.
	 */
	public Set<Card> getDifference(Card[] cards) {
		Set<Card> mySet = new TreeSet<Card>(this.cards);
		for (Card c : cards) {
			mySet.remove(c);
		}
		
		return mySet;
	}
}