package com.arm.fanucci;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Controller used for importing and exporting a Double Fanucci deck.  The 
 * format of the file is XML and must have the following structure:
 * 
 * <pre>
 * <cards slots="4" order="0,1,2,3,4">
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
			// First get the number of slots
			String str = attr.getValue("slots");
			if (str != null) {
				int slotCount;
				try {
					slotCount = Integer.valueOf(str);
				} catch (NumberFormatException ex) {
					slotCount = SimulatorOptions.DEFAULT_SLOT_COUNT;
				}
				options.setMaxSlots(slotCount);
			}
			
			// Now get the order of the slots.
			str = attr.getValue("order");
			if (str != null) {
				StringTokenizer st = new StringTokenizer(str, ",");
				List<Integer> list = new ArrayList<Integer>(5);
				while (st.hasMoreTokens()) {
					try {
						int i = Integer.valueOf(st.nextToken());
						if (i >= 0 && i <= 4) {
							list.add(i);
						}
					} catch (NumberFormatException ex) {
						// Ignore.
					}
				}
				if (list.size() > 0) {
					int[] arr = new int[list.size()];
					for (int i = 0; i < arr.length; i++) {
						arr[i] = list.get(i);
					}
					options.setSlotOrder(arr);
				}
			}
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
		
		BufferedInputStream bis = new BufferedInputStream(
				new FileInputStream(f));
		try {
			parser.parse(new InputSource(bis), new DeckController(deck, options));
		} finally {
			try {
				bis.close();
			} catch (IOException ex) {
				// Safe to ignore.
			}
		}
		
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

		final int[] slotOrder = options.getSlotOrder();
		StringBuffer sb = new StringBuffer();
		if (slotOrder.length > 0) {
			sb.append(slotOrder[0]);
			for (int i = 1; i < slotOrder.length; i++) {
				sb.append(',').append(slotOrder[i]);
			}
		}
		cards.setAttribute("order", sb.toString());

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
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(f));
	
		try {
			trans.transform(new DOMSource(doc), new StreamResult(bos));
		} finally {
			try {
				bos.flush();
				bos.close();
			} catch (IOException ex) {
				// Safe to ignore
			}
		}
	}
}