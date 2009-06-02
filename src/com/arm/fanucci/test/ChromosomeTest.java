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
		System.out.println("Expecting 62: " + formatter.format(c.getScore()));
		
		set.clear();
		set.add(new Card(SUIT_MAZES, POWER_FIVE));
		set.add(new Card(SUIT_MAZES, POWER_FOUR));
		set.add(new Card(SUIT_EARS, POWER_THREE));
		set.add(new Card(SUIT_TOPS, POWER_FIVE));
		c = new Chromosome(null, set);
		System.out.println("Expecting 72: " + formatter.format(c.getScore()));

		set.clear();
		set.add(new Card(SUIT_TOPS, POWER_THREE));
		set.add(new Card(SUIT_EARS, POWER_TWO));
		set.add(new Card(SUIT_MAZES, POWER_THREE));
		set.add(new Card(SUIT_TOPS, POWER_FOUR));
		c = new Chromosome(null, set);
		System.out.println("Expecting 48: " + formatter.format(c.getScore()));

		set.clear();
		set.add(new Card(SUIT_BUGS, POWER_TWO));
		set.add(new Card(SUIT_BUGS, POWER_ONE));
		set.add(new Card(SUIT_TIME, POWER_TWO));
		set.add(new Card(SUIT_TIME, POWER_ONE));
		c = new Chromosome(null, set);
		System.out.println("Expecting 24: " + formatter.format(c.getScore()));

		set.clear();
		set.add(new Card(SUIT_BUGS, POWER_THREE));
		set.add(new Card(SUIT_BUGS, POWER_FOUR));
		set.add(new Card(SUIT_TIME, POWER_THREE));
		set.add(new Card(SUIT_TIME, POWER_FOUR));
		c = new Chromosome(null, set);
		System.out.println("Expecting 72: " + formatter.format(c.getScore()));

		set.clear();
		set.add(new Card(SUIT_BUGS, POWER_INFINITY));
		set.add(new Card(SUIT_BUGS, POWER_FIVE));
		set.add(new Card(SUIT_INKBLOTS, POWER_SIX));
		set.add(new Card(SUIT_INKBLOTS, POWER_FIVE));
		c = new Chromosome(null, set);
		double score = (c.getScore() > 100.0) ? 100.0 : c.getScore();
		System.out.println("Expecting 100: " + formatter.format(score));

		set.clear();
		set.add(new Card(SUIT_LAMPS, POWER_SEVEN));
		set.add(new Card(SUIT_LAMPS, POWER_SIX));
		set.add(new Card(SUIT_FROMPS, POWER_FIVE));
		set.add(new Card(SUIT_FROMPS, POWER_THREE));
		c = new Chromosome(null, set);
		score = (c.getScore() > 100.0) ? 100.0 : c.getScore();
		System.out.println("Expecting 100: " + formatter.format(score));
		
		set.clear();
		set.add(new Card(SUIT_HIVES, POWER_FIVE));
		set.add(new Card(SUIT_HIVES, POWER_FOUR));
		set.add(new Card(SUIT_INKBLOTS, POWER_SEVEN));
		set.add(new Card(SUIT_INKBLOTS, POWER_FOUR));
		c = new Chromosome(null, set);
		score = (c.getScore() > 100.0) ? 100.0 : c.getScore();
		System.out.println("Expecting 100: " + formatter.format(score));

		set.clear();
		set.add(new Card(SUIT_MAZES, POWER_FOUR));
		set.add(new Card(SUIT_MAZES, POWER_THREE));
		set.add(new Card(SUIT_SCYTHES, POWER_SEVEN));
		set.add(new Card(SUIT_SCYTHES, POWER_SIX));
		c = new Chromosome(null, set);
		score = (c.getScore() > 100.0) ? 100.0 : c.getScore();
		System.out.println("Expecting 100: " + formatter.format(score));

		set.clear();
		set.add(new Card(SUIT_BUGS, POWER_NINE));
		set.add(new Card(SUIT_BUGS, POWER_SEVEN));
		set.add(new Card(SUIT_ZURFS, POWER_FOUR));
		set.add(new Card(SUIT_BOOKS, POWER_FOUR));
		c = new Chromosome(null, set);
		score = (c.getScore() > 100.0) ? 100.0 : c.getScore();
		System.out.println("Expecting 100: " + formatter.format(score));
	}
}