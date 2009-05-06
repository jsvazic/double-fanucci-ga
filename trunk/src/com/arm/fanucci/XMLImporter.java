package com.arm.fanucci;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Implementation of a SAX handler used to import Fanucci cards from an XML 
 * file.  The XML file must be of the format:
 * <pre>
 * <cards>
 *   <card suit="Bugs" value="Naught" />
 *   <card suit="Bugs" value="1" />
 * </cards>
 * </pre>
 * 
 * @author jsvazic
 */
public class XMLImporter extends DefaultHandler {
	
	/**
	 * Default constructor.
	 */
	public XMLImporter() {
	}
	
	@Override
	public void startElement(String uri, String lName, String qName, 
			Attributes attr) throws SAXException {
		
		if ("card".equals(qName)) {
			short suit = FanucciUtil.getSuitId(attr.getValue("suit"));
			short group = FanucciUtil.getGroupId(suit);
			short value = FanucciUtil.getValue(attr.getValue("value"));
			Deck.getInstance().addCard(new Card(group, suit, value));
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
		Deck.getInstance().reset();
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(f, this);
	}

}