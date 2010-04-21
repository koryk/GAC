package gui;

import ga.Problem;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

import binpack.BinPackProblem;
import binpack.gwa.WaveletBinProblemPanel;

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
		int hardness = 100;
		while (true){try{
			BinPackProblem prob = new BinPackProblem(2,50 + (int)(Math.random()*hardness),BinPackProblem.SGA, hardness), probtwo = new BinPackProblem(prob.getBins(), prob.getItems(), prob.getWeights());
			probtwo.setImplementation(BinPackProblem.GWA);
			loadProblems(prob, probtwo);
			
			//((WaveletBinProblemPanel)probtwo.getProblemPanel()).getSolution()
			}catch(Exception e){e.printStackTrace();};
		}
	}
	public void loadProblem(Problem problem){
		dp.add(problem.getProblemPanel());
		problem.getProblemPanel().runGA();
	}
	public void loadProblems(Problem problemOne, Problem problemTwo){
		dp.removeAll();
		if (!problemOne.equals(problemTwo)){
			dp.add(problemOne.getProblemPanel(),BorderLayout.EAST);
			problemOne.getProblemPanel().runGA();
			dp.add(problemTwo.getProblemPanel(),BorderLayout.WEST);
			problemTwo.getProblemPanel().runGA();
		}
	}
	public static void main (String[] args){
		new DriverFrame();
	}
}
