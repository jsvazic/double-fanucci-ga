package com.arm.fanucci;

import java.util.Set;
import java.util.TreeSet;

public class TestFitness implements IFanucci {

	public static void main(String[] args) {
		Set<Card> cards = new TreeSet<Card>();
		cards.add(new Card(FanucciUtil.getGroupId(SUIT_TOPS), SUIT_TOPS, 
				POWER_THREE));
		cards.add(new Card(FanucciUtil.getGroupId(SUIT_TOPS), SUIT_TOPS, 
				POWER_TWO));
		cards.add(new Card(FanucciUtil.getGroupId(SUIT_EARS), SUIT_EARS, 
				POWER_TWO));
		cards.add(new Card(FanucciUtil.getGroupId(SUIT_FROMPS), SUIT_FROMPS, 
				POWER_TWO));
		
		FanucciChromosome chromosome = new FanucciChromosome(
				new FanucciPopulation(new Card[0], 0), cards);
		
		System.out.println("Fitness: " + (100 - chromosome.getFitness()));
		System.out.println("--------------------------------");

		cards.clear();
		cards.add(new Card(FanucciUtil.getGroupId(SUIT_BUGS), SUIT_BUGS, 
				POWER_TWO));
		cards.add(new Card(FanucciUtil.getGroupId(SUIT_BUGS), SUIT_BUGS, 
				POWER_ONE));
		cards.add(new Card(FanucciUtil.getGroupId(SUIT_ZURFS), SUIT_ZURFS, 
				POWER_ONE));
		cards.add(new Card(FanucciUtil.getGroupId(SUIT_INKBLOTS), SUIT_INKBLOTS, 
				POWER_TWO));
		
		chromosome = new FanucciChromosome(new FanucciPopulation(
				new Card[0], 0), cards);
		
		System.out.println("Fitness: " + (100 - chromosome.getFitness()));

	}

}
