package com.arm.fanucci.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.NumberFormat;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.arm.fanucci.SimulatorOptions;

public class OptionsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private SimulatorOptions options;
	private JSlider popSlider;
	private JSlider elitismSlider;
	private JSlider mutationSlider;
	private JFormattedTextField iterationField;
	private JComboBox setComboBox;
	private JSpinner repeatSpinner;
	
	public OptionsPanel(SimulatorOptions options) {
		this.options = options;
		init();
	}
	
	private void init() {
		popSlider = new JSlider(0, 4096, options.getPopulationSize());
		popSlider.setMajorTickSpacing(1024);
		popSlider.setMinorTickSpacing(128);
		popSlider.setPaintTicks(true);
		popSlider.setPaintLabels(true);
		
		elitismSlider = new JSlider(0, 100, 
				(int) (options.getElitismRate() * 100));
		elitismSlider.setMajorTickSpacing(10);
		elitismSlider.setMinorTickSpacing(1);
		elitismSlider.setPaintTicks(true);
		elitismSlider.setPaintLabels(true);

		mutationSlider = new JSlider(0, 100, 
				(int) (options.getMutationRate() * 100));
		mutationSlider.setMajorTickSpacing(10);
		mutationSlider.setMinorTickSpacing(1);
		mutationSlider.setPaintTicks(true);
		mutationSlider.setPaintLabels(true);

		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMaximumFractionDigits(0);
		formatter.setMaximumIntegerDigits(4);
		iterationField = new JFormattedTextField(formatter);
		iterationField.setText(String.valueOf(options.getMaxIterations()));
		
		setComboBox = new JComboBox(new String[] { "2", "3", "4", "5", "6" });
		repeatSpinner = new JSpinner(new SpinnerNumberModel(
				options.getMaxRepeatCount(), 0, 500, 1));
		
		setLayout(new GridBagLayout());
		add(new JLabel("Population Size"), new GridBagConstraints(0, 0, 1, 1,
				0.5, 0.5, GridBagConstraints.SOUTH, 
				GridBagConstraints.NONE, new Insets(5, 5, 1, 5), 1, 1));
		add(popSlider, new GridBagConstraints(0, 1, 1, 1,
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
		add(panel, new GridBagConstraints(1, 0, 1, 2,
				0.5, 0.5, GridBagConstraints.NORTH, 
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 1, 1));

		add(new JLabel("Elitism Rate"), new GridBagConstraints(0, 2, 1, 1,
				0.5, 0.5, GridBagConstraints.CENTER, 
				GridBagConstraints.NONE, new Insets(5, 5, 1, 5), 1, 1));
		add(elitismSlider, new GridBagConstraints(0, 3, 1, 1,
				0.5, 0.5, GridBagConstraints.NORTH, 
				GridBagConstraints.HORIZONTAL, new Insets(1, 5, 5, 5), 1, 1));

		add(new JLabel("Mutation Rate"), new GridBagConstraints(1, 2, 1, 1,
				0.5, 0.5, GridBagConstraints.CENTER, 
				GridBagConstraints.NONE, new Insets(5, 5, 1, 5), 1, 1));
		add(mutationSlider, new GridBagConstraints(1, 3, 1, 1,
				0.5, 0.5, GridBagConstraints.NORTH, 
				GridBagConstraints.HORIZONTAL, new Insets(1, 5, 5, 5), 1, 1));
	}
}