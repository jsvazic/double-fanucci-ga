package com.arm.fanucci;

/**
 * Utility class used for handling relationships between Fannuci card suits.
 * 
 * @author jsvazic
 */
public class FanucciUtil implements IFanucci {

	
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
		if ("Books".equalsIgnoreCase(suit)) {
			return  SUIT_BOOKS;
		} else if ("Bugs".equalsIgnoreCase(suit)) {
			return SUIT_BUGS;
		} else if ("Ears".equalsIgnoreCase(suit)) {
			return SUIT_EARS;
		} else if ("Faces".equalsIgnoreCase(suit)) {
			return SUIT_FACES;
		} else if ("Fromps".equalsIgnoreCase(suit)) {
			return SUIT_FROMPS;
		} else if ("Hives".equalsIgnoreCase(suit)) {
			return SUIT_HIVES;
		} else if ("Inkblots".equalsIgnoreCase(suit)) {
			return SUIT_INKBLOTS;
		} else if ("Lamps".equalsIgnoreCase(suit)) {
			return SUIT_LAMPS;
		} else if ("Mazes".equalsIgnoreCase(suit)) {
			return SUIT_MAZES;
		} else if ("Plungers".equalsIgnoreCase(suit)) {
			return SUIT_PLUNGERS;
		} else if ("Rain".equalsIgnoreCase(suit)) {
			return SUIT_RAIN;
		} else if ("Scythes".equalsIgnoreCase(suit)) {
			return SUIT_SCYTHES;
		} else if ("Time".equalsIgnoreCase(suit)) {
			return SUIT_TIME;
		} else if ("Tops".equalsIgnoreCase(suit)) {
			return SUIT_TOPS;
		} else if ("Zurfs".equalsIgnoreCase(suit)) {
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
		} else if ("Infinity".equals(value) || "\u221E".equals(value)) {
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