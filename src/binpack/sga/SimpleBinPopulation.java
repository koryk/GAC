package binpack.sga;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import binpack.BinPackProblem;

import com.syncleus.core.dann.examples.tsp.TravellingSalesmanChromosome;
import com.syncleus.dann.InterruptedDannRuntimeException;
import com.syncleus.dann.UnexpectedDannError;
import com.syncleus.dann.genetics.AbstractGeneticAlgorithmFitnessFunction;
import com.syncleus.dann.genetics.AbstractGeneticAlgorithmPopulation;
import com.syncleus.dann.genetics.GeneticAlgorithmChromosome;
import com.syncleus.dann.genetics.AbstractGeneticAlgorithmPopulation;

import org.apache.log4j.Logger;

public class SimpleBinPopulation extends AbstractGeneticAlgorithmPopulation{
	private final static Logger LOGGER = Logger.getLogger(SimpleBinPopulation.class);
	private BinPackProblem problem;
	public SimpleBinPopulation(double mutationDeviation,
			double crossoverPercentage, double dieOffPercentage, BinPackProblem problem) {
		super(mutationDeviation, crossoverPercentage, dieOffPercentage);
		this.problem = problem;
		
	}

	@Override
	protected AbstractGeneticAlgorithmFitnessFunction packageChromosome(
			GeneticAlgorithmChromosome chrom) {
		// TODO Auto-generated method stub
		return new SimpleBinFitnessFunction((SimpleBinChromosome)chrom, problem.getBins(), problem.getItems());
	}
	private static Set<GeneticAlgorithmChromosome> initialChromosomes(final int populationSize, int itemSize)
	{
		final HashSet<GeneticAlgorithmChromosome> returnValue = new HashSet<GeneticAlgorithmChromosome>();
		for (int i = 0; i < populationSize;i++)
			returnValue.add(new SimpleBinChromosome(itemSize));
		return returnValue;
	}
	public void initializePopulation(final int populationSize)
	{
		
		if(populationSize < 4)
			throw new IllegalArgumentException("populationSize must be at least 4");
		Set<GeneticAlgorithmChromosome> chroms  = initialChromosomes(populationSize, problem.getItems().length);
		System.out.println(chroms.size());
		
		this.addAll(chroms);
	
	}

	public final SimpleBinChromosome getWinner()
	{
		GeneticAlgorithmChromosome winner = super.getWinner();
		assert(winner instanceof SimpleBinChromosome);
		return (SimpleBinChromosome) winner;
	}
	
}
