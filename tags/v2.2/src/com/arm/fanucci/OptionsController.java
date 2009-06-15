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
 * Controller used for loading and saving <code>SimulatorOptions</code> 
 * instances.  The format of the file is XML and must have the following 
 * structure:
 * 
 * <pre>
 * <fanucci-options>
 *   <population-size value="1024" />
 *   <elitism-rate value="0.1" />
 *   <mutation-rate value="0.15" />
 *   <iterations value="256" />
 *   <repeat-count value="32" />
 * </fanucci-options>
 * </pre>
 * 
 * @author jsvazic
 */
public class OptionsController extends DefaultHandler {
	
	/** The location where the options file will be saved to. */
	public final static File OLD_OPTIONS_FILE = 
			new File(System.getProperty("user.home"), "yadfc/options.config");

	public final static File OPTIONS_FILE = 
		new File(System.getProperty("user.dir"), "calc.dat");
	
	private SimulatorOptions options;
	
	/**
	 * Default constructor.
	 */
	private OptionsController() {
		this.options = new SimulatorOptions();
	}
	
	@Override
	public void startElement(String uri, String lName, String qName, 
			Attributes attr) throws SAXException {
		
		if ("population-size".equals(qName)) {
			int value;
			try {
				value = Integer.valueOf(attr.getValue("value"));
			} catch (NumberFormatException ex) {
				value = SimulatorOptions.DEFAULT_POPULATION_SIZE;
			}
			options.setPopulationSize(value);
		} else if ("elitism-rate".equals(qName)) {
			float rate;
			try {
				rate = Float.valueOf(attr.getValue("value"));
			} catch (NumberFormatException ex) {
				rate = SimulatorOptions.DEFAULT_ELITISM_RATE;
			}
			options.setElitismRate(rate);			
		} else if ("mutation-rate".equals(qName)) {
			float rate;
			try {
				rate = Float.valueOf(attr.getValue("value"));
			} catch (NumberFormatException ex) {
				rate = SimulatorOptions.DEFAULT_MUTATION_RATE;
			}
			options.setMutationRate(rate);			
		} else if ("iterations".equals(qName)) {
			int count;
			try {
				count = Integer.valueOf(attr.getValue("value"));
			} catch (NumberFormatException ex) {
				count = SimulatorOptions.DEFAULT_ITERATIONS;
			}
			options.setMaxIterations(count);			
		} else if ("repeat-count".equals(qName)) {
			int count;
			try {
				count = Integer.valueOf(attr.getValue("value"));
			} catch (NumberFormatException ex) {
				count = SimulatorOptions.DEFAULT_REPEAT_COUNT;
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
			if (OPTIONS_FILE.exists() && OPTIONS_FILE.canRead()) {			
				SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
				parser.parse(OPTIONS_FILE, controller);
			} else if (OLD_OPTIONS_FILE.exists() && OLD_OPTIONS_FILE.canRead()) {
				SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
				parser.parse(OLD_OPTIONS_FILE, controller);
				if (!OLD_OPTIONS_FILE.delete()) {
					OLD_OPTIONS_FILE.deleteOnExit();
				}
			}
		} catch (Exception ex) {
			System.out.println("Failed to load the config file: " +
					ex.getMessage());
		}
		
		return controller.getOptions();
	}

	/**
	 * Method used to save the <code>SimulatorOptions</code> for the user.
	 * The save location is pre-determined.
	 * 
	 * @param options The <code>SimulatorOptions</code> object to save.
	 * 
	 * @throws Exception Thrown if the options could not be saved.
	 * 
	 * @see #OPTIONS_FILE
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
		
		element = doc.createElement("repeat-count");
		element.setAttribute("value", String.valueOf(repeatCount)); 
		root.appendChild(element);

		doc.appendChild(root);
		
		// Export the document to the specified file.
		if (!OPTIONS_FILE.exists()) {
			OPTIONS_FILE.getParentFile().mkdirs();
			OPTIONS_FILE.createNewFile();
		}
		
		Transformer trans = TransformerFactory.newInstance().newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(OPTIONS_FILE));
	}
}