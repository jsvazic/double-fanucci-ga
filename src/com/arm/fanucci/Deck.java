package com.arm.fanucci;

import java.util.Collections;
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
		cards = Collections.synchronizedSet(new TreeSet<Card>());
	}
	
	/**
	 * Method to retrieve the singleton instance of this class.
	 * 
	 * @return A singleton instance of the <code>Deck</code> class.
	 */
	public static synchronized Deck getInstance() {
		if (instance == null) {
			instance = new Deck();
		}
		
		return instance;
	}
	
	/**
	 * Method used to add a <code>Card</code> to this deck.
	 * 
	 * @param card The <code>Card</code> to add to this deck.
	 */
	public void addCard(Card card) {
		synchronized(cards) {
			cards.add(card);
		}
	}
	
	/**
	 * Method to remove a given card.
	 * 
	 * @param c The card to remove from the imported set.
	 */
	public void removeCard(Card c) {
		synchronized(cards) {
			cards.remove(c);
		}
	}
	
	/**
	 * Method to retrieve all the cards that were imported.
	 * 
	 * @return The set of all imported cards.
	 */
	public Card[] getAllCards() {
		synchronized(cards) {
			return cards.toArray(new Card[0]);
		}
	}
	
	public Set<Card> getCardSet() {
		synchronized(cards) {
			return new TreeSet<Card>(cards);
		}
	}
	
	/**
	 * Method used to check to see if a given <code>Card</code> already 
	 * exists in this deck or not.
	 * 
	 * @param c The card to check.
	 * 
	 * @return <code>true</code> if the <code>Card</code> is in this deck; 
	 * <code>false</code> otherwise.
	 */
	public boolean hasCard(Card c) {
		synchronized(cards) {
			return cards.contains(c);
		}
	}
		
	/**
	 * Method to retrieve the size of this deck.
	 * 
	 * @return The size of this deck.
	 */
	public int size() {
		synchronized (cards) {
			return cards.size();			
		}
	}

	/**
	 * Method used to reset the deck contents.
	 */
	public void reset() {
		synchronized (cards) {
			cards.clear();
		}
	}
}