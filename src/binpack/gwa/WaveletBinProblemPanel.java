package binpack.gwa;

import java.awt.Graphics;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import binpack.BinPackProblem;
import binpack.sga.SimpleBinChromosome;
import binpack.sga.SimpleBinFitnessFunction;
import binpack.sga.SimpleBinPopulation;

import com.syncleus.dann.genetics.AbstractValueGene;
import com.syncleus.dann.genetics.GeneticAlgorithmChromosome;
import com.syncleus.dann.genetics.wavelets.AbstractOrganism;

import ga.Problem;
import gui.ProblemPanel;

public class WaveletBinProblemPanel extends ProblemPanel{

	public WaveletBinProblemPanel(Problem problem){
		super(problem);
	}
	@Override
	public void loadPanel() {
		// TODO Auto-generated method stub	
	}
	public void paintComponent(Graphics g){
		((BinPackProblem)problem).paint(g);
			
	}
	@Override
	public void runGA() {
		// TODO Auto-generated method stub
		System.out.println(problem);
		final WaveletBinPopulation population = new WaveletBinPopulation(10, .4, .4, (BinPackProblem)problem);
		AbstractOrganism currentWinner;
		population.initializePopulation(20);
		//try{Thread.sleep(10000);}catch(Exception e){;}
		int i=0, max = 50;
		double prevFull = 0;
		int convergeGeneration =0;
		WaveletBinFitnessFunction winner, oldWinner = null;
		try
		{
			for(; i < max; i++){
				population.nextGeneration();
				//System.out.println(population.getGenerations());
				currentWinner = population.getWinner();
				winner = (WaveletBinFitnessFunction)population.packageChromosome(currentWinner);
				winner.process();
				//System.out.println(winner);
				System.out.print(" " + population.getGenerations() + " ");
				if (prevFull < winner.percentFull){
					
					//System.out.println(winner);					
					oldWinner = winner;
					convergeGeneration = population.getGenerations();
					prevFull = winner.percentFull;
					//System.out.println("Wooooo !!" + (prevFull = winner.percentFull) + " " + population.getGenerations() + " generation.");				
					if (prevFull == 1)
						break;
				}
			}//this.currentWinner = this.futureWinner.get();
			System.out.println("ga over");
			((BinPackProblem)problem).setSolution(oldWinner.getSolution());
			this.repaint();
			FileOutputStream os = new FileOutputStream(new File("gwa.txt"),true);
			os.write((problem.toString() + ": " + prevFull + " at " + convergeGeneration + " : ").getBytes());
			for (int j = 0 ; j < oldWinner.getSolution().length; j++){
				os.write(("\n[" + j + "]:").getBytes());
				int tmp = 0;
				for (Double d : oldWinner.getSolution()[j]){
					os.write((d + "-" + ((BinPackProblem)problem).getItems()[d.intValue()] + "*" + ((BinPackProblem)problem).getWeights()[d.intValue()] + "=" + ((BinPackProblem)problem).getItems()[d.intValue()]*((BinPackProblem)problem).getWeights()[d.intValue()]).getBytes());
					tmp += d;
				}
				os.write(("(" + tmp + "/"+ ((BinPackProblem)problem).getBins()[0]+")").getBytes());
			}
			os.write("\n\n".getBytes());
			os.close();
			System.out.println(problem.toString() + ": " + prevFull + " at " + convergeGeneration);
		}		
		catch(Exception caught)
		{
			caught.printStackTrace();				
			throw new RuntimeException("Throwable was caught" + caught.getMessage(), caught);
		}
		catch(Error caught)
		{
			caught.printStackTrace();
			throw new Error("Throwable was caught" + caught.getMessage());
		}	
	}

}
