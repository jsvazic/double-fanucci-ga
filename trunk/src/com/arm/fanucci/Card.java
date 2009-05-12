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
	public Card(short suit, short value) {
		this.suit = suit;
		this.value = value;
		this.group = FanucciUtil.getGroupId(suit);
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
			if (suit < o.suit) {
				return -1;
			} else if (suit > o.suit) {
				return 1;
			} else {
				if (value > o.value) {
					return -1;
				} else if (value < o.value) {
					return 1;
				}
		
				// Group, suit and value are equal, i.e. the same card.
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
		StringBuffer sb = new StringBuffer(FanucciUtil.getValueString(value));
		sb.append(" of ").append(FanucciUtil.getSuitName(suit));
		
		return sb.toString();
	}
}