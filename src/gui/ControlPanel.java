package gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
	JComboBox problemChoose, compareProblemChoose, algorithmChoose, compareAlgorithmChoose;
	JCheckBox compare;
	JButton run, help;
	private DriverFrame df;
	private final Dimension SIZE = new Dimension(800,100);
	public ControlPanel(DriverFrame frame){
		super();
		df = frame;
		setSize(SIZE);
		setLayout(new GridLayout(9,1));
		problemChoose = new JComboBox();
		compareProblemChoose = new JComboBox();
		algorithmChoose = new JComboBox();
		compareAlgorithmChoose = new JComboBox();		
		compare = new JCheckBox();
		problemChoose.addItem("Bin Packing");
		compareProblemChoose.addItem("Bin Packing");
		algorithmChoose.addItem("sGA");
		algorithmChoose.addItem("GWA");
		compareAlgorithmChoose.addItem("sGA");
		compareAlgorithmChoose.addItem("GWA");		
		run = new JButton("Run");
		help = new JButton("Help");
		add(new JLabel("Run problem "));
		add(problemChoose);
		add(new JLabel(" using the algorithm "));
		add(algorithmChoose);
		add(compare);
		add(new JLabel(" compare with "));
		add(compareProblemChoose);
		add(new JLabel(" using the algorithm "));
		add(compareAlgorithmChoose);
	}
}
