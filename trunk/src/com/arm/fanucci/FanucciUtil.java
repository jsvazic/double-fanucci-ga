package com.arm.fanucci;

/**
 * Utility class used for handling relationships between Fannuci card suits.
 * 
 * @author jsvazic
 */
public class FanucciUtil implements IFanucci {

	/**
	 * Method to retrieve the weight-modifier for a given pair of groups.  The
	 * modifiers will be one of:
	 * <ul>
	 *    <li>0.0</li>
	 *    <li>0.5</li>
	 *    <li>1.0</li>
	 *    <li>1.5</li>
	 * </ul>
	 * depending on whether or not the two group IDs are from the same family,
	 * are allies, neutral or enemies respectively.
	 * 
	 * @param firstGroupId The first group ID to compare.
	 * @param secondGroupId The second group ID to compare.
	 * 
	 * @return The modifier for the given pair of suits. 
	 */
	public static double getModifier(short firstGroupId, short secondGroupId) {
		if (firstGroupId == secondGroupId) {
			return 0.0;
		}
		
		int idx = Math.abs(firstGroupId - secondGroupId) % 4;
		switch (idx) {
			case 0:	
			case 2:
				return 0.5; // Allies
			case 1: 
				return 1.5; // Enemies
			case 3: 
				return 1.0; // Neutral
			default:
				return 0.0; // Unknown
		}
	}

	/**
	 * Method to return a group ID given a suit ID.
	 * 
	 * @param suitId The ID of the suit to return the group ID for.
	 * 
	 * @return The group ID for the given suit, or <code>GROUP_UNKNOWN</code>
	 * if no matching group can be found.
	 * 
	 * @see com.arm.fanucci.IFanucci
	 */
	public static short getGroupId(short suitId) {
		switch (suitId) {
			case SUIT_BUGS:
			case SUIT_TIME:
				return GROUP_1;
			case SUIT_LAMPS:
			case SUIT_FROMPS:
				return GROUP_2;
			case SUIT_HIVES:
			case SUIT_INKBLOTS:
				return GROUP_3;
			case SUIT_MAZES:
			case SUIT_EARS:
			case SUIT_SCYTHES:
				return GROUP_4;
			case SUIT_ZURFS:
			case SUIT_BOOKS:
			case SUIT_PLUNGERS:
				return GROUP_5;
			case SUIT_TOPS:
			case SUIT_RAIN:
			case SUIT_FACES:
				return GROUP_6;
			default:
				return GROUP_UNKNOWN;
		}
	}
	
	/**
	 * Method to return the suit ID for a given suit plain-text name.
	 * 
	 * @param suit The suit ID for the given plain-text suit name.
	 * 
	 * @return The ID for the suit matching the name, or 
	 * <code>SUIT_UNKNOWN</code> if no matching ID can be found.
	 * 
	 * @see com.arm.fanucci.IFanucci
	 */
	public static short getSuitId(String suit) {
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
	
	/**
	 * Method to return the numeric power value for a given suit value. 
	 * 
	 * @param value The plain-text name of the card's value.
	 * 
	 * @return The associated power value with the face value of the card, or
	 * <code>0</code> if no value can be found.
	 * 
	 * @see com.arm.fanucci.IFanucci
	 */
	public static short getValue(String value) {
		if ("Naught".equals(value) || "0".equals(value)) {
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
		} else if ("Inifinity".equals(value) || "\u221E".equals(value)) {
			return POWER_INFINITY;
		}
		
		return POWER_UNKNOWN;
	}
	
	/**
	 * Method to retrieve the textual name of the specified suit.
	 * 
	 * @return The textual name of the specified suit.
	 */
	public static String getSuitName(short suit) {
		switch (suit) {
			case SUIT_BOOKS:
				return "Books";
			case SUIT_BUGS:
				return "Bugs";
			case SUIT_EARS:
				return "Ears";
			case SUIT_FACES:
				return "Faces";
			case SUIT_FROMPS:
				return "Fromps";
			case SUIT_HIVES:
				return "Hives";
			case SUIT_INKBLOTS:
				return "Inkblots";
			case SUIT_LAMPS:
				return "Lamps";
			case SUIT_MAZES:
				return "Mazes";
			case SUIT_PLUNGERS:
				return "Plungers";
			case SUIT_RAIN:
				return "Rain";
			case SUIT_SCYTHES:
				return "Scythes";
			case SUIT_TIME:
				return "Time";
			case SUIT_TOPS:
				return "Tops";
			case SUIT_ZURFS:
				return "Zurfs";
			default:
				return "Unknown";
		}	
	}
	
	public static String getValueString(short value) {
		switch (value) {
			case POWER_NAUGHT:
				return "Naught";
			case POWER_ONE:
				return "1";
			case POWER_TWO:
				return "2";
			case POWER_THREE:
				return "3";
			case POWER_FOUR:
				return "4";
			case POWER_FIVE:
				return "5";
			case POWER_SIX:
				return "6";
			case POWER_SEVEN:
				return "7";
			case POWER_EIGHT:
				return "8";
			case POWER_NINE:
				return "9";
			case POWER_INFINITY:
				return "Infinity";
			default:
				return "Unknown";
		}
	}

}