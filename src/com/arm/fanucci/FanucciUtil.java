package com.arm.fanucci;

/**
 * Utility class used for handling relationships between Fannuci card suits.
 * 
 * @author jsvazic
 */
public class FanucciUtil implements IFanucci {
	
	/*
	 6 Sets (0%):
		Ears, Mazes, Scythes
		Hives, Inkblots
		Plungers, Books, Zurfs
		Tops, Rain, Faces
		Bugs, Time
		Lamps, Fromps

	 2 Allied Factions (-50%):
		Bugs, Time – Plungers, Books, Zurfs
		Hives, Inkblots – Bugs, Time
		Plungers, Books, Zurfs – Hives, Inkblots

		Ears, Mazes, Scythes – Tops, Rain, Faces
		Lamps, Fromps – Ears, Mazes, Scythes
		Tops, Rain, Faces – Lamps, Fromps

	3 Neutral Groups (-100%):
		Hives, Inkblots – Tops, Rain, Faces
		Ears, Mazes, Scythes – Bugs, Time
		Plungers, Books, Zurfs – Lamps, Fromps

	3 Enemy Factions (-150%):
		Bugs, Time – Tops, Rain, Faces
		Bugs, Time – Lamps, Fromps

		Plungers, Books, Zurfs – Tops, Rain, Faces
		Plungers, Books, Zurfs – Ears, Mazes, Scythes

		Hives, Inkblots – Ears, Mazes, Scythes
		Hives, Inkblots – Lamps, Fromps

	 */

	/**
	 * Method to retrieve the weight-modifier for a given pair of suits.  The
	 * modifiers will be one of:
	 * <ul>
	 *    <li>0.0</li>
	 *    <li>0.5</li>
	 *    <li>1.0</li>
	 *    <li>1.5</li>
	 * </ul>
	 * depending on whether or not the two suits are from the same family, 
	 * are allies, neutral or enemies respectively.
	 * 
	 * @param firstSuite The first suite to compare.
	 * @param secondSuite The second suite to compare.
	 * 
	 * @return The modifier for the given pair of suits. 
	 */
	public static double getModifier(short firstSuite, short secondSuite) {
		if (areAllies(firstSuite, secondSuite)) {
			return 0.5;
		} else if (areNeutral(firstSuite, secondSuite)) {
			return 1.0;
		} else if (areEnemies(firstSuite, secondSuite)) {
			return 1.5;
		}
		
		return 0.0;
	}
	
	/**
	 * Helper method to determine whether or not two given suits are allies 
	 * or not.
	 * 
	 * @param suiteOne The first suite to check.
	 * @param suiteTwo The second suite to check.
	 * 
	 * @return <code>true</code> if the two suits are allies; 
	 * <code>false</code> otherwise.
	 */
	private static boolean areAllies(short suiteOne, short suiteTwo) {
		switch (suiteOne) {
			case SUIT_EARS:
			case SUIT_MAZES:
			case SUIT_SCYTHES:
				switch (suiteTwo) {
					case SUIT_TOPS:
					case SUIT_RAIN:
					case SUIT_FACES:
					case SUIT_LAMPS:
					case SUIT_FROMPS:
						return true;
					default:
						return false;
				}
			case SUIT_HIVES:
			case SUIT_INKBLOTS:
				switch (suiteTwo) {
					case SUIT_ZURFS:
					case SUIT_BOOKS:
					case SUIT_PLUNGERS:
					case SUIT_BUGS:
					case SUIT_TIME:
						return true;
					default:
						return false;
				}
			case SUIT_PLUNGERS:
			case SUIT_BOOKS:
			case SUIT_ZURFS:
				switch (suiteTwo) {
					case SUIT_HIVES:
					case SUIT_INKBLOTS:
					case SUIT_BUGS:
					case SUIT_TIME:
						return true;
					default:
						return false;
				}
			case SUIT_TOPS:
			case SUIT_RAIN:
			case SUIT_FACES:
				switch (suiteTwo) {
					case SUIT_MAZES:
					case SUIT_EARS:
					case SUIT_SCYTHES:
					case SUIT_LAMPS:
					case SUIT_FROMPS:
						return true;
					default:
						return false;
				}
			case SUIT_BUGS:
			case SUIT_TIME:
				switch (suiteTwo) {
					case SUIT_ZURFS:
					case SUIT_BOOKS:
					case SUIT_PLUNGERS:
					case SUIT_HIVES:
					case SUIT_INKBLOTS:
						return true;
					default:
						return false;
				}
			
			case SUIT_LAMPS:
			case SUIT_FROMPS:
				switch (suiteTwo) {
					case SUIT_TOPS:
					case SUIT_RAIN:
					case SUIT_FACES:
					case SUIT_MAZES:
					case SUIT_EARS:
					case SUIT_SCYTHES:
						return true;
					default:
						return false;
				}
			default:
				return false;
		}
	}
	
