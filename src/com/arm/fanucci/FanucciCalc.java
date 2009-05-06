package com.arm.fanucci;

import java.io.File;

import com.arm.genetic.Chromosome;

/**
 * Main driver for the Hello World Genetic Algorithms simulation.
 * 
 * @author jsvazic
 */
public class FanucciCalc {
	/** The default size for the population. */
	private static final int POPULATION_SIZE = 64;

	/** The elitism rate for the simulation, where: 0.0 &lt; rate &lt; 1.0 */
	private static final float ELITISM_RATE = 0.1f;

	/** The mutation rate for the simulation, where: 0.0 &lt; rate &lt; 1.0 */
	private static final float MUTATION_RATE = 0.15f;
	
	/** Maximum number of iterations for the simulation. */
	private static final int MAX_ITERATION = 64;
	
	private static final int MAX_HANDS = 4;

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
		Importer importer = new Importer();
		try {
			importer.importCards(cardFile);
		} catch (Exception ex) {
			System.out.println("Failed to import the Fanucci cards from: " + 
					cardFile);
			
			System.out.println(ex.getMessage());
		}
		
		Chromosome[] arr = new Chromosome[MAX_HANDS];
		for (int j = 0; j < MAX_HANDS; j++) {
			FanucciPopulation population = new FanucciPopulation(
					POPULATION_SIZE, importer);

			for (int i = 0; i < MAX_ITERATION; i++) {
				// Evolve the population
				population.evolve(ELITISM_RATE, MUTATION_RATE);
			}
			
			arr[j] = population.getBestChromosome();
			for (Card c : ((FanucciChromosome) arr[j]).getCards()) {
				importer.removeCard(c);
			}
		}
		long endTime = System.currentTimeMillis();
		
		for (Chromosome c : arr) {
			c.printGene();
		}
		
		System.out.println();
		System.out.println("Total time: " + (endTime - startTime) + "ms");
	}
}
