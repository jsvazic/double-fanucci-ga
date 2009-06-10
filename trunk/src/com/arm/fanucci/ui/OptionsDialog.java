package com.arm.fanucci.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.arm.fanucci.SimulatorOptions;

public class OptionsDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private SimulatorOptions options;
	private JPanel buttonPanel;

	private JLabel popLabel;
	private JSlider popSlider;
	private JLabel elitismLabel;
	private JSlider elitismSlider;
	private JLabel mutationLabel;
	private JSlider mutationSlider;
	private JTextField iterationField;
	private JComboBox setComboBox;
	private JSpinner repeatSpinner;
	private JLabel slotLabel;
	private JList slotOrder;
	private JButton upButton;
	private JButton downButton;

	/**
	 * Default constructor.
	 * 
	 * @param options
	 */
	public OptionsDialog(JFrame frame, SimulatorOptions options) {
		super(frame, "Yet Another Double Fanucci Calculator - Options", true);
		this.options = options;
		init();
	}

	/**
	 * Helper to initialize the UI.
	 */
	private void init() {
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okPressed();
			}
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelPressed();
			}
		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeDialog();
			}
		});

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		JPanel optionsPanel = new JPanel();
		optionsPanel.setBorder(BorderFactory
				.createTitledBorder("Simulator Options"));

		optionsPanel.setLayout(new GridBagLayout());

		popLabel = new JLabel("Population Size - "
				+ options.getPopulationSize());
		popSlider = new JSlider(0, 4096, options.getPopulationSize());
		popSlider.setMajorTickSpacing(1024);
		popSlider.setMinorTickSpacing(128);
		popSlider.setPaintTicks(true);
		popSlider.setPaintLabels(true);
		popSlider.setSnapToTicks(true);
		popSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int count = (int) source.getValue();
					popLabel.setText("Population Size - " + count);
				}
			}
		});

		int elitismRate = (int) (options.getElitismRate() * 100);
		elitismLabel = new JLabel("Elitism Rate - " + elitismRate + "%");
		elitismSlider = new JSlider(0, 100, elitismRate);
		elitismSlider.setMajorTickSpacing(10);
		elitismSlider.setMinorTickSpacing(1);
		elitismSlider.setPaintTicks(true);
		elitismSlider.setPaintLabels(true);
		elitismSlider.setSnapToTicks(true);
		elitismSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int rate = (int) source.getValue();
					elitismLabel.setText("Elitism Rate - " + rate + "%");
				}
			}
		});

		int mutationRate = (int) (options.getMutationRate() * 100);
		mutationLabel = new JLabel("Mutation Rate - " + mutationRate + "%");
		mutationSlider = new JSlider(0, 100, mutationRate);
		mutationSlider.setMajorTickSpacing(10);
		mutationSlider.setMinorTickSpacing(1);
		mutationSlider.setPaintTicks(true);
		mutationSlider.setPaintLabels(true);
		mutationSlider.setSnapToTicks(true);
		mutationSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int rate = (int) source.getValue();
					mutationLabel.setText("Mutation Rate - " + rate + "%");
				}
			}
		});

		iterationField = new JTextField();
		iterationField.setDocument(new IntegerDocument(5));
		iterationField.setText(String.valueOf(options.getMaxIterations()));

		setComboBox = new JComboBox(new String[] { "1", "2", "3", "4", "5" });
		setComboBox.setSelectedItem(String.valueOf(options.getMaxSlots()));

		repeatSpinner = new JSpinner(new SpinnerNumberModel(options
				.getMaxRepeatCount(), 0, 500, 1));
	
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2, 5, 5));
		panel.add(new JLabel("Number of Iterations"));
		panel.add(iterationField);
		panel.add(new JLabel("Number of Sets"));
		panel.add(setComboBox);
		panel.add(new JLabel("Local Maxima Count"));
		panel.add(repeatSpinner);
		
		slotLabel = new JLabel("Slot Solution Order");
		slotOrder = new JList(new SlotModel(options.getSlotOrder()));
		upButton = new JButton("Up");
		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upPressed();
			}
		});
		
		downButton = new JButton("Down");
		downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				downPressed();
			}
		});
		
		optionsPanel.add(popLabel, new GridBagConstraints(0, 0, 1, 1, 0.5, 0.5,
				GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(
						5, 5, 1, 5), 1, 1));
		optionsPanel.add(popSlider, new GridBagConstraints(0, 1, 1, 1, 0.5,
				0.5, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
				new Insets(1, 5, 5, 5), 1, 1));

		optionsPanel.add(elitismLabel, new GridBagConstraints(0, 2, 1, 1, 0.5,
				0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(5, 5, 1, 5), 1, 1));
		optionsPanel.add(elitismSlider, new GridBagConstraints(0, 3, 1, 1, 0.5,
				0.5, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
				new Insets(1, 5, 5, 5), 1, 1));

		optionsPanel.add(mutationLabel, new GridBagConstraints(0, 4, 1, 1, 0.5,
				0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(5, 5, 1, 5), 1, 1));
		optionsPanel.add(mutationSlider, new GridBagConstraints(0, 5, 1, 1,
				0.5, 0.5, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(1, 5, 5, 5), 1, 1));
				
		optionsPanel.add(panel, new GridBagConstraints(1, 0, 2, 2, 0.5, 0.5,
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 1, 1));
		
		optionsPanel.add(slotLabel, new GridBagConstraints(1, 2, 1, 1, 0.5, 0.5,
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(5, 5, 1, 5), 1, 1));

		optionsPanel.add(new JScrollPane(slotOrder, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), 
				new GridBagConstraints(1, 3, 1, 3, 0.5, 0.5,
				GridBagConstraints.WEST, GridBagConstraints.BOTH,
				new Insets(1, 5, 5, 5), 1, 1));

		optionsPanel.add(upButton, new GridBagConstraints(2, 3, 1, 1, 0.5, 0.5,
				GridBagConstraints.SOUTH, GridBagConstraints.NONE,
				new Insets(1, 1, 1, 1), 1, 1));
		optionsPanel.add(downButton, new GridBagConstraints(2, 4, 1, 1, 0.5, 0.5,
				GridBagConstraints.NORTH, GridBagConstraints.NONE,
				new Insets(1, 1, 1, 1), 1, 1));
		
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(10, 10));
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		contentPane.add(optionsPanel, BorderLayout.CENTER);

		setContentPane(contentPane);
		pack();
	}

	private void upPressed() {
		int idx = slotOrder.getSelectedIndex();
		if (idx <= 0) {
			return;
		}
		
		SlotModel model = (SlotModel) slotOrder.getModel();
		model.swap(idx, idx - 1);
		slotOrder.setSelectedIndex(idx - 1);
	}

	private void downPressed() {
		int idx = slotOrder.getSelectedIndex();
		if (idx < 0 || idx == slotOrder.getModel().getSize() - 1) {
			return;
		}
		
		SlotModel model = (SlotModel) slotOrder.getModel();
		model.swap(idx, idx + 1);
		slotOrder.setSelectedIndex(idx + 1);
	}

	/**
	 * Method called when the OK button is pressed.
	 */
	private void okPressed() {
		// Update the options object
		options.setElitismRate(elitismSlider.getValue() / 100.0f);
		try {
			options.setMaxSlots(Integer.valueOf((String) setComboBox
					.getSelectedItem()));
		} catch (NumberFormatException ex) {
			// Safe to ignore.
		}
		try {
			options.setMaxIterations(Integer.valueOf(iterationField.getText()));
		} catch (NumberFormatException ex) {
			options.setMaxIterations(0);
		}
		try {
			options.setMaxRepeatCount((Integer) repeatSpinner.getValue());
		} catch (NumberFormatException ex) {
			// Safe to ignore.
		}
		options.setMutationRate(mutationSlider.getValue() / 100.0f);
		options.setPopulationSize(popSlider.getValue());
		
		SlotModel model = (SlotModel) slotOrder.getModel();
		options.setSlotOrder(model.getSlotOrder());
		
		closeDialog();
	}

	/**
	 * Method called when the Cancel button is pressed.
	 */
	private void cancelPressed() {
		closeDialog();
	}

	/**
	 * Method called when the dialog is closed.
	 */
	private void closeDialog() {
		setVisible(false);
		dispose();
	}
	
	private class SlotModel extends DefaultListModel {
		private static final long serialVersionUID = 1L;
		
		private static final String GAMBIT = "Fanucci Gambit";
		private static final String MIND = "Mind";
		private static final String BODY = "Body";
		private static final String SPIRIT = "Spirit";
		private static final String SIDEKICK = "Sidekick";

		public SlotModel(int[] slotOrder) {
			for (int slotId : slotOrder) {
				addElement(getSlotName(slotId));
			}
		}
		
		public void swap(int idx1, int idx2) {
			Object obj = elementAt(idx2);
			
			setElementAt(elementAt(idx1), idx2);
			setElementAt(obj, idx1);
		}
		
		public int[] getSlotOrder() {
			int[] arr = new int[getSize()];
			for (int i = 0; i < getSize(); i++) {
				String str = (String) elementAt(i);
				if (GAMBIT.equals(str)) {
					arr[i] = SolutionPanel.GAMBIT;
				} else if (MIND.equals(str)) {
					arr[i] = SolutionPanel.MIND;
				} else if (BODY.equals(str)) {
					arr[i] = SolutionPanel.BODY;
				} else if (SPIRIT.equals(str)) {
					arr[i] = SolutionPanel.SPIRIT;
				} else if (SIDEKICK.equals(str)) {
					arr[i] = SolutionPanel.SIDEKICK;
				} else {
					arr[i] = -1;
				}
			}
			
			return arr;
		}
		
		private String getSlotName(int slotId) {
			switch (slotId) {
				case SolutionPanel.GAMBIT:
					return GAMBIT;
				case SolutionPanel.MIND:
					return MIND;
				case SolutionPanel.BODY:
					return BODY;
				case SolutionPanel.SPIRIT:
					return SPIRIT;
				case SolutionPanel.SIDEKICK:
					return SIDEKICK;
				default:
					return "Unknown Slot";				
			}
		}
	}

	/**
	 * Private class used for field validation.
	 * 
	 * @author jsvazic
	 */
	private class IntegerDocument extends PlainDocument {		
		private static final long serialVersionUID = 1L;
		private int maxLength;
		
		public IntegerDocument(int maxLength) {
			this.maxLength = maxLength;
		}
		
		public void insertString(int offset, String str, AttributeSet attrs) 
				throws BadLocationException {

			if (str == null) {
				return;
			} else {
				String newValue;
				int length = getLength();
				
				if (length == 0) {
					newValue = str;
				} else {
					String currentContent = getText(0, length);
					StringBuffer currentBuffer = new StringBuffer(
							currentContent);
					currentBuffer.insert(offset, str);
					newValue = currentBuffer.toString();
				}
				
				if (newValue.length() > maxLength) {
					newValue = newValue.substring(0, maxLength);
				}

				try {
					Integer.parseInt(newValue);
					super.insertString(offset, str, attrs);
				} catch (NumberFormatException exception) {
					Toolkit.getDefaultToolkit().beep();
				}
			}
		}
	}
}