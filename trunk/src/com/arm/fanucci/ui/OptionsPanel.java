package com.arm.fanucci.ui;

import javax.swing.JPanel;

import com.arm.fanucci.SimulatorOptions;

public class OptionsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private SimulatorOptions options;
	
	public OptionsPanel(SimulatorOptions options) {
		this.options = options;
	}
}
