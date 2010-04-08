package gui;

import ga.Problem;

import javax.swing.JPanel;

public abstract class ProblemPanel extends JPanel{
	protected Problem problem;
	public ProblemPanel (Problem problem){
		this.problem = problem;
	}
	public abstract void loadPanel();
	public abstract void runGA();
}
