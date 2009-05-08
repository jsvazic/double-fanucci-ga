package com.arm.fanucci.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.arm.fanucci.Deck;
import com.arm.fanucci.DeckController;
import com.arm.fanucci.FanucciCalc;
import com.arm.fanucci.OptionsController;
import com.arm.fanucci.SimulatorOptions;
import com.arm.genetic.Chromosome;

/**
 * Main class for the UI.
 * 
 * @author jsvazic
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private CardPanel cardPanel;
	private SimulatorOptions simOptions;
	private JTextArea outputArea;

	/**
	 * Default constructor.
	 */
	public MainFrame() {
		super("Yet Another Double Fanucci Calculator (YADFC)");
		simOptions = OptionsController.loadOptions();
		init();
	}
	
	/**
	 * Helper method used to initialize the UI.
	 */
	private void init() {
		// Create our menu
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		JMenu simMenu = new JMenu("Simulator");
		simMenu.setMnemonic(KeyEvent.VK_S);
		
		fileMenu.add(new JMenuItem(new LoadAction(this)));
		fileMenu.add(new JMenuItem(new SaveAction(this)));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(new ExitAction()));
		
		simMenu.add(new JMenuItem(new RunAction()));
		simMenu.addSeparator();
		simMenu.add(new JMenuItem(new OptionsAction()));
		
		menuBar.add(fileMenu);
		menuBar.add(simMenu);
		
		setJMenuBar(menuBar);
		
		cardPanel = new CardPanel();
		outputArea = new JTextArea(10, 20);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(5, 5));
		contentPane.add(cardPanel, BorderLayout.CENTER);
		contentPane.add(new JScrollPane(outputArea, 
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), 
				BorderLayout.SOUTH);
		
		setContentPane(contentPane);
		setSize(400, 550);
				
		addListeners();
	}
	
	/**
	 * Helper method used to add any associated listeners to the frame.
	 */
	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				shutdown();
			}
		});
	}
	
	/**
	 * Method used to reset the selection in the suit list.
	 */
	public void resetSelection() {
		cardPanel.resetSelection();
	}
	
	/**
	 * Method used to terminate the application.
	 */
	private void shutdown() {
		try {
			OptionsController.saveOptions(simOptions);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, 
					"Failed to save the options: " + ex.getMessage(),
					"Error Saving Options", JOptionPane.ERROR_MESSAGE);
		}
		
		dispose();
		System.exit(0);
	}
	
	/**
	 * Main method for executing the UI.
	 * 
	 * @param args Command line arguments (ignored).
	 * 
	 * @throws Exception Thrown if there was an error bringing up the display.
	 */
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFrame frame = new MainFrame();
		
		// Center the frame.
		Toolkit toolkit = Toolkit.getDefaultToolkit(); 
		Dimension screenSize = toolkit.getScreenSize(); 
		int x = (screenSize.width - frame.getWidth()) / 2; 
		int y = (screenSize.height - frame.getHeight()) / 2;
		
		frame.setLocation(x, y);
		frame.setVisible(true);
	}

	/**
	 * Action related to the load/open action.
	 * 
	 * @author jsvazic
	 */
	private class LoadAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		
		private MainFrame frame;

		/**
		 * Default constructor.
		 * 
		 * @param frame The parent frame used for the file chooser dialog.
		 */
		public LoadAction(MainFrame frame) {
			super("Open Deck");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, 
				KeyEvent.CTRL_MASK));
			
			putValue(MNEMONIC_KEY, KeyEvent.VK_O);
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "Double Fanucci Deck (*.dfd)", "dfd");
			
			fc.setFileFilter(filter);
			int retVal = fc.showOpenDialog(frame);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				File inFile = fc.getSelectedFile();
				try {
					DeckController.importDeck(inFile);
					frame.resetSelection();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, 
							"Failed to load the deck:\n" + ex.getMessage(), 
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * Action related to the save action.
	 * 
	 * @author jsvazic
	 */
	private class SaveAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private JFrame frame;
		
		/**
		 * Default constructor.
		 * 
		 * @param frame The parent frame used for the file chooser dialog.
		 */
		public SaveAction(JFrame frame) {
			super("Save Deck");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, 
				KeyEvent.CTRL_MASK));
			
			putValue(MNEMONIC_KEY, KeyEvent.VK_S);
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "Double Fanucci Deck", "dfd");

			fc.setFileFilter(filter);
			int retVal = fc.showSaveDialog(frame);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				File outFile = fc.getSelectedFile();
				
				// Make sure we have the proper extension on the file.
				if (!outFile.getAbsolutePath().endsWith(".dfd")) {
					outFile = new File(outFile.getAbsolutePath() + ".dfd");
				}
				
				try {
					DeckController.exportDeck(Deck.getInstance(), outFile);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, 
							"Failed to save the deck:\n" + ex.getMessage(), 
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
	
	/**
	 * Action related to the exit action.
	 * 
	 * @author jsvazic
	 */
	private class ExitAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * Default constructor.
		 */
		public ExitAction() {
			super("Exit");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X, 
				KeyEvent.CTRL_MASK));
			
			putValue(MNEMONIC_KEY, KeyEvent.VK_X);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			shutdown();
		}
	}
	
	/**
	 * Action related to running the algorithm for the calculator.
	 * 
	 * @author jsvazic
	 */
	private class RunAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * Default constructor.
		 */
		public RunAction() {
			super("Run");
			putValue(ACCELERATOR_KEY, 
					KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
	
			putValue(MNEMONIC_KEY, KeyEvent.VK_R);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Runnable r = new Runnable() {
				@Override
				public void run() {
					long startTime = System.currentTimeMillis();
					FanucciCalc calc = new FanucciCalc(simOptions);
					Chromosome[] arr = calc.execute(Deck.getInstance());
					long endTime = System.currentTimeMillis();
					
					// Print out the best hands available for the given deck.
					for (Chromosome c : arr) {
						if (c == null) {
							break;
						}
						MainFrame.this.outputArea.append(c + "\n");
					}
					
					MainFrame.this.outputArea.append("\nTotal time: " + 
							(endTime - startTime) + "ms\n");
				}				
			};
			
			Thread t = new Thread(r);
			t.start();
			try {
				t.join();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Action related to the displaying the options panel.
	 * 
	 * @author jsvazic
	 */
	private class OptionsAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * Default constructor.
		 */
		public OptionsAction() {
			super("Options");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, 
				KeyEvent.CTRL_MASK));
			
			putValue(MNEMONIC_KEY, KeyEvent.VK_P);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			OptionsDialog dialog = new OptionsDialog(
					MainFrame.this, simOptions);
			
			// Center the dialog.
			Toolkit toolkit = Toolkit.getDefaultToolkit(); 
			Dimension screenSize = toolkit.getScreenSize(); 
			int x = (screenSize.width - dialog.getWidth()) / 2; 
			int y = (screenSize.height - dialog.getHeight()) / 2;
			
			dialog.setLocation(x, y);
			dialog.setVisible(true);
		}
	}
}