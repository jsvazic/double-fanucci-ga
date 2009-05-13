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
		this.group = FanucciUtil.getGroupId(suit);
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
		StringBuffer sb = new StringBuffer(FanucciUtil.getValueString(value));
		sb.append(" of ").append(FanucciUtil.getSuitName(suit));
		
		return sb.toString();
	}
}