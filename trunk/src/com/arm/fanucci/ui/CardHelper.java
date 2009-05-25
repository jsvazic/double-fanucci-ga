package com.arm.fanucci.ui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * Class used for loading Fanucci card image resources, and keeping a cache 
 * to speed up subsequent re-loads.
 * 
 * @author jsvazic 
 */
public class CardHelper {

	private static final Map<String, BufferedImage> images = 
		new HashMap<String, BufferedImage>();
	
	
	/**
	 * Method used to retrieve a <code>BufferedImage</code> for a specified suit.
	 * 
	 * @param suit The suit to retrieve the image for.
	 * @param isSelected Whether or not the suit is selected.
	 * 
	 * @return The corresponding <code>BufferedImage</code> instance, or 
	 * <code>null</code> if one could not be found/loaded.
	 */
	public static final BufferedImage getCardImage(String suit, 
			boolean isSelected) {
		
		String key = new StringBuffer(suit.toLowerCase()).append(isSelected)
				.toString();
		
		if (!images.containsKey(key)) {
			BufferedImage img = null;
			try {
				// Read the main image
				URL url;
				if (isSelected) {
					url = CardPanel.class.getResource("images/" + 
							suit.toLowerCase() + "_selected.gif");
				} else {
					url = CardHelper.class.getResource("images/" + 
							suit.toLowerCase() + ".gif");
				}

				img = ImageIO.read(url);
				images.put(key, img);
			} catch (IOException ex) {
				// Safe to ignore
			}

		}

		return images.get(key);
	}	
}