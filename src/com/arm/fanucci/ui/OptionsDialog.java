package com.arm.fanucci.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	private JFormattedTextField iterationField;
	private JComboBox setComboBox;
	private JSpinner repeatSpinner;

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
				shutdown();
			}
		});
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBorder(BorderFactory.createTitledBorder(
				"Simulator Options"));
		
		optionsPanel.setLayout(new GridBagLayout());

		popLabel  = new JLabel("Population Size - " + 
				options.getPopulationSize());
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

		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMaximumFractionDigits(0);
		formatter.setMaximumIntegerDigits(4);
		iterationField = new JFormattedTextField(formatter);
		iterationField.setText(String.valueOf(options.getMaxIterations()));
		
		setComboBox = new JComboBox(new String[] { "2", "3", "4", "5", "6" });
		repeatSpinner = new JSpinner(new SpinnerNumberModel(
				options.getMaxRepeatCount(), 0, 500, 1));
		
		optionsPanel.add(popLabel, new GridBagConstraints(0, 0, 1, 1,
				0.5, 0.5, GridBagConstraints.SOUTH, 
				GridBagConstraints.NONE, new Insets(5, 5, 1, 5), 1, 1));
		optionsPanel.add(popSlider, new GridBagConstraints(0, 1, 1, 1,
				0.5, 0.5, GridBagConstraints.NORTH, 
				GridBagConstraints.HORIZONTAL, new Insets(1, 5, 5, 5), 1, 1));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2, 5, 5));
		panel.add(new JLabel("Number of Iterations"));
		panel.add(iterationField);
		panel.add(new JLabel("Number of Sets"));
		panel.add(setComboBox);
		panel.add(new JLabel("Local Maxima Count"));
		panel.add(repeatSpinner);
		optionsPanel.add(panel, new GridBagConstraints(1, 0, 1, 2, 0.5, 0.5, 
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 
				new Insets(5, 5, 5, 5), 1, 1));

		optionsPanel.add(elitismLabel, new GridBagConstraints(0, 2, 1, 1,
				0.5, 0.5, GridBagConstraints.CENTER, 
				GridBagConstraints.NONE, new Insets(5, 5, 1, 5), 1, 1));
		optionsPanel.add(elitismSlider, new GridBagConstraints(0, 3, 1, 1,
				0.5, 0.5, GridBagConstraints.NORTH, 
				GridBagConstraints.HORIZONTAL, new Insets(1, 5, 5, 5), 1, 1));

		optionsPanel.add(mutationLabel, new GridBagConstraints(1, 2, 1, 1,
				0.5, 0.5, GridBagConstraints.CENTER, 
				GridBagConstraints.NONE, new Insets(5, 5, 1, 5), 1, 1));
		optionsPanel.add(mutationSlider, new GridBagConstraints(1, 3, 1, 1,
				0.5, 0.5, GridBagConstraints.NORTH, 
				GridBagConstraints.HORIZONTAL, new Insets(1, 5, 5, 5), 1, 1));
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(10, 10));
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		contentPane.add(optionsPanel, BorderLayout.CENTER);
		
		setContentPane(contentPane);
		pack();
	}
	
	/**
	 * Method called when the OK button is pressed.
	 */
	private void okPressed() {
		// Update the options object
		options.setElitismRate(elitismSlider.getValue() / 100.0f);
		try {
			options.setMaxHands(Integer.valueOf(
					(String) setComboBox.getSelectedItem()));
		} catch (NumberFormatException ex) {
			// Safe to ignore.
		}
		try {
			options.setMaxIterations(Integer.valueOf(
					iterationField.getText()));
		} catch (NumberFormatException ex) {
			// Safe to ignore.
		}
		try {
			options.setMaxRepeatCount((Integer) repeatSpinner.getValue());
		} catch (NumberFormatException ex) {
			// Safe to ignore.
		}
		options.setMutationRate(mutationSlider.getValue() / 100.0f);
		options.setPopulationSize(popSlider.getValue());
		shutdown();
	}
	
	/**
	 * Method called when the Cancel button is pressed.
	 */
	private void cancelPressed() {
		shutdown();
	}
	
	/**
	 * Method called when the dialog is closed.
	 */
	private void shutdown() {
		setVisible(false);
		dispose();
	}
}