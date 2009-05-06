package com.arm.fanucci.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.arm.fanucci.SimulatorOptions;

public class OptionsDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private OptionsPanel optionsPanel;
	private JPanel buttonPanel;
	private int dialogResult;

	/**
	 * Default constructor.
	 * 
	 * @param options
	 */
	public OptionsDialog(JFrame frame, SimulatorOptions options) {
		super(frame, "YADFC Options", true);
		this.optionsPanel = new OptionsPanel(options);
		init();
	}
	
	/**
	 * Get the result of the dialog action.
	 * 
	 * @return One of <code>JOptionPane.OK_OPTION</code>,
	 * <code>JOptionPane.CANCEL_OPTION</code>, or 
	 * <code>JOptionPane.CLOSED_OPTION</code>.  
	 */
	public int getDialogResult() {
		return dialogResult;
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
				dialogResult = JOptionPane.CLOSED_OPTION;
				shutdown();
			}
		});
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		contentPane.add(optionsPanel, BorderLayout.CENTER);
		
		setContentPane(contentPane);
		pack();
	}
	
	/**
	 * Method called when the OK button is pressed.
	 */
	private void okPressed() {
		dialogResult = JOptionPane.OK_OPTION;
		shutdown();
	}
	
	/**
	 * Method called when the Cancel button is pressed.
	 */
	private void cancelPressed() {
		dialogResult = JOptionPane.CANCEL_OPTION;
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