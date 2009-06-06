package com.arm.fanucci;

import java.io.File;

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
		File cardFile = null;
		if (args.length > 0) {
			cardFile = new File(args[0]);
			if (!cardFile.isFile() || !cardFile.canRead()) {
				System.out.println("Unable to read file: " + args[0]);
				System.exit(1);
			}
		} 

		if (cardFile == null) {
			System.out.println("Syntax: java -classpath yadfc.jar " +
					"com.arm.fanucci.CLICalculator <card file>");
			
			System.exit(1);
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
		Chromosome[] arr = calc.execute(deck.getCardSet());
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