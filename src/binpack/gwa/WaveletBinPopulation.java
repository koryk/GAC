package binpack.gwa;

import ga.AbstractWaveletFitnessFunction;
import ga.AbstractWaveletPopulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import binpack.BinPackProblem;

import com.syncleus.dann.genetics.AbstractGeneticAlgorithmFitnessFunction;
import com.syncleus.dann.genetics.AbstractGeneticAlgorithmPopulation;
import com.syncleus.dann.genetics.GeneticAlgorithmChromosome;
import com.syncleus.dann.genetics.wavelets.AbstractOrganism;
import com.syncleus.dann.genetics.wavelets.Mutations;

public class WaveletBinPopulation extends AbstractWaveletPopulation{
	private BinPackProblem problem;
	public WaveletBinPopulation(double mutationDeviation,
			double crossoverPercentage, double dieOffPercentage, BinPackProblem problem) {
		super(mutationDeviation, crossoverPercentage, dieOffPercentage);
		this.problem = problem;
		// TODO Auto-generated constructor stub
	}


	public void initializePopulation(final int populationSize){
		if(populationSize < 4)
			throw new IllegalArgumentException("populationSize must be at least 4");
		int popLeft;
		while ((popLeft = populationSize - getPopulationSize()) != 0){
			Set<AbstractOrganism> chroms  = initializeIndividuals(popLeft);
		this.addAll(chroms);
		}
	}

	private Set<AbstractOrganism> initializeIndividuals(int populationSize) {
		Set<AbstractOrganism> retSet = new HashSet<AbstractOrganism>();
		WaveletBinIndividual hank;//hank is my cat
		for (int i = 0; i < populationSize; i++){
			hank = new WaveletBinIndividual();
			for(int j = 0; i < Mutations.getRandom().nextInt(40); j++)
				hank.getCell().mutate();			
			retSet.add(hank);			
		}		
		return retSet;
	}

	@Override
	protected AbstractWaveletFitnessFunction<WaveletBinFitnessFunction> packageChromosome(
			AbstractOrganism chromosome) {
		return new WaveletBinFitnessFunction((WaveletBinIndividual)chromosome, problem.getBins(), problem.getItems(), problem.getWeights());
	}

}
