package com.arm.fanucci.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import com.arm.fanucci.FanucciUtil;

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
				GridBagConstraints.CENTER, GridBagConstraints.NONE, 
				new Insets(1, 1, 1, 1), 1, 1));

		add(bodyPanel, new GridBagConstraints(0, 1, 1, 1, 0.5, 0.5, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, 
				new Insets(1, 1, 1, 1), 1, 1));

		add(spiritPanel, new GridBagConstraints(0, 2, 1, 1, 0.5, 0.5, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, 
				new Insets(1, 1, 1, 1), 1, 1));

		add(gambitPanel, new GridBagConstraints(1, 0, 1, 2, 0.5, 0.5, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, 
				new Insets(1, 1, 1, 1), 1, 1));

		add(sidekickPanel, new GridBagConstraints(1, 2, 1, 1, 0.5, 0.5, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, 
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
			
			setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
			
			cards = new JLabel[4];
			for (int i = 0; i < cards.length; i++) {
				cards[i] = new JLabel(new ImageIcon(CardHelper.loadImage(
						"blank.gif")));
			}

			setBorder(border);
			init();
		}
		
		protected void init() {
			for (int i = 0; i < cards.length; i++) {
				add(cards[i]);
			}
		}
				
		public void layoutCards(final Chromosome chromosome) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					Card[] arr = chromosome.getCards();
					for (int i = 0; i < arr.length; i++) {
						BufferedImage img = CardHelper.getCardImage(
								FanucciUtil.getSuitName(arr[i].suit), false);
						
						cards[i].setIcon(new FanucciCardImageIcon(img, 
								arr[i].getValueStr()));
					}

					NumberFormat formatter = NumberFormat.getIntegerInstance();
					border.setTitle(title + " - " + formatter.format(
							100.0 - chromosome.getFitness()));
					//setBorder(border);
					repaint();
				}
			});
		}
	}
	
	private class GambitPanel extends SlotPanel {
		private static final long serialVersionUID = 1L;

		public GambitPanel() {
			super("Fanucci Gambit");
			setLayout(new BorderLayout(1, 1));
			init();
		}
		
		protected void init() {
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout(FlowLayout.CENTER));
			p.add(cards[0]);
			add(p, BorderLayout.NORTH);

			p = new JPanel();
			p.setLayout(new FlowLayout(FlowLayout.LEFT));
			p.add(cards[1]);
			add(p, BorderLayout.EAST);
			
			p = new JPanel();
			p.setLayout(new FlowLayout(FlowLayout.CENTER));
			p.add(cards[2]);
			add(p, BorderLayout.SOUTH);

			p = new JPanel();
			p.setLayout(new FlowLayout(FlowLayout.RIGHT));
			p.add(cards[3]);
			add(p, BorderLayout.WEST);			
		}
	}
}