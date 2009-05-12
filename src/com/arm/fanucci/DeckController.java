package com.arm.fanucci;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Controller used for importing and exporting a Double Fanucci deck.  The 
 * format of the file is XML and must have the following structure:
 * 
 * <pre>
 * <cards>
 *   <card suit="Bugs" value="Naught" />
 *   <card suit="Bugs" value="1" />
 *   ...
 * </cards>
 * </pre>
 * 
 * @author jsvazic
 */
public class DeckController extends DefaultHandler {
	
	private Deck deck;
	
	/**
	 * Default constructor.
	 */
	private DeckController(Deck deck) {
		this.deck = deck;
	}
	
	@Override
	public void startElement(String uri, String lName, String qName, 
			Attributes attr) throws SAXException {
		
		if ("card".equals(qName)) {
			short suit = FanucciUtil.getSuitId(attr.getValue("suit"));
			short value = FanucciUtil.getValue(attr.getValue("value"));
			deck.addCard(new Card(suit, value));
		}
	}
	
	/**
	 * Method used to import a deck from a specified file.
	 * 
	 * @param f The file to load the deck from.
	 * 
	 * @throws Exception Thrown if there was a problem importing the deck.
	 */
	public static Deck importDeck(File f) throws Exception {
		Deck deck = Deck.getInstance();
		deck.reset();
		
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(f, new DeckController(deck));
		
		return deck;
	}

	/**
	 * Method used to export a deck to a file.
	 * 
	 * @param deck The deck to export.
	 * @param f The file to export the deck to.
	 * 
	 * @throws Exception Thrown if the deck could not be exported.
	 */
	public static void exportDeck(Deck deck, File f) throws Exception {
		// Construct a DOM XML document for export.
		DocumentBuilder builder = 
				DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
		Document doc = builder.newDocument();
		Element root = doc.createElement("cards");
		for (Card c : deck.getAllCards()) {
			Element card = doc.createElement("card");
			card.setAttribute("suit", FanucciUtil.getSuitName(c.getSuit()));
			card.setAttribute("value", 
					FanucciUtil.getValueString(c.getValue()));
			
			root.appendChild(card);
		}
		doc.appendChild(root);
		
		// Export the document to the specified file.
		Transformer trans = TransformerFactory.newInstance().newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(f));
	}
}