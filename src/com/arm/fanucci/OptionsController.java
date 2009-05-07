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
 * <fanucci-options>
 *   <population-size value="1024" />
 *   <elitism-rate value="0.1" />
 *   <mutation-rate value="0.15" />
 *   <iterations value="256" />
 *   <hands value="4" />
 *   <repeat-count value="4" />
 * </fanucci-options>
 * </pre>
 * 
 * @author jsvazic
 */
public class OptionsController extends DefaultHandler {
	
	private final static File CONFIG_FILE = 
			new File(System.getProperty("user.home"), "yadfc/options.config");

	/** The default size for the population. */
	public static final int DEFAULT_POPULATION_SIZE = 256;

	/** The elitism rate for the simulation, where: 0.0 &lt; rate &lt; 1.0 */
	public static final float DEFAULT_ELITISM_RATE = 0.1f;

	/** The mutation rate for the simulation, where: 0.0 &lt; rate &lt; 1.0 */
	public static final float DEFAULT_MUTATION_RATE = 0.15f;
	
	/** Maximum number of iterations for the simulation. */
	public static final int DEFAULT_ITERATIONS = 128;
	
	/** Maximum number of hands to generate. */
	public static final int DEFAULT_HANDS = 4;
	
	/** Maximum repeat count for a best fit before exiting. */
	public static final int DEFAULT_REPEAT_COUNT = 32;
	
	private SimulatorOptions options;
	
	/**
	 * Default constructor.
	 */
	private OptionsController() {
		this.options = new SimulatorOptions(DEFAULT_POPULATION_SIZE, 
				DEFAULT_ELITISM_RATE, DEFAULT_MUTATION_RATE, 
				DEFAULT_ITERATIONS, DEFAULT_HANDS, DEFAULT_REPEAT_COUNT);
	}
	
	@Override
	public void startElement(String uri, String lName, String qName, 
			Attributes attr) throws SAXException {
		
		if ("population-size".equals(qName)) {
			int value;
			try {
				value = Integer.valueOf(attr.getValue("value"));
			} catch (NumberFormatException ex) {
				value = DEFAULT_POPULATION_SIZE;
			}
			options.setPopulationSize(value);
		} else if ("elitism-rate".equals(qName)) {
			float rate;
			try {
				rate = Float.valueOf(attr.getValue("value"));
			} catch (NumberFormatException ex) {
				rate = DEFAULT_ELITISM_RATE;
			}
			options.setElitismRate(rate);			
		} else if ("mutation-rate".equals(qName)) {
			float rate;
			try {
				rate = Float.valueOf(attr.getValue("value"));
			} catch (NumberFormatException ex) {
				rate = DEFAULT_MUTATION_RATE;
			}
			options.setMutationRate(rate);			
		} else if ("iterations".equals(qName)) {
			int count;
			try {
				count = Integer.valueOf(attr.getValue("value"));
			} catch (NumberFormatException ex) {
				count = DEFAULT_ITERATIONS;
			}
			options.setMaxIterations(count);			
		} else if ("hands".equals(qName)) {
			int count;
			try {
				count = Integer.valueOf(attr.getValue("value"));
			} catch (NumberFormatException ex) {
				count = DEFAULT_HANDS;
			}
			options.setMaxHands(count);			
		} else if ("repeat-count".equals(qName)) {
			int count;
			try {
				count = Integer.valueOf(attr.getValue("value"));
			} catch (NumberFormatException ex) {
				count = DEFAULT_REPEAT_COUNT;
			}
			options.setMaxRepeatCount(count);			
		}
	}
	
	/**
	 * Method to retrieve an initialized <code>SimulatorOptions</code> object.
	 * 
	 * @return An initialized <code>SimulatorOptions</code> object.
	 */
	private SimulatorOptions getOptions() {
		return options;
	}
	
	/**
	 * Method used to import the configuration options.  If the options could
	 * not be imported, then default options for the simulator are used 
	 * instead.
	 * 
	 * @return An imported and/or initialized <code>SimulatorOptions</code> 
	 * object.
	 */
	public static SimulatorOptions loadOptions() {
		OptionsController controller = new OptionsController();
		try {
			if (CONFIG_FILE.exists() && CONFIG_FILE.canRead()) {			
				SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
				parser.parse(CONFIG_FILE, controller);
			}
		} catch (Exception ex) {
			System.out.println("Failed to load the config file: " +
					ex.getMessage());
		}
		
		return controller.getOptions();
	}

	/**
	 * Method used to export a deck to a file.
	 * 
	 * @param deck The deck to export.
	 * @param f The file to export the deck to.
	 * 
	 * @throws Exception Thrown if the deck could not be exported.
	 */
	public static void saveOptions(SimulatorOptions options) 
			throws Exception {
		
		// Construct a DOM XML document for export.
		DocumentBuilder builder = 
				DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
		Document doc = builder.newDocument();
		Element root = doc.createElement("fanucci-options");
		
		final float elitismRate = options.getElitismRate();
		final float mutationRate = options.getMutationRate();
		final int hands = options.getMaxHands();
		final int iterations = options.getMaxIterations();
		final int repeatCount = options.getMaxRepeatCount();
		final int popSize = options.getPopulationSize();

		Element element = doc.createElement("population-size");
		element.setAttribute("value", String.valueOf(popSize)); 
		root.appendChild(element);
		
		element = doc.createElement("elitism-rate");
		element.setAttribute("value", String.valueOf(elitismRate)); 
		root.appendChild(element);

		element = doc.createElement("mutation-rate");
		element.setAttribute("value", String.valueOf(mutationRate)); 
		root.appendChild(element);

		element = doc.createElement("iterations");
		element.setAttribute("value", String.valueOf(iterations)); 
		root.appendChild(element);

		element = doc.createElement("hands");
		element.setAttribute("value", String.valueOf(hands)); 
		root.appendChild(element);

		element = doc.createElement("repeat-count");
		element.setAttribute("value", String.valueOf(repeatCount)); 
		root.appendChild(element);

		doc.appendChild(root);
		
		// Export the document to the specified file.
		if (!CONFIG_FILE.exists()) {
			CONFIG_FILE.getParentFile().mkdirs();
			CONFIG_FILE.createNewFile();
		}
		
		Transformer trans = TransformerFactory.newInstance().newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(CONFIG_FILE));
	}
}