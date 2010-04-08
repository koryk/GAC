package gui;

import java.awt.Dimension;

import ga.Problem;

import javax.swing.JPanel;

public abstract class ProblemPanel extends JPanel{
	protected Problem problem;
	public ProblemPanel (Problem problem){
		super();
		this.problem = problem;
		setSize(new Dimension(400,500));
	}
	public abstract void loadPanel();
	public abstract void runGA();
}