	/**
	 * Helper method to determine whether or not two given suits are neutral 
	 * or not.
	 * 
	 * @param suiteOne The first suite to check.
	 * @param suiteTwo The second suite to check.
	 * 
	 * @return <code>true</code> if the two suits are neutral with each 
	 * other; <code>false</code> otherwise.
	 */
	private static boolean areNeutral(short suiteOne, short suiteTwo) {
		switch (suiteOne) {
			case SUIT_EARS:
			case SUIT_MAZES:
			case SUIT_SCYTHES:
				switch (suiteTwo) {
					case SUIT_BUGS:
					case SUIT_TIME:
						return true;
					default:
						return false;
				}
			case SUIT_HIVES:
			case SUIT_INKBLOTS:
				switch (suiteTwo) {
					case SUIT_TOPS:
					case SUIT_RAIN:
					case SUIT_FACES:
						return true;
					default:
						return false;
				}
			case SUIT_PLUNGERS:
			case SUIT_BOOKS:
			case SUIT_ZURFS:
				switch (suiteTwo) {
					case SUIT_LAMPS:
					case SUIT_FROMPS:
						return true;
					default:
						return false;
				}
			case SUIT_TOPS:
			case SUIT_RAIN:
			case SUIT_FACES:
				switch (suiteTwo) {
					case SUIT_HIVES:
					case SUIT_INKBLOTS:
						return true;
					default:
						return false;
				}
			case SUIT_BUGS:
			case SUIT_TIME:
				switch (suiteTwo) {
					case SUIT_MAZES:
					case SUIT_EARS:
					case SUIT_SCYTHES:
						return true;
					default:
						return false;
				}
			case SUIT_LAMPS:
			case SUIT_FROMPS:
				switch (suiteTwo) {
					case SUIT_ZURFS:
					case SUIT_BOOKS:
					case SUIT_PLUNGERS:
						return true;
					default:
						return false;
				}
			default:
				return false;
		}
	}
	
	/**
	 * Helper method to determine whether or not two given suits are enemies 
	 * or not.
	 * 
	 * @param suiteOne The first suite to check.
	 * @param suiteTwo The second suite to check.
	 * 
	 * @return <code>true</code> if the two suits are enemies; 
	 * <code>false</code> otherwise.
	 */
	private static boolean areEnemies(short suiteOne, short suiteTwo) {
		switch (suiteOne) {
			case SUIT_EARS:
			case SUIT_MAZES:
			case SUIT_SCYTHES:
				switch (suiteTwo) {
					case SUIT_HIVES:
					case SUIT_INKBLOTS:
					case SUIT_ZURFS:
					case SUIT_BOOKS:
					case SUIT_PLUNGERS:
						return true;
					default:
						return false;
				}
			case SUIT_HIVES:
			case SUIT_INKBLOTS:
				switch (suiteTwo) {
					case SUIT_MAZES:
					case SUIT_EARS:
					case SUIT_SCYTHES:
					case SUIT_LAMPS:
					case SUIT_FROMPS:
						return true;
					default:
						return false;
				}
			case SUIT_PLUNGERS:
			case SUIT_BOOKS:
			case SUIT_ZURFS:
				switch (suiteTwo) {
					case SUIT_MAZES:
					case SUIT_EARS:
					case SUIT_SCYTHES:
					case SUIT_TOPS:
					case SUIT_RAIN:
					case SUIT_FACES:
						return true;
					default:
						return false;
				}
			case SUIT_TOPS:
			case SUIT_RAIN:
			case SUIT_FACES:
				switch (suiteTwo) {
					case SUIT_ZURFS:
					case SUIT_BOOKS:
					case SUIT_PLUNGERS:
					case SUIT_BUGS:
					case SUIT_TIME:
						return true;
					default:
						return false;
				}
			case SUIT_BUGS:
			case SUIT_TIME:
				switch (suiteTwo) {
					case SUIT_TOPS:
					case SUIT_RAIN:
					case SUIT_FACES:
					case SUIT_LAMPS:
					case SUIT_FROMPS:
						return true;
					default:
						return false;
				}
			case SUIT_LAMPS:
			case SUIT_FROMPS:
				switch (suiteTwo) {
					case SUIT_BUGS:
					case SUIT_TIME:
					case SUIT_HIVES:
					case SUIT_INKBLOTS:
						return true;
					default:
						return false;
				}
			default:
				return false;
		}
	}
	
	public static short getSuitCode(String suit) {
		if ("Books".equals(suit)) {
			return  SUIT_BOOKS;
		} else if ("Bugs".equals(suit)) {
			return SUIT_BUGS;
		} else if ("Ears".equals(suit)) {
			return SUIT_EARS;
		} else if ("Faces".equals(suit)) {
			return SUIT_FACES;
		} else if ("Fromps".equals(suit)) {
			return SUIT_FROMPS;
		} else if ("Hives".equals(suit)) {
			return SUIT_HIVES;
		} else if ("Inkblots".equals(suit)) {
			return SUIT_INKBLOTS;
		} else if ("Lamps".equals(suit)) {
			return SUIT_LAMPS;
		} else if ("Mazes".equals(suit)) {
			return SUIT_MAZES;
		} else if ("Plungers".equals(suit)) {
			return SUIT_PLUNGERS;
		} else if ("Rain".equals(suit)) {
			return SUIT_RAIN;
		} else if ("Scythes".equals(suit)) {
			return SUIT_SCYTHES;
		} else if ("Time".equals(suit)) {
			return SUIT_TIME;
		} else if ("Tops".equals(suit)) {
			return SUIT_TOPS;
		} else if ("Zurfs".equals(suit)) {
			return SUIT_ZURFS;
		}
		
		return SUIT_UNKNOWN;		
	}
	
	public static short getValueCode(String value) {
		if ("Naught".equals(value)) {
			return POWER_NAUGHT;
		} else if ("1".equals(value)) {
			return POWER_ONE;
		} else if ("2".equals(value)) {
			return POWER_TWO;
		} else if ("3".equals(value)) {
			return POWER_THREE;
		} else if ("4".equals(value)) {
			return POWER_FOUR;
		} else if ("5".equals(value)) {
			return POWER_FIVE;
		} else if ("6".equals(value)) {
			return POWER_SIX;
		} else if ("7".equals(value)) {
			return POWER_SEVEN;
		} else if ("8".equals(value)) {
			return POWER_EIGHT;
		} else if ("9".equals(value)) {
			return POWER_NINE;
		} else if ("Inifinity".equals(value)) {
			return POWER_INFINITY;
		}
		
		return ((short) 0);
	}

}