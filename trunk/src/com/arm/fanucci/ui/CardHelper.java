package com.arm.fanucci.ui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.arm.fanucci.Card;
import com.arm.fanucci.FanucciUtil;
import com.arm.fanucci.IFanucci;

/**
 * Class used for loading Fanucci card image resources, and keeping a cache 
 * to speed up subsequent re-loads.
 * 
 * @author jsvazic 
 */
public class CardHelper implements IFanucci {
	
	private static final Map<String, BufferedImage> images = 
		new HashMap<String, BufferedImage>();
	
	
	/**
	 * Method used to retrieve a <code>BufferedImage</code> for a specified suit.
	 * 
	 * @param card The card to retrieve the image for.
	 * 
	 * @return The corresponding <code>BufferedImage</code> instance, or 
	 * <code>null</code> if one could not be found/loaded.
	 */
	public static final BufferedImage getCardImage(Card card) {
		String suit = FanucciUtil.getSuitName(card.suit).toLowerCase();
		if (!images.containsKey(card.toString())) {
			String url = suit + "-" + getSuffix(card.value) + ".png";
			images.put(card.toString(), loadImage(url));
		}

		return images.get(card.toString());
	}
	
	private static String getSuffix(short value) {
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
				return "10";
		}
		
		return null;
	}

	public static BufferedImage loadImage(String url) {
		BufferedImage img = null;
		try {
			// Read the image
			img = ImageIO.read(CardHelper.class.getResource("images/" + url));
		} catch (IOException ex) {
			// Safe to ignore
		}
		
		return img;
	}
}