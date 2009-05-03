package com.arm.genetic;

/**
 * Abstract class modeling a chromosome for a Genetic Algorithm 
 * simulation.
 * 
 * @author jsvazic
 */
public abstract class Chromosome implements Comparable<Chromosome> {

	/**
	 * Method to retrieve the fitness level of the chromosome.
	 *  
	 * @return The fitness level of the chromosome.	 */
	public abstract double getFitness();
	
	/**
	 * Method to mutate the chromosome.
	 */
	public abstract void mutate();
		
	/**
	 * Method used to mate this chromosome with another, producing an 
	 * offspring.
	 * 
	 * @param mate The <code>Chromosome</code> to mate with.
	 * 
	 * @return The resulting <code>Chomosome</code> after mating.
	 */
	public abstract Chromosome mate(Chromosome mate);
	
	/**
	 * Default implementation that will allow sorting based on the fitness
	 * level with a lower fitness value being "better".
	 *  
	 *  @param o The <code>Chromosome</code> to compare to.
	 *  @return -1 if this <code>Chromosome</code> has a better fitness level,
	 *  1 if the other <code>Chromosome</code> has a better fitness level, 
	 *  or 0 if both <code>Chromosome</code>s have the same fitness level. 
	 */
	public int compareTo(Chromosome o) {
		if (equalsChromosome(o)) {
			return 0;
		}
		
		double myFitness = getFitness();
		double otherFitness = o.getFitness();
		if (myFitness <= otherFitness) {
			return -1;
		} else {
			return 1;
		}
	}
	
	public abstract boolean equalsChromosome(Chromosome c);
	
	public boolean equals(Object o) {
		if (!(o instanceof Chromosome)) {
			return false;
		}
		
		return equalsChromosome((Chromosome) o);
	}
	
	/**
	 * Method to print the details of the gene.
	 */
	public abstract void printGene();
}