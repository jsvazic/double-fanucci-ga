package com.arm.fanucci;

/**
 * Class used to define a Fanucci card.
 * 
 * @see com.arm.fanucci.IFanucci
 * 
 * @author jsvazic
 */
public class Card implements Comparable<Card>, IFanucci {
	public final short suit;
	public final short value;
	public final short group;
	
	/**
	 * Default constructor
	 * 
	 * @param suit The suite of the card.
	 * @param value The value of the card.
	 */
	public Card(short suit, short value) {
		this.suit = suit;
		this.value = value;
		this.group = getGroupId(suit);
	}

	/**
	 * Method to return the printable label for the card's value.
	 * @return
	 */
	public String getValueStr() {
		switch (value) {
			case POWER_NAUGHT:
				return "0";
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
				return "\u221E";
			default:
				return "? (" + value + ")";
		}
	}
	
	@Override
	public int compareTo(Card c) {
		if ((group == c.group && suit == c.suit && value == c.value)) {
			return 0;
		}
		
		// Sort first by suite, then by value (descending) for each suite
		if (group < c.group) {
			return -1;
		} else if (group > c.group) {
			return 1;
		} else {
			if (suit < c.suit) {
				return -1;
			} else if (suit > c.suit) {
				return 1;
			} else {
				if (value > c.value) {
					return -1;
				} else {
					return 1;
				}	
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Card)) {
			return false;
		}
		
		Card c = (Card) obj;
		
		return (group == c.group && suit == c.suit && value == c.value);
	}
	
	@Override
	public int hashCode() {
		return (new StringBuffer(group).append(suit).append(value))
				.toString().hashCode();
	}
		
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(getValueStr());
		sb.append(" of ").append(FanucciUtil.getSuitName(suit));
		
		return sb.toString();
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
	private static short getGroupId(short suitId) {
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
}