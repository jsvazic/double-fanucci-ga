package com.arm.fanucci.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.arm.fanucci.Card;
import com.arm.fanucci.Deck;
import com.arm.fanucci.FanucciUtil;
import com.arm.fanucci.IFanucci;

public class CardPanel extends JPanel implements IFanucci {

	private static final long serialVersionUID = 1L;
	
	private static final String BLANK = "blank_panel";
	
	private static final String[] SUITS = {
			"Bugs", "Lamps", "Mazes", "Fromps", "Hives", "Inkblots", "Ears", 
			"Time", "Scythes", "Zurfs", "Books", "Plungers", "Tops", "Rain",
			"Faces", "Fanucci Face"
	};
	
	private static final short[] CARD_VALUES = {
			POWER_NAUGHT, POWER_ONE, POWER_TWO, POWER_THREE, POWER_FOUR, 
			POWER_FIVE, POWER_SIX, POWER_SEVEN, POWER_EIGHT, POWER_NINE, 
			POWER_INFINITY
	};

	private static final short[] FACE_CARDS = {
			FACE_BEAUTY, FACE_DEATH, FACE_GRANOLA, FACE_GRUE, FACE_JESTER,
			FACE_LIGHT, FACE_LOBSTER, FACE_SNAIL, FACE_TIME
	};
	
	private JList suitList;
	private JPanel buttonPanel;
	private CardLayout cardLayout;
	private Deck deck;
	private Map<String, JPanel> panelMap;
	private JFrame mainFrame;

	/**
	 * Default constructor.
	 */
	public CardPanel(JFrame frame) {
		this.deck = Deck.getInstance();
		this.panelMap = new HashMap<String, JPanel>(15);
		this.mainFrame = frame;
		init();
	}
	
	/**
	 * Helper method used to initialize the UI.
	 */
	private void init() {
		suitList = new JList(SUITS);
		suitList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		suitList.setVisibleRowCount(10);

		buttonPanel = new JPanel();
		cardLayout  = new CardLayout();
		
		buttonPanel.setLayout(cardLayout);
		buttonPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Total cards - 0"));
		
		buttonPanel.add(new JPanel(), BLANK);
		
		// Iterate over the suits, giving a custom panel for each.
		for (String suit : SUITS) {
			JPanel innerPanel = new JPanel();
			innerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
			short[] arr = ("Fanucci Face".equals(suit) ? FACE_CARDS : 
					CARD_VALUES);
			
			short suitId = ("Fanucci Face".equals(suit) ? SUIT_FACE_ALL : 
					FanucciUtil.getSuitId(suit));
			
			for (short cardValue : arr) {
				Card card = new Card(suitId, cardValue);
				BufferedImage img = ImageHelper.getCardImage(card);
				RescaleOp op = new RescaleOp(new float[] { 0.8f, 0.8f, 0.8f }, 
						new float[] { 0.0f, 0.0f, 0.0f } , null);
				
				BufferedImage selectedImg = op.filter(img, null);
				FanucciCardButton button = new FanucciCardButton(
						new ImageIcon(img), card);
				
				button.setSelectedIcon(new ImageIcon(selectedImg));
				button.setMargin(new Insets(0, 0, 0, 0));
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						handleButtonToggled(e);
					}
				});
				
				if (deck.hasCard(card)) {
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
			FanucciCardButton button = (FanucciCardButton) component;
			Card c = button.getCard();
			if (deck.hasCard(c)) {
				button.setSelected(true);
			} else {
				button.setSelected(false);
			}
		}
		
		updateCardSelectionCount();
	}

	/**
	 * Method used to handle button toggles.
	 * 
	 * @param e The event generated by the button toggle.
	 */
	private void handleButtonToggled(ActionEvent e) {
		FanucciCardButton button = (FanucciCardButton) e.getSource();
		String suit = (String) suitList.getSelectedValue();
		JPanel panel = this.panelMap.get(suit);
		boolean deSelect = false;
		Card card = button.getCard();
		
		String title = mainFrame.getTitle();
		if (!title.startsWith("*")) {
			mainFrame.setTitle("*" + title);
		}
		
		try {
			if (button.isSelected()) {
				deck.addCard(card);
			} else {
				deck.removeCard(card);
			}

			// If this is a face card, then we're done.
			if (card.suit == IFanucci.SUIT_FACE_ALL) {
				return;
			}
			
			// Iterate over the sub-cards and make sure they
			// get auto-selected as well.
			for (Component component : panel.getComponents()) {
				FanucciCardButton btn = (FanucciCardButton) component;
				card = btn.getCard();
				if (btn == button) {
					deSelect = true;
					continue;
				}
				if (!btn.isSelected() && !deSelect) {
					btn.setSelected(true);
					deck.addCard(card);
				} else if (btn.isSelected() && deSelect) {
					btn.setSelected(false);
					deck.removeCard(card);
				}
			}
		} finally {
			updateCardSelectionCount();
		}
	}
	
	private class FanucciCardButton extends JToggleButton {
		private static final long serialVersionUID = 1L;
		
		private final Card card;  // The card associated with the button
		
		public FanucciCardButton(Icon icon, Card card) {
			super(icon);
			this.card = card;
			setToolTipText(card.toString());
		}
		
		public Card getCard() {
			return card;
		} 
	}
	
	private void updateCardSelectionCount() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				((TitledBorder) buttonPanel.getBorder()).setTitle(
						"Total cards - " + deck.size());
				
				buttonPanel.repaint();
			}			
		});
	}
}