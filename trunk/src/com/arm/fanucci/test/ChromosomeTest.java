package com.arm.fanucci.test;

import java.text.NumberFormat;
import java.util.Set;
import java.util.TreeSet;

import com.arm.fanucci.Card;
import com.arm.fanucci.Chromosome;
import com.arm.fanucci.IFanucci;

public class ChromosomeTest implements IFanucci {
	public static void main(String[] args) {
		Set<Card> set = new TreeSet<Card>();
		set.add(new Card(SUIT_FROMPS, POWER_FOUR));
		set.add(new Card(SUIT_FROMPS, POWER_THREE));
		set.add(new Card(SUIT_TOPS, POWER_FIVE));
		set.add(new Card(SUIT_TOPS, POWER_FOUR));
		NumberFormat formatter = NumberFormat.getIntegerInstance();
		Chromosome c = new Chromosome(null, set);
		System.out.println("Expecting 62: " + 
				formatter.format(100.0 - c.getFitness()));
	}
}
