package gui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
	JComboBox problemChoose, compareProblemChoose, algorithmChoose, compareAlgorithmChoose;
	JCheckBox compare;
	JButton run, help;
	public ControlPanel(){
		super();
		problemChoose = new JComboBox();
		compareProblemChoose = new JComboBox();
		algorithmChoose = new JComboBox();
		compare = new JCheckBox();
		compareAlgorithmChoose = new JComboBox();
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
