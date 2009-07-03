package com.arm.fanucci.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.arm.fanucci.Chromosome;
import com.arm.fanucci.Deck;
import com.arm.fanucci.DeckController;
import com.arm.fanucci.FanucciCalc;
import com.arm.fanucci.OptionsController;
import com.arm.fanucci.SimulatorOptions;

/**
 * Main class for the UI.
 * 
 * @author jsvazic
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private CardPanel cardPanel;
	private SimulatorOptions simOptions;
	private JPanel contentPane;
	private String lastFileLocation;
	private File lastFile;
	private SolutionPanel solutionPanel;
	private JLabel statusBar;
	private boolean macOS;

	private static final String UI_CONFIG_FILE = "yadfc.dat";
	private static final String TITLE_PREFIX = 
			"Yet Another Double Fanucci Calculator(tm)";

	/**
	 * Default constructor.
	 */
	public MainFrame() {
		super(TITLE_PREFIX);
		this.macOS = (System.getProperty("mrj.version") != null);
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

		fileMenu.add(new JMenuItem(new LoadAction()));
		fileMenu.add(new JMenuItem(new SaveAction()));
		fileMenu.add(new JMenuItem(new SaveAsAction()));
		if (!macOS) {
			fileMenu.addSeparator();
			fileMenu.add(new JMenuItem(new ExitAction()));
		}

		simMenu.add(new JMenuItem(new RunAction()));
		simMenu.addSeparator();
		simMenu.add(new JMenuItem(new OptionsAction()));

		menuBar.add(fileMenu);
		menuBar.add(simMenu);

		setJMenuBar(menuBar);

		cardPanel = new CardPanel(this);
		solutionPanel = new SolutionPanel();

		statusBar = new JLabel(" ");
		statusBar.setHorizontalAlignment(SwingConstants.RIGHT);
		statusBar.setBorder(BorderFactory.createEtchedBorder());

		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(1, 1));
		contentPane.add(cardPanel, BorderLayout.NORTH);
		contentPane.add(new JScrollPane(solutionPanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
				BorderLayout.CENTER);

		contentPane.add(statusBar, BorderLayout.SOUTH);

		setContentPane(contentPane);
		setSize(585, 630);

		// Center the frame.
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int x = (screenSize.width - getWidth()) / 2;
		int y = (screenSize.height - getHeight()) / 2;
		setLocation(x, y);

		// Try to load the settings
		try {
			loadDetails();
		} catch (IOException ex) {
			// Failed to load the frame, no problem, stick with
			// the defaults.
		}

		addListeners();
	}

	/**
	 * Helper method used to add any associated listeners to the frame.
	 */
	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				shutdown();
			}
		});
	}

	/**
	 * Method to retrieve the underlying <code>CardPanel</code> instance.
	 * 
	 * @return The associated <code>CardPanel</code> instance.
	 */
	public CardPanel getCardPanel() {
		return cardPanel;
	}

	/**
	 * Method used to terminate the application.
	 */
	private void shutdown() {
		try {
			OptionsController.saveOptions(simOptions);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Failed to save the options: "
					+ ex.getMessage(), "Error Saving Options",
					JOptionPane.ERROR_MESSAGE);
		}

		try {
			saveDetails();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this,
					"Failed to save the UI settings: " + ex.getMessage(),
					"Error Saving UI Settings", JOptionPane.ERROR_MESSAGE);
		}

		dispose();
		System.exit(0);
	}

	/**
	 * Main method for executing the UI.
	 * 
	 * @param args
	 *            Command line arguments (ignored).
	 * 
	 * @throws Exception
	 *             Thrown if there was an error bringing up the display.
	 */
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFrame frame = new MainFrame();
		frame.setVisible(true);
	}

	/**
	 * Action related to the load/open action.
	 * 
	 * @author jsvazic
	 */
	private class LoadAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * Default constructor.
		 * 
		 * @param frame
		 *            The parent frame used for the file chooser dialog.
		 */
		public LoadAction() {
			super("Open Deck...");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O,
					macOS ? KeyEvent.META_MASK : KeyEvent.CTRL_MASK));

			putValue(MNEMONIC_KEY, KeyEvent.VK_O);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc;
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Double Fanucci Deck (*.dfd)", "dfd");

			if (lastFileLocation != null) {
				fc = new JFileChooser(lastFileLocation);
			} else {
				fc = new JFileChooser(System.getProperty("user.dir"));
			}

			fc.setFileFilter(filter);
			int retVal = fc.showOpenDialog(MainFrame.this);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				File inFile = fc.getSelectedFile();
				lastFileLocation = inFile.getParent();
				try {
					DeckController.importDeck(inFile, simOptions);
					lastFile = inFile;
					solutionPanel.resetPanels();
					CardPanel cardPanel = MainFrame.this.getCardPanel();
					int selectedIdx = cardPanel.getSelectedIndex();
					cardPanel.setSelection(-1);
					cardPanel.setSelection(selectedIdx);
					
					// Get the name of the file, minus the ".dfd" extension
					String name = lastFile.getName().substring(0, 
							lastFile.getName().length() - 4);
					
					MainFrame.this.setTitle(TITLE_PREFIX + " - " + name);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(MainFrame.this,
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

		/**
		 * Default constructor.
		 * 
		 * @param frame
		 *            The parent frame used for the file chooser dialog.
		 * 
		 */
		public SaveAction() {
			super("Save Deck");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,
					macOS ? KeyEvent.META_MASK : KeyEvent.CTRL_MASK));
			putValue(MNEMONIC_KEY, KeyEvent.VK_S);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (lastFile == null) {
				new SaveAsAction().actionPerformed(e);
			} else {
				try {
					DeckController.exportDeck(Deck.getInstance(), simOptions,
							lastFile);
					
					String title = MainFrame.this.getTitle();
					if (title.startsWith("*")) {
						MainFrame.this.setTitle(title.substring(1));
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(MainFrame.this,
							"Failed to save the deck:\n" + ex.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * Action related to the save-as action.
	 * 
	 * @author jsvazic
	 */
	private class SaveAsAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * Default constructor.
		 * 
		 * @param frame
		 *            The parent frame used for the file chooser dialog.
		 */
		public SaveAsAction() {
			super("Save Deck As...");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc;
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Double Fanucci Deck", "dfd");

			if (lastFileLocation != null) {
				fc = new JFileChooser(lastFileLocation);
			} else {
				fc = new JFileChooser(System.getProperty("user.dir"));
			}

			fc.setFileFilter(filter);
			int retVal = fc.showSaveDialog(MainFrame.this);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				File outFile = fc.getSelectedFile();
				
				if (outFile.exists()) {
					retVal = JOptionPane.showConfirmDialog(MainFrame.this, 
							"File already exists!\nAre you sure you want " +
							"to overwrite?", "Confirm Overwrite", 
							JOptionPane.YES_NO_CANCEL_OPTION);
					
					if (retVal != JOptionPane.YES_OPTION) {
						return;
					}
				}
				
				lastFileLocation = outFile.getParent();

				// Make sure we have the proper extension on the file.
				if (!outFile.getAbsolutePath().endsWith(".dfd")) {
					outFile = new File(outFile.getAbsolutePath() + ".dfd");
				}

				try {
					DeckController.exportDeck(Deck.getInstance(), simOptions,
							outFile);
					
					lastFile = outFile;
					
					// Get the name of the file, minus the ".dfd" extension
					String name = lastFile.getName().substring(0, 
							lastFile.getName().length() - 4);
					
					MainFrame.this.setTitle(TITLE_PREFIX + " - " + name);

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(MainFrame.this,
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
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));

			putValue(MNEMONIC_KEY, KeyEvent.VK_R);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Runnable r = new Runnable() {
				@Override
				public void run() {
					long sTime = System.currentTimeMillis();
					FanucciCalc calc = new FanucciCalc(simOptions);
					Chromosome[] arr = calc.execute(Deck.getInstance()
							.getCardSet());
					long eTime = System.currentTimeMillis();

					solutionPanel.resetPanels();
					int[] slotOrder = simOptions.getSlotOrder();
					// Print out the best hands available for the given deck.
					for (int i = 0; i < arr.length; i++) {
						solutionPanel.updatePanel(slotOrder[i], arr[i]);
					}

					statusBar.setText("Total time: " + (eTime - sTime) + "ms");
				}
			};

			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			Thread t = new Thread(r);
			t.start();
			try {
				t.join();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			setCursor(Cursor.getDefaultCursor());
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
					macOS ? KeyEvent.META_MASK : KeyEvent.CTRL_MASK));

			putValue(MNEMONIC_KEY, KeyEvent.VK_P);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			OptionsDialog dialog = new OptionsDialog(MainFrame.this,
					simOptions);

			// Center the dialog.
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Dimension screenSize = toolkit.getScreenSize();
			int x = (screenSize.width - dialog.getWidth()) / 2;
			int y = (screenSize.height - dialog.getHeight()) / 2;

			dialog.setLocation(x, y);
			dialog.setVisible(true);
		}
	}

	private void saveDetails() throws IOException {
		Properties props = new Properties();
		props.setProperty("State", String.valueOf(getExtendedState()));
		props.setProperty("X", String.valueOf(getX()));
		props.setProperty("Y", String.valueOf(getY()));
		props.setProperty("W", String.valueOf(getWidth()));
		props.setProperty("H", String.valueOf(getHeight()));
		props.setProperty("SFL", (lastFileLocation != null) ? lastFileLocation
				: System.getProperty("user.dir"));

		props.storeToXML(new FileOutputStream(UI_CONFIG_FILE), null);
	}

	private void loadDetails() throws IOException {
		File file = new File(UI_CONFIG_FILE);
		if (!file.exists() || !file.canRead()) {
			return;
		}
		Properties props = new Properties();
		props.loadFromXML(new FileInputStream(UI_CONFIG_FILE));
		int extendedState = Integer.parseInt(props.getProperty("State",
				String.valueOf(getExtendedState())));

		lastFileLocation = props.getProperty("SFL", System
				.getProperty("user.dir"));

		contentPane.repaint();

		if (extendedState != JFrame.MAXIMIZED_BOTH) {
			setBounds(Integer.parseInt(props.getProperty("X", String
					.valueOf(getX()))), Integer.parseInt(props.getProperty("Y",
					String.valueOf(getY()))), Integer.parseInt(props
					.getProperty("W", String.valueOf(getWidth()))), Integer
					.parseInt(props.getProperty("H", String
							.valueOf(getHeight()))));
		} else {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
	}
}