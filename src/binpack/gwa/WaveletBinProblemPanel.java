package binpack.gwa;

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

	@Override
	public void runGA() {
		// TODO Auto-generated method stub
		System.out.println(problem);
		final WaveletBinPopulation population = new WaveletBinPopulation(20, .5, .4, (BinPackProblem)problem);
		AbstractOrganism currentWinner;
		population.initializePopulation(20);
		//try{Thread.sleep(10000);}catch(Exception e){;}
		int i=0, max = 100;
		double prevFull = 0;
		try
		{
			for(; i < max; i++){
				population.nextGeneration();
				currentWinner = population.getWinner();
				WaveletBinFitnessFunction winner = (WaveletBinFitnessFunction)population.packageChromosome(currentWinner);
				winner.process();
				if (prevFull == 1){ 
					System.out.println("Found solution in " + i + " generations");
					break;}
				if (prevFull < winner.percentFull){
					System.out.println(winner);					
					System.out.println("Wooooo !!" + (prevFull = winner.percentFull));				
				System.out.println();
				}
			}//this.currentWinner = this.futureWinner.get();
			System.out.println("ga over");
		}		
		catch(Exception caught)
		{
			caught.printStackTrace();				
			throw new RuntimeException("Throwable was caught" + caught.getMessage(), caught);
		}
		catch(Error caught)
		{
			throw new Error("Throwable was caught" + caught.getMessage());
		}	
	}

}
