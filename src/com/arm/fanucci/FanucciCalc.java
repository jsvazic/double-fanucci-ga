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
	 * Main entry point for the application.
	 * 
	 * @param args Command line argument (ignored).
	 */
	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		Importer importer = new Importer();
		importer.importCards(new File("etc/cards.xml"));
		Chromosome[] arr = new Chromosome[4];
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
