package gui;

import ga.Problem;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

import binpack.BinPackProblem;

public class DriverFrame extends JFrame {
	private final Dimension SIZE = new Dimension(800,600);
	private ControlPanel cp;
	private DisplayPanel dp;
	public DriverFrame() {
		super();
		cp = new ControlPanel(this);
		dp = new DisplayPanel(this);

		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(SIZE);
		Container cpanel = this.getContentPane();
		cpanel.setLayout(new BorderLayout());
		cpanel.add(cp, BorderLayout.SOUTH);
		cpanel.add(dp, BorderLayout.CENTER);
		loadProblem(new BinPackProblem(3,100));
	}
	public void loadProblem(Problem problem){
		dp.add(problem.getProblemPanel());
		problem.getProblemPanel().runGA();
	}
	public static void main (String[] args){
		new DriverFrame();
	}
}
