package com.arm.fanucci;

import java.io.File;
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

	private Set<Card> cards;
	
	/**
	 * Default constructor.
	 */
	public Importer() {
		cards = new TreeSet<Card>();
	}
	
	/**
	 */
	public void startElement(String uri, String lName, String qName, 
			Attributes attr) throws SAXException {
		
		if ("card".equals(qName)) {
			short suit = FanucciUtil.getSuitId(attr.getValue("suit"));
			short group = FanucciUtil.getGroupId(suit);
			short value = FanucciUtil.getValue(attr.getValue("value"));
			cards.add(new Card(group, suit, value));
		}
	}
	
	/**
	 * Method to remove a given card.
	 * 
	 * @param c
	 */
	public void removeCard(Card c) {
		cards.remove(c);
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