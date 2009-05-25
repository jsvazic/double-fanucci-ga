package com.arm.fanucci.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Class used for drawing text onto images.
 * 
 * @author jsvazic
 */
public class FanucciCardImageIcon extends ImageIcon {
	private static final long serialVersionUID = 1L;
	private Font font;
	private String label;
	
	/**
	 * Default constructor.
	 * 
	 * @param img The image to use for the icon.
	 * @param label The label for the card.
	 */
	public FanucciCardImageIcon(Image img, String label) {
		super(img);
		this.font  = new Font("Helvedica", Font.PLAIN, 12);
		this.label = label;
	}
	
	/**
	 * Method to retrieve the card label. 
	 * 
	 * @return The label of the card.
	 */
	public String getLabel() {
		return label;
	}
	
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		super.paintIcon(c, g, x, y);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(font);
		g2d.setColor(Color.BLACK);
		g2d.drawString(label, 5, 15);
		g2d.translate(c.getWidth() - 5, c.getHeight() - 15);			
		g2d.rotate(Math.PI); // Rotate 180 degrees
		g2d.drawString(label, 0, 0);
		g2d.translate(c.getWidth() - 5, c.getHeight() - 15);			
	}
}