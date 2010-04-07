package binpack.sga;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.UIManager;

import com.syncleus.core.dann.examples.tsp.TravellingSalesmanChromosome;
import com.syncleus.dann.genetics.AbstractValueGene;
import com.syncleus.dann.genetics.GeneticAlgorithmChromosome;






import binpack.BinPackProblem;
import gui.ProblemPanel;

public class SimpleBinProblemPanel extends ProblemPanel{


	private Future<SimpleBinChromosome> futureWinner = null;
	private final ExecutorService executor = Executors.newFixedThreadPool(1);
	private SimpleBinChromosome currentWinner = null;
	public void loadPanel() {
		// TODO Auto-generated method stub
		
	}
@Override
	public void runGA() {
		BinPackProblem prob = new BinPackProblem(3, (int)(Math.random()*70));
		System.out.println(prob);
		final SimpleBinPopulation population = new SimpleBinPopulation(20, .5, .6, prob);
		population.initializePopulation(100);
		//try{Thread.sleep(10000);}catch(Exception e){;}
		int i=0, max = 100;
		double prevFull = 0;
		try
		{
			for(; i < max; i++){
				population.nextGeneration();
				currentWinner = population.getWinner();
				for (GeneticAlgorithmChromosome c : population.getChromosomes()){
					for (AbstractValueGene g : ((SimpleBinChromosome)c).getSortedGenes())
						;//System.out.print(g.getValue().longValue()+" ");
					//System.out.println();
				}
				SimpleBinFitnessFunction winner = (SimpleBinFitnessFunction)population.packageChromosome(currentWinner);
				winner.process();
				if (prevFull == 1){ 
					System.out.println("Found solution in " + i + " generations");
					break;}
				if (prevFull < winner.percentFull){
					System.out.println(winner);					
					System.out.println("Wooooo !!" + (prevFull = winner.percentFull));				
				for (AbstractValueGene g : ((SimpleBinChromosome)currentWinner).getSortedGenes())
					System.out.print(currentWinner.getGenes().indexOf(g) + ":" + g.getValue() + " ");
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



	

