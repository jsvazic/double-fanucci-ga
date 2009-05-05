package com.arm.fanucci;

/**
 * Class used to define a Fanucci card.
 * 
 * @see com.arm.fanucci.IFanucci
 * 
 * @author jsvazic
 */
public class Card implements Comparable<Card>, IFanucci {

	private short suit;
	private short value;
	private short group;
	
	/**
	 * Default constructor
	 * 
	 * @param suit The suite of the card.
	 * @param value The value of the card.
	 */
	public Card(short group, short suit, short value) {
		this.suit = suit;
		this.value = value;
		this.group = group;
	}
	
	/**
	 * Method to retrieve the suite of the card.
	 * 
	 * @return The suite of the card.
	 */
	public short getSuit() {
		return suit;
	}
	
	/**
	 * Method to retrieve the value of the card.
	 * 
	 * @return The value of the card.
	 */
	public short getValue() {
		return value;
	}
	
	/**
	 * Method used to return the group this card belongs to.
	 * 
	 * @return The group the card belongs to.
	 */
	public short getGroup() {
		return group;
	}

	@Override
	public int compareTo(Card o) {
		// Sort first by suite, then by value (descending) for each suite
		if (group < o.group) {
			return -1;
		} else if (group > o.group) {
			return 1;
		} else {
			if (value > o.value) {
				return -1;
			} else if (value < o.value) {
				return 1;
			} else {
				if (suit < o.suit) {
					return -1;
				} else if (suit > o.suit) {
					return 1;
				}
		
				// Group, value and suit are all the same, i.e. the same card.
				return 0;
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Card)) {
			return false;
		}
		
		return (compareTo((Card) obj) == 0);
	}
	
	@Override
	public int hashCode() {
		return (new StringBuffer(group).append(suit).append(value))
				.toString().hashCode();
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		switch (value) {
			case POWER_NAUGHT:
				sb.append("Naught");
				break;
			case POWER_ONE:
				sb.append('1');
				break;
			case POWER_TWO:
				sb.append('2');
				break;
			case POWER_THREE:
				sb.append('3');
				break;
			case POWER_FOUR:
				sb.append('4');
				break;
			case POWER_FIVE:
				sb.append('5');
				break;
			case POWER_SIX:
				sb.append('6');
				break;
			case POWER_SEVEN:
				sb.append('7');
				break;
			case POWER_EIGHT:
				sb.append('8');
				break;
			case POWER_NINE:
				sb.append('9');
				break;
			case POWER_INFINITY:
				sb.append('\u221E');
		}
		sb.append(" of ");
		switch (suit) {
			case SUIT_BOOKS:
				sb.append("Books");
				break;
			case SUIT_BUGS:
				sb.append("Bugs");
				break;
			case SUIT_EARS:
				sb.append("Ears");
				break;
			case SUIT_FACES:
				sb.append("Faces");
				break;
			case SUIT_FROMPS:
				sb.append("Fromps");
				break;
			case SUIT_HIVES:
				sb.append("Hives");
				break;
			case SUIT_INKBLOTS:
				sb.append("Inkblots");
				break;
			case SUIT_LAMPS:
				sb.append("Lamps");
				break;
			case SUIT_MAZES:
				sb.append("Mazes");
				break;
			case SUIT_PLUNGERS:
				sb.append("Plungers");
				break;
			case SUIT_RAIN:
				sb.append("Rain");
				break;
			case SUIT_SCYTHES:
				sb.append("Scythes");
				break;
			case SUIT_TIME:
				sb.append("Time");
				break;
			case SUIT_TOPS:
				sb.append("Tops");
				break;
			case SUIT_ZURFS:
				sb.append("Zurfs");
				break;
			default:
				sb.append("Unknown?");
		}
		
		return sb.toString();
	}
}