package com.arm.fanucci.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
	private Map<String, JPanel> panelMap;

	/**
	 * Default constructor.
	 */
	public CardPanel() {
		this.deck = Deck.getInstance();
		this.panelMap = new HashMap<String, JPanel>(15);
		init();
	}
	
	/**
	 * Helper method used to initialize the UI.
	 */
	private void init() {
		suitList = new JList(SUITS);
		suitList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		buttonPanel = new JPanel();
		cardLayout  = new CardLayout();
		
		buttonPanel.setLayout(cardLayout);
		buttonPanel.add(new JPanel(), BLANK);
		
		// Iterate over the suits, giving a custom panel for each.
		for (String suit : SUITS) {
			JPanel innerPanel = new JPanel();
			innerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			BufferedImage img = null;
			BufferedImage selectedImg = null;
			try {
				// Read the main image
				URL url = CardPanel.class.getResource("images/" + 
						suit.toLowerCase() + ".gif");
				img = ImageIO.read(url);
				
				// Now read the selected image
				url = CardPanel.class.getResource("images/" + 
						suit.toLowerCase() + "_selected.gif");
				selectedImg = ImageIO.read(url);
			} catch (IOException ex) {
				// Safe to ignore
			}
		
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
				
				short suitId = FanucciUtil.getSuitId(suit);
				short value   = FanucciUtil.getValue(label);
				Card c = new Card(suitId, value);
				if (deck.hasCard(c)) {
					button.setSelected(true);
				}
							
				innerPanel.add(button);			
			}
			
			panelMap.put(suit, innerPanel);
			buttonPanel.add(innerPanel, suit);
		}
		
		setLayout(new BorderLayout(5, 5));
		add(new JScrollPane(suitList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), 
				BorderLayout.WEST);
		
		add(new JScrollPane(buttonPanel, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
				BorderLayout.CENTER);
	
		addListeners();
		
		// Set a default selection for the suits
		suitList.setSelectedIndex(0);
	}
	
	/**
	 * Method to retrieve the currently selected index of the list.
	 * 
	 * @return The currently selected list index.
	 */
	public int getSelectedIndex() {
		return suitList.getSelectedIndex();
	}
	
	/**
	 * Method used to reset the selection.  If -1 is passed in or if the
	 * selected index is greater than the number of elements in the list,
	 * then the selection is cleared.
	 * 
	 * @param idx The index of the item in the list to select.
	 */
	public void setSelection(int idx) {
		if (idx < 0 || idx > suitList.getModel().getSize()) {
			suitList.clearSelection();
		} else {
			suitList.setSelectedIndex(idx);
		}
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
					String suit = (String) suitList.getSelectedValue();
					if (panelMap.containsKey(suit)) {
						updateButtonSelection(panelMap.get(suit));
					}
					
					cardLayout.show(buttonPanel, suit);
				}
			}			
		});		
	}
	
	/**
	 * Method used to update the button selections.  This method will check
	 * the buttons on the given panel and then mark them as selected or not
	 * based on the corresponding card existing in the deck.
	 * 
	 * @param panel The panel to update.
	 */
	private void updateButtonSelection(JPanel panel) {
		for (Component component : panel.getComponents()) {
			AbstractButton button = (AbstractButton) component;
			short suitId = FanucciUtil.getSuitId(
					(String) suitList.getSelectedValue());
			
			String str = ((FanucciCardImageIcon) button.getIcon()).getLabel();
			short value = FanucciUtil.getValue(str);
			
			Card c = new Card(suitId, value);
			if (deck.hasCard(c)) {
				button.setSelected(true);
			} else {
				button.setSelected(false);
			}
		}
		
	}

	/**
	 * Method used to handle button toggles.
	 * 
	 * @param e The event generated by the button toggle.
	 */
	private void handleButtonToggled(ActionEvent e) {
		AbstractButton button = (AbstractButton) e.getSource();
		String suit  = (String) suitList.getSelectedValue(); 
		short suitId = FanucciUtil.getSuitId(suit);
		String str = ((FanucciCardImageIcon) button.getIcon()).getLabel();
		short value = FanucciUtil.getValue(str);
		
		Card c = new Card(suitId, value);
		if (!button.isSelected()) {
			deck.removeCard(c);
		} else {
			deck.addCard(c);
			
			// Iterate over the sub-cards and make sure they
			// get auto-selected as well.
			JPanel panel = this.panelMap.get(suit);
			for (Component component : panel.getComponents()) {
				AbstractButton btn = (AbstractButton) component;
				if (btn == button) {
					break;
				}
				
				str = ((FanucciCardImageIcon) btn.getIcon()).getLabel();
				value = FanucciUtil.getValue(str);
				c = new Card(suitId, value);
				if (!btn.isSelected()) {
					btn.setSelected(true);
					deck.addCard(c);
				}
			}
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
}