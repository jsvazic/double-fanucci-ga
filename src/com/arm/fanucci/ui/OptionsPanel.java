package com.arm.fanucci.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.arm.fanucci.SimulatorOptions;

public class OptionsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private SimulatorOptions options;
	private JSlider popSlider;
	private JSlider elitismSlider;
	private JSlider mutationSlider;
	private JTextField iterationField;
	private JComboBox handComboBox;
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

		iterationField = new JTextField(String.valueOf(
				options.getMaxIterations()));
		
		handComboBox = new JComboBox(new String[] { "2", "3", "4", "5", "6" });
		
		repeatSpinner = new JSpinner(new SpinnerNumberModel(
				options.getMaxRepeatCount(), 0, 500, 1));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2, 2, 5, 5));
		topPanel.add(popSlider);
		topPanel.add(iterationField);
		topPanel.add(elitismSlider);
		topPanel.add(mutationSlider);
		
		topPanel.setBorder(BorderFactory.createTitledBorder(
				"Simulation Settings"));
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2, 5, 5));
		bottomPanel.add(handComboBox);
		bottomPanel.add(repeatSpinner);
		
		setLayout(new BorderLayout(5, 5));
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);
	}
}