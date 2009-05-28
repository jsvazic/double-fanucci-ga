package com.arm.fanucci.test;

import java.text.NumberFormat;
import java.util.Set;
import java.util.TreeSet;

import com.arm.fanucci.Card;
import com.arm.fanucci.Chromosome;
import com.arm.fanucci.IFanucci;

public class ChromosomeTest implements IFanucci {
	public static void main(String[] args) {
		NumberFormat formatter = NumberFormat.getIntegerInstance();

		Set<Card> set = new TreeSet<Card>();
		set.add(new Card(SUIT_FROMPS, POWER_FOUR));
		set.add(new Card(SUIT_FROMPS, POWER_THREE));
		set.add(new Card(SUIT_TOPS, POWER_FIVE));
		set.add(new Card(SUIT_TOPS, POWER_FOUR));		
		Chromosome c = new Chromosome(null, set);
		System.out.println("Expecting 62: " + 
				formatter.format(100.0 - c.getFitness()));
		
		set.clear();
		set.add(new Card(SUIT_MAZES, POWER_FIVE));
		set.add(new Card(SUIT_MAZES, POWER_FOUR));
		set.add(new Card(SUIT_EARS, POWER_THREE));
		set.add(new Card(SUIT_TOPS, POWER_FIVE));
		c = new Chromosome(null, set);
		System.out.println("Expecting 72: " + 
				formatter.format(100.0 - c.getFitness()));

		set.clear();
		set.add(new Card(SUIT_TOPS, POWER_THREE));
		set.add(new Card(SUIT_EARS, POWER_TWO));
		set.add(new Card(SUIT_MAZES, POWER_THREE));
		set.add(new Card(SUIT_TOPS, POWER_FOUR));
		c = new Chromosome(null, set);
		System.out.println("Expecting 48: " + 
				formatter.format(100.0 - c.getFitness()));

		set.clear();
		set.add(new Card(SUIT_BUGS, POWER_TWO));
		set.add(new Card(SUIT_BUGS, POWER_ONE));
		set.add(new Card(SUIT_TIME, POWER_TWO));
		set.add(new Card(SUIT_TIME, POWER_ONE));
		c = new Chromosome(null, set);
		System.out.println("Expecting 24: " + 
				formatter.format(100.0 - c.getFitness()));

		set.clear();
		set.add(new Card(SUIT_BUGS, POWER_THREE));
		set.add(new Card(SUIT_BUGS, POWER_FOUR));
		set.add(new Card(SUIT_TIME, POWER_THREE));
		set.add(new Card(SUIT_TIME, POWER_FOUR));
		c = new Chromosome(null, set);
		System.out.println("Expecting 72: " + 
				formatter.format(100.0 - c.getFitness()));
	}
}