package binpack.sga;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.UIManager;

import com.syncleus.core.dann.examples.tsp.TravellingSalesmanChromosome;
import com.syncleus.dann.genetics.AbstractValueGene;
import com.syncleus.dann.genetics.GeneticAlgorithmChromosome;






import binpack.BinPackProblem;
import ga.Problem;
import gui.ProblemPanel;

public class SimpleBinProblemPanel extends ProblemPanel{

	public SimpleBinProblemPanel(Problem problem){
		super(problem);
	}

	private Future<SimpleBinChromosome> futureWinner = null;
	private final ExecutorService executor = Executors.newFixedThreadPool(1);
	private SimpleBinChromosome currentWinner = null;
	public void loadPanel() {
		// TODO Auto-generated method stub
		
	}
@Override
	public void runGA() {		
		final SimpleBinPopulation population = new SimpleBinPopulation(20, .4, .4, (BinPackProblem)(problem));
		population.initializePopulation(50);
		//try{Thread.sleep(10000);}catch(Exception e){;}
		int i=0, max = 50;
		SimpleBinFitnessFunction oldWinner = null;
		int convergeGeneration=0;
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
					//System.out.println("Found solution in " + i + " generations");
					break;}
				if (prevFull < winner.percentFull){
					//System.out.println(winner);
					oldWinner = winner;
					convergeGeneration = population.getGenerations();
					prevFull = winner.percentFull;
					//System.out.println("Wooooo !!" + (prevFull = winner.percentFull) + " " + population.getGenerations() + " generations");				
				for (AbstractValueGene g : ((SimpleBinChromosome)currentWinner).getSortedGenes())
					;//System.out.print(currentWinner.getGenes().indexOf(g) + ":" + g.getValue() + " ");
				//System.out.println();
				}
				
			}//this.currentWinner = this.futureWinner.get();
			FileOutputStream os = new FileOutputStream(new File("sga.txt"),true);
			os.write((problem.toString() + ": " + prevFull + " at " + convergeGeneration + " : ").getBytes());
			for (int j = 0 ; j < oldWinner.getSolution().length; j++){
				os.write((j + ":").getBytes());
				for (Double d : oldWinner.getSolution()[j])
					os.write((d + ",").getBytes());
			}
			os.write("\n\n".getBytes());
			os.close();
			System.out.println(problem.toString() + ": " + prevFull + " at " + convergeGeneration );
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



	

