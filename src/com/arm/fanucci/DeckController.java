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
 * <cards slots="4">
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
	private SimulatorOptions options;
	
	/**
	 * Default constructor.
	 * 
	 * @param deck The deck to store the cards to.
	 * @param options The options to store the number of slots to generate.
	 */
	private DeckController(Deck deck, SimulatorOptions options) {
		this.deck = deck;
		this.options = options;
	}
	
	@Override
	public void startElement(String uri, String lName, String qName, 
			Attributes attr) throws SAXException {
		
		if ("cards".equals(qName)) {
			int slotCount;
			try {
				slotCount = Integer.valueOf(attr.getValue("slots"));
			} catch (NumberFormatException ex) {
				slotCount = SimulatorOptions.DEFAULT_SLOT_COUNT;
			}
			options.setMaxSlots(slotCount);
		} else if ("card".equals(qName)) {
			short suit = FanucciUtil.getSuitId(attr.getValue("suit"));
			short value = FanucciUtil.getValue(attr.getValue("value"));
			deck.addCard(new Card(suit, value));
		}
	}
	
	/**
	 * Method used to import a deck from a specified file.  Both the cards and
	 * the number of slots to generate will be imported from the file.
	 * 
	 * @param f The file to load the deck from.
	 * @param options The calculator options that will hold the number of 
	 * slots to generate.
	 * 
	 * @throws Exception Thrown if there was a problem importing the deck.
	 */
	public static Deck importDeck(File f, SimulatorOptions options) 
			throws Exception {
		
		Deck deck = Deck.getInstance();
		deck.reset();
		
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(f, new DeckController(deck, options));
		
		return deck;
	}

	/**
	 * Method used to export a deck to a file.
	 * 
	 * @param deck The deck to export.
	 * @param options The simulator options containing the number of slots to
	 * generate.
	 * @param f The file to export the deck to.
	 * 
	 * @throws Exception Thrown if the deck could not be exported.
	 */
	public static void exportDeck(Deck deck, SimulatorOptions options, File f) 
			throws Exception {
		
		// Construct a DOM XML document for export.
		DocumentBuilder builder = 
				DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
		Document doc = builder.newDocument();
		Element cards = doc.createElement("cards");
		cards.setAttribute("slots", String.valueOf(options.getMaxSlots()));
		
		for (Card c : deck.getAllCards()) {
			Element card = doc.createElement("card");
			card.setAttribute("suit", FanucciUtil.getSuitName(c.suit));
			card.setAttribute("value", 
					FanucciUtil.getValueString(c.value));
			
			cards.appendChild(card);
		}
		doc.appendChild(cards);
		
		// Export the document to the specified file.
		Transformer trans = TransformerFactory.newInstance().newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(f));
	}
}