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
		Set<AbstractOrganism> chroms  = initializeIndividuals(populationSize, problem.getItems().length);
		System.out.println(chroms.size());
		this.addAll(chroms);
	}

	private Set<AbstractOrganism> initializeIndividuals(
			int populationSize, int length) {
		Set<AbstractOrganism> retSet = new HashSet<AbstractOrganism>();
		for (int i = 0; i < populationSize; i++)
			retSet.add(new WaveletBinIndividual());
		
		return retSet;
	}

	@Override
	protected AbstractWaveletFitnessFunction packageChromosome(
			AbstractOrganism chromosome) {
		return new WaveletBinFitnessFunction((WaveletBinIndividual)chromosome, problem.getBins(), problem.getItems());
	}

}
