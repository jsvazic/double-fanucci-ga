package com.arm.fanucci.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.arm.fanucci.Card;
import com.arm.fanucci.FanucciUtil;

public class SolutionPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final int GAMBIT   = 0;
	public static final int MIND     = 1;
	public static final int BODY     = 2;
	public static final int SPIRIT   = 3;
	public static final int SIDEKICK = 4;
	
	private JPanel[] cardPanels;
	
	public SolutionPanel() {
		super();
		init();
	}
	
	private void init() {
		JPanel mindPanel = new JPanel();
		mindPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		mindPanel.setBorder(BorderFactory.createTitledBorder("Mind"));

		JPanel bodyPanel = new JPanel();
		bodyPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		bodyPanel.setBorder(BorderFactory.createTitledBorder("Body"));
		
		JPanel spiritPanel = new JPanel();
		spiritPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		spiritPanel.setBorder(BorderFactory.createTitledBorder("Spirit"));
		
		JPanel sidekickPanel = new JPanel();
		sidekickPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		sidekickPanel.setBorder(BorderFactory.createTitledBorder("Sidekick"));
		
		JPanel gambitPanel = new JPanel();
		gambitPanel.setLayout(new BorderLayout(5, 5));
		gambitPanel.setBorder(BorderFactory.createTitledBorder("Fanucci Gambit"));
		
		cardPanels = new JPanel[] { gambitPanel, mindPanel, bodyPanel, 
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

		add(sidekickPanel, new GridBagConstraints(1, 0, 1, 1, 0.5, 0.5, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, 
				new Insets(1, 1, 1, 1), 1, 1));

		add(gambitPanel, new GridBagConstraints(1, 1, 1, 2, 0.5, 0.5, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, 
				new Insets(1, 1, 1, 1), 1, 1));
	}
	
	public void updatePanel(int panel, Card[] cards) {
		cardPanels[panel].removeAll();
		for (int i = 0; i < cards.length && i < 4; i++) {
			BufferedImage img = CardHelper.getCardImage(
					FanucciUtil.getSuitName(cards[i].suit), false);
			
			JLabel label = new JLabel(new FanucciCardImageIcon(img, 
					cards[i].getValueStr()));
			
			switch(panel) {
				case MIND:
				case BODY:
				case SPIRIT:
				case SIDEKICK:
					cardPanels[panel].add(label);
					break;
				case GAMBIT:
					if (i == 0) {
						JPanel p = new JPanel();
						p.setLayout(new FlowLayout(FlowLayout.CENTER));
						p.add(label);
						cardPanels[panel].add(p, BorderLayout.NORTH);
					} else if (i == 1) {
						JPanel p = new JPanel();
						p.setLayout(new FlowLayout(FlowLayout.LEFT));
						p.add(label);
						cardPanels[panel].add(p, BorderLayout.EAST);						
					} else if (i == 2) {
						JPanel p = new JPanel();
						p.setLayout(new FlowLayout(FlowLayout.CENTER));
						p.add(label);
						cardPanels[panel].add(p, BorderLayout.SOUTH);						
					} else if (i == 3) {
						JPanel p = new JPanel();
						p.setLayout(new FlowLayout(FlowLayout.RIGHT));
						p.add(label);
						cardPanels[panel].add(p, BorderLayout.WEST);						
					}
					break;
			}
		}
	}
}