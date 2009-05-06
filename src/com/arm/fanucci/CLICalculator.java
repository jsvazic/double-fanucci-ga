package com.arm.fanucci;

import java.io.File;

import com.arm.genetic.Chromosome;

/**
 * The main entry point for a command-line version of the calculator.
 * 
 * @author jsvazic
 */
public class CLICalculator {
	
	/**
	 * Main entry point for the application from the command-line.
	 * 
	 * @param args Command line argument (ignored).
	 */
	public static void main(String[] args) throws Exception {
		File cardFile;
		if (args.length > 0) {
			cardFile = new File(args[0]);
			if (!cardFile.isFile() || !cardFile.canRead()) {
				System.out.println("Unable to read file: " + args[0]);
				System.exit(1);
			}
		} else {
			cardFile = new File("etc/cards.xml");
		}
		
		long startTime = System.currentTimeMillis();
		Deck deck = null;
		try {
			deck = DeckController.importDeck(cardFile);
		} catch (Exception ex) {
			System.out.println("Failed to import the Fanucci cards from: " + 
					cardFile);
			
			System.out.println(ex.getMessage());
			System.exit(1);
		}
		
		SimulatorOptions simOptions = OptionsController.loadOptions();
		
		FanucciCalc calc = new FanucciCalc(simOptions);
		Chromosome[] arr = calc.execute(deck);
		long endTime = System.currentTimeMillis();
		
		// Print out the best hands available for the given deck.
		for (Chromosome c : arr) {
			if (c == null) {
				break;
			}

			System.out.println(c);
		}
		
		System.out.println();
		System.out.println("Total time: " + (endTime - startTime) + "ms");
	}
}