package com.arm.fanucci.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import com.arm.fanucci.Card;
import com.arm.fanucci.Chromosome;

public class SolutionPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final int GAMBIT   = 0;
	public static final int MIND     = 1;
	public static final int BODY     = 2;
	public static final int SPIRIT   = 3;
	public static final int SIDEKICK = 4;
	
	private SlotPanel[] cardPanels;
	
	public SolutionPanel() {
		init();
	}
	
	private void init() {
		SlotPanel mindPanel = new SlotPanel("Mind");
		SlotPanel bodyPanel = new SlotPanel("Body");
		SlotPanel spiritPanel = new SlotPanel("Spirit");
		SlotPanel sidekickPanel = new SlotPanel("Sidekick");
		GambitPanel gambitPanel = new GambitPanel();
		
		cardPanels = new SlotPanel[] { gambitPanel, mindPanel, bodyPanel, 
				spiritPanel, sidekickPanel };
		
		setLayout(new GridBagLayout());
		add(mindPanel, new GridBagConstraints(0, 0, 1, 1, 0.5, 0.5, 
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 
				new Insets(1, 1, 1, 1), 1, 1));

		add(bodyPanel, new GridBagConstraints(0, 1, 1, 1, 0.5, 0.5, 
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 
				new Insets(1, 1, 1, 1), 1, 1));

		add(spiritPanel, new GridBagConstraints(0, 2, 1, 1, 0.5, 0.5, 
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 
				new Insets(1, 1, 1, 1), 1, 1));

		add(sidekickPanel, new GridBagConstraints(1, 0, 1, 1, 0.5, 0.5, 
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 
				new Insets(1, 1, 1, 1), 1, 1));

		add(gambitPanel, new GridBagConstraints(2, 0, 1, 3, 0.5, 0.5, 
				GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, 
				new Insets(1, 1, 1, 1), 1, 1));
	}
		
	public void updatePanel(int panel, Chromosome c) {
		cardPanels[panel].layoutCards(c);
	}
	
	private class SlotPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		protected String title;
		protected TitledBorder border;
		protected JLabel[] cards;
		
		public SlotPanel(String title) {
			this.title  = title;
			this.border = BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(), title + " - 0");
			
			setLayout(new GridBagLayout());
			
			cards = new JLabel[4];
			for (int i = 0; i < cards.length; i++) {
				cards[i] = new JLabel(new ImageIcon(CardHelper.loadImage(
						"blank.png")));
			}

			setBorder(border);
			init();
		}
		
		protected void init() {
			for (int i = 0; i < cards.length; i++) {
				add(cards[i], new GridBagConstraints(i, 0, 1, 1, 0.5, 0.5, 
						GridBagConstraints.CENTER, GridBagConstraints.NONE, 
						new Insets(1, 1, 1, 1), 0, 0));
			}
		}
				
		public void layoutCards(final Chromosome chromosome) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					Card[] arr = chromosome.getCards();
					for (int i = 0; i < arr.length; i++) {
						BufferedImage img = CardHelper.getCardImage(arr[i]);
						cards[i].setIcon(new ImageIcon(img));
						cards[i].setToolTipText(arr[i].toString());
					}

					NumberFormat formatter = NumberFormat.getIntegerInstance();
					border.setTitle(title + " - " + formatter.format(
							100.0 - chromosome.getFitness()));
					
					repaint();
				}
			});
		}
	}
	
	private class GambitPanel extends SlotPanel {
		private static final long serialVersionUID = 1L;

		public GambitPanel() {
			super("Fanucci Gambit");
			setLayout(new GridBagLayout());
			init();
		}
		
		protected void init() {
			add(cards[0], new GridBagConstraints(0, 0, 2, 1, 0.5, 0.5, 
					GridBagConstraints.SOUTH, GridBagConstraints.NONE, 
					new Insets(1, 1, 1, 1), 0, 0));

			add(cards[1], new GridBagConstraints(1, 1, 1, 1, 0.5, 0.5, 
					GridBagConstraints.WEST, GridBagConstraints.NONE, 
					new Insets(1, 1, 1, 1), 0, 0));
			
			add(cards[2], new GridBagConstraints(0, 2, 2, 1, 0.5, 0.5, 
					GridBagConstraints.NORTH, GridBagConstraints.NONE, 
					new Insets(1, 1, 1, 1), 0, 0));

			add(cards[3], new GridBagConstraints(0, 1, 1, 1, 0.5, 0.5, 
					GridBagConstraints.EAST, GridBagConstraints.NONE, 
					new Insets(1, 1, 1, 1), 0, 0));
		}
	}
}