package com.arm.fanucci.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.arm.fanucci.Card;
import com.arm.fanucci.Deck;
import com.arm.fanucci.FanucciUtil;

public class CardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final String BLANK = "blank_panel";
	
	private static final String[] SUITS = {
			"Bugs", "Lamps", "Mazes", "Fromps", "Hives", "Inkblots", "Ears",
			"Time", "Scythes", "Zurfs", "Books", "Plungers", "Tops", "Rain",
			"Faces"
	};
	
	private static final String[] BUTTON_LABELS = {
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "\u221E"
	};
	
	private JList suitList;
	private JPanel buttonPanel;
	private CardLayout cardLayout;
	private Deck deck;

	/**
	 * Default constructor.
	 */
	public CardPanel(Deck deck) {
		this.deck = deck;
		init();
	}
	
	/**
	 * Helper method used to initialize the UI.
	 */
	private void init() {
		setLayout(new GridBagLayout());
		suitList = new JList(SUITS);
		suitList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(suitList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), 
					new GridBagConstraints(0, 0, 1, 1, 0.3, 0.3, 
							GridBagConstraints.WEST, GridBagConstraints.BOTH,
							new Insets(1, 1, 1, 1), 0, 0)
		);

		buttonPanel = new JPanel();
		cardLayout  = new CardLayout();
		
		buttonPanel.setLayout(cardLayout);
		buttonPanel.add(new JPanel(), BLANK);
		
		// Iterate over the suits, giving a custom panel for each.
		for (String suit : SUITS) {
			JPanel innerPanel = new JPanel();
			innerPanel.setLayout(new GridBagLayout());
			
			BufferedImage img = null;
			BufferedImage selectedImg = null;
			try {
				File imgFile = new File("images/" + suit.toLowerCase() + 
						".gif");
				if (imgFile.exists() && imgFile.canRead()) {
					img = ImageIO.read(imgFile);
				}
				
				imgFile = new File("images/" + suit.toLowerCase() + 
						"_selected.gif");
				if (imgFile.exists() && imgFile.canRead()) {
					selectedImg = ImageIO.read(imgFile);
				}
				
			} catch (IOException ex) {
				//ex.printStackTrace();
			}
		
			int xPos = 0;
			int yPos = 0;
			for (String label : BUTTON_LABELS) {
				JToggleButton button;
				if (img != null && selectedImg != null) {
					button = new JToggleButton(new FanucciCardImageIcon(
							img, label));
					
					button.setSelectedIcon(new FanucciCardImageIcon(
							selectedImg, label));
				} else {
					button = new JToggleButton(label);
				}
				
				button.setMargin(new Insets(0, 0, 0, 0));
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						handleButtonToggled(e);
					}
				});
			
				innerPanel.add(button, new GridBagConstraints(
						xPos, yPos, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, 
						GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 
						1, 1));
			
				xPos = (xPos + 1) % 3;
				if (xPos == 0) {
					++yPos;
				}
			}
			
			buttonPanel.add(innerPanel, suit);
		}
		
		add(new JScrollPane(buttonPanel, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
				new GridBagConstraints(1, 0, 1, 1, 0.7, 0.7, 
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
						new Insets(1, 1, 1, 1), 0, 0)
		);
	
		addListeners();
		
		// Set a default selection for the suits
		suitList.setSelectedIndex(0);
	}
	
	/**
	 * Method used to reset the selection.
	 */
	public void resetSelection() {
		suitList.clearSelection();
	}
	
	/**
	 * Method used to add listeners to the various UI components.
	 */
	private void addListeners() {
		suitList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					return;
				}
				if (suitList.getSelectedIndex() == -1) {
					cardLayout.show(buttonPanel, BLANK);
				} else {
					cardLayout.show(buttonPanel, 
							(String) suitList.getSelectedValue());
				}
			}			
		});		
	}

	/**
	 * Method used to handle button toggles.
	 * 
	 * @param e The event generated by the button toggle.
	 */
	private void handleButtonToggled(ActionEvent e) {
		AbstractButton button = (AbstractButton) e.getSource();
		short suitId = FanucciUtil.getSuitId(
				(String) suitList.getSelectedValue());
		
		short groupId = FanucciUtil.getGroupId(suitId);
		short value = FanucciUtil.getValue(button.getText());
		
		Card c = new Card(groupId, suitId, value);
		if (button.getModel().isSelected()) {
			deck.addCard(c);
		} else {
			deck.removeCard(c);
		}
	}
	
	/**
	 * Class used for drawing text onto images.
	 * 
	 * @author jsvazic
	 */
	private class FanucciCardImageIcon extends ImageIcon {
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
			this.font  = new Font("Verdana", Font.PLAIN, 12);
			this.label = label;
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
}