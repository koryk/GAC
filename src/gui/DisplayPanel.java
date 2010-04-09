package gui;


import java.awt.BorderLayout;

import ga.Problem;

import javax.swing.JPanel;

public class DisplayPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DriverFrame df;
	public DisplayPanel(DriverFrame frame) {
		super();
		setLayout(new BorderLayout());
		df = frame;
	}
	public void compareProblems(Problem a, Problem b){
		
	}

}
