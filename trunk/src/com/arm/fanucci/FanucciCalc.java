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
	private static final int POPULATION_SIZE = 1024;

	/** The elitism rate for the simulation, where: 0.0 &lt; rate &lt; 1.0 */
	private static final float ELITISM_RATE = 0.1f;

	/** The mutation rate for the simulation, where: 0.0 &lt; rate &lt; 1.0 */
	private static final float MUTATION_RATE = 0.15f;
	
	/** Maximum number of iterations for the simulation. */
	private static final int MAX_ITERATION = 128;
	
	private static final int MAX_HANDS = 4;

	/**
	 * Main entry point for the application.
	 * 
	 * @param args Command line argument (ignored).
	 */
	public static void main(String[] args) throws Exception {
		Importer importer = new Importer();
		importer.importCards(new File("etc/cards.xml"));

		for (int j = 0; j < MAX_HANDS; j++) {
			FanucciPopulation population = new FanucciPopulation(
					POPULATION_SIZE, importer);

			Chromosome bestChromosome = population.getBestChromosome();
			for (int i = 1; i <= MAX_ITERATION; i++) {
				// Evolve the population
				population.evolve(ELITISM_RATE, MUTATION_RATE);
			}
		
			bestChromosome.printGene();
			for (Card c : ((FanucciChromosome) bestChromosome).getCards()) {
				importer.removeCard(c);
			}
		}
	}
}
