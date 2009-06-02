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
public class ImageHelper implements IFanucci {
	
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
		if (!images.containsKey(card.toString())) {
			StringBuilder url = new StringBuilder();
			if (card.suit != SUIT_FACE_ALL) {
				String suit = FanucciUtil.getSuitName(card.suit).toLowerCase();
				url.append(suit).append('-');
			}
			url.append(FanucciUtil.getValueString(card.value).toLowerCase());
			url.append(".png"); 
			images.put(card.toString(), loadImage(url.toString()));
		}

		return images.get(card.toString());
	}
	
	public static BufferedImage loadImage(String url) {
		BufferedImage img = null;
		if (!images.containsKey(url)) {
			try {
				// Read the image
				img = ImageIO.read(ImageHelper.class.getResource(
						"images/" + url));
				
				images.put(url, img);
			} catch (IOException ex) {
				// Safe to ignore
			}
		}
		
		return images.get(url);
	}
}