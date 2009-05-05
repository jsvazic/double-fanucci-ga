package com.arm.fanucci;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class used for importing a data file containing Fanucci card details.
 * 
 * @author jsvazic
 */
public class Importer extends DefaultHandler {

	private Map<Short, Set<Card>> cards;
	
	/**
	 * Default constructor.
	 */
	public Importer() {
		cards = new HashMap<Short, Set<Card>>();
	}
	
	/**
	 */
	public void startElement(String uri, String lName, String qName, 
			Attributes attr) throws SAXException {
		
		if ("card".equals(qName)) {
			short suit = FanucciUtil.getSuitId(attr.getValue("suit"));
			short group = FanucciUtil.getGroupId(suit);
			short value = FanucciUtil.getValue(attr.getValue("value")); 
			Card c = new Card(group, suit, value);
			Set<Card> set = cards.get(suit);

			if (set == null) {
				set = new TreeSet<Card>();
				cards.put(suit, set);
			}
			
			set.add(c);
		}
	}
	
	/**
	 * Method to remove a given card.
	 * 
	 * @param c
	 */
	public void removeCard(Card c) {
		if (cards.containsKey(c.getSuit())) {
			cards.get(c.getSuit()).remove(c);
		}
	}

	/**
	 * Method used to import cards from a specified file.
	 * 
	 * @param f The XML file to load the cards from.
	 * 
	 * @throws Exception Thrown if there was a problem importing the cards.
	 */
	public void importCards(File f) throws Exception {
		cards.clear();
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(f, this);		
	}
	
	/**
	 * Method to retrieve all the cards that were imported.
	 * 
	 * @return The set of all cards imported.
	 */
	public Set<Card> getAllCards() {
		Set<Card> mySet = new TreeSet<Card>();
		for (Set<Card> cardSet : this.cards.values()) {
			mySet.addAll(cardSet);
		}
		
		return mySet;
	}
	
	/**
	 * Method to retrieve all cards for a given suit.
	 * 
	 * @param suitId The ID of the suit to retrieve all the cards for.
	 * 
	 * @return The set of all cards for a given suit.
	 */
	public Set<Card> getCardsForSuit(short suitId) {
		Set<Card> mySet = new TreeSet<Card>();
		if (cards.containsKey(suitId)) {
			for (Card c : cards.get(suitId)) {
				mySet.add(c);
			}
		}
		
		return mySet;		
	}

	/**
	 * Method to retrieve all cards for a given group.
	 * 
	 * @param groupId The ID of the group to retrieve all the cards for.
	 * 
	 * @return The set of all cards for a given group.
	 */
	public Set<Card> getCardsForGroup(short groupId) {
		Set<Card> mySet = new TreeSet<Card>();
		short[] suitArr = FanucciUtil.getSuitsForGroup(groupId);
		for (short suitId : suitArr) {
			if (cards.containsKey(suitId)) {
				for (Card c : cards.get(suitId)) {
					mySet.add(c);
				}
			}
		}
		
		return mySet;		
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
		Set<Card> mySet = getAllCards();
		for (Card c : cards) {
			mySet.remove(c);
		}
		
		return mySet;
	}
}