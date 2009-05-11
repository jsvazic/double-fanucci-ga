package com.arm.fanucci;

import java.util.Set;
import java.util.TreeSet;

public class TestFitness implements IFanucci {

	public static void main(String[] args) {
		Set<Card> cards = new TreeSet<Card>();
		cards.add(new Card(FanucciUtil.getGroupId(SUIT_FROMPS), SUIT_FROMPS, 
				POWER_ONE));
		cards.add(new Card(FanucciUtil.getGroupId(SUIT_MAZES), SUIT_MAZES, 
				POWER_TWO));
		cards.add(new Card(FanucciUtil.getGroupId(SUIT_TOPS), SUIT_TOPS, 
				POWER_ONE));
		cards.add(new Card(FanucciUtil.getGroupId(SUIT_TOPS), SUIT_TOPS, 
				POWER_TWO));
		
		FanucciChromosome chromosome = new FanucciChromosome(
				new FanucciPopulation(new Card[0], 0), cards);
		
		System.out.println("Expected Fitness : 16.0");
		System.out.println("Actual Fitness   : " + (100 - chromosome.getFitness()));
		System.out.println("--------------------------------");
	}

}
