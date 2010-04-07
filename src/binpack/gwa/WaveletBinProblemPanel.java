package binpack.gwa;

import binpack.BinPackProblem;
import binpack.sga.SimpleBinChromosome;
import binpack.sga.SimpleBinFitnessFunction;
import binpack.sga.SimpleBinPopulation;

import com.syncleus.dann.genetics.AbstractValueGene;
import com.syncleus.dann.genetics.GeneticAlgorithmChromosome;
import com.syncleus.dann.genetics.wavelets.AbstractOrganism;

import gui.ProblemPanel;

public class WaveletBinProblemPanel extends ProblemPanel{

	@Override
	public void loadPanel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runGA() {
		// TODO Auto-generated method stub
		BinPackProblem prob = new BinPackProblem(3, (int)(Math.random()*70));
		System.out.println(prob);
		final WaveletBinPopulation population = new WaveletBinPopulation(20, .5, .6, prob);
		AbstractOrganism currentWinner;
		System.out.println("hey!");
		population.initializePopulation(4);
		System.out.println("hey!");
		//try{Thread.sleep(10000);}catch(Exception e){;}
		int i=0, max = 100;
		double prevFull = 0;
		try
		{
			for(; i < max; i++){
				System.out.println("hey!");
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
