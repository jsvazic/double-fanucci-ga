package com.arm.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Class modeling a Genetic Algorithm population for the Hello World 
 * simulation.
 *  
 * @author jsvazic
 */
public abstract class Population {
	
	private static Random rand = new Random(System.currentTimeMillis());
	
	/** Our collection of <code>Chromosome</code>s for the population. */
	protected List<Chromosome> population;
	
	/**
	 * Default constructor.
	 */
	public Population() {
	}
	
	/**
	 * Default constructor. Used to initialize the population size.
	 * 
	 * @param size The size of the population.
	 */
	public Population(int size) {
		this.population = new ArrayList<Chromosome>(size);
		generateInitialPopulation(size);
	}

	/**
	 * Return the size of the population.
	 * 
	 * @return The size of the population. 
	 */
	public int getSize() {
		return (population != null) ? population.size() : 0;
	}
	
	/**
	 * Method to evolve the population based on the characteristics of the
	 * simulation.  Characteristics include elitism ratio and mutation rate.
	 * 
	 * @param elitismRate The elitism rate for the population, measured in
	 * percent.
	 * @param mutationRate The mutation rate for the population, measured in
	 * percent.
	 */
	public void evolve(float elitismRate, float mutationRate) {
		int popSize = population.size();
		int eSize = (int) (popSize * elitismRate);
		List<Chromosome> buffer = new ArrayList<Chromosome>();

		// Keep the first set of the population for elitism, and mate the rest
		Iterator<Chromosome> it = population.iterator();
		for (int i = 0; i < eSize; i++) {
			buffer.add(it.next());
		}
		Chromosome[] popArr = population.toArray(new Chromosome[0]);
		for (int i = eSize; i < popSize; i++) {
			Chromosome p1 = popArr[rand.nextInt(popArr.length / 2)];
			Chromosome p2 = popArr[rand.nextInt(popArr.length / 2)];
			
			// Get the child based on the mating of the two parents.
			Chromosome offspring = p1.mate(p2);

			// Randomly mutate the offspring
			if (rand.nextFloat() < mutationRate) {
				offspring.mutate();
			}
			
			buffer.add(offspring);
		}

		// Update the population
		population.clear();
		population.addAll(buffer);
		Collections.sort(population);
	}

	/**
	 * Method to retrieve the most fit chromosome.
	 * 
	 * @return The most fit chromosome.
	 */
	public Chromosome getBestChromosome() {
		if (population.size() > 0) {
			return population.iterator().next();
		}
		
		return null;
	}
	
	/**
	 * Method to retrieve the specified number of best fit chromosomes.
	 * 
	 * @param size The number of best fit chromosomes to retrieve.
	 * @return The most fit chromosome.
	 */
	public Chromosome[] getBestChromosomes(int size) {
		if (size < population.size()) {
			Chromosome[] arr = new Chromosome[size];
			Iterator<Chromosome> it = population.iterator();
			for (int i = 0; i < size; i++) {
				arr[i] = it.next();
			}

			return arr;
		}
		
		return null;
	}
	
	/**
	 * Method used to generate the initial population.
	 * 
	 * @param populationSize The size of the population to generate.
	 */
	protected abstract void generateInitialPopulation(int populationSize);
}