package binpack.gwa;

import ga.AbstractWaveletFitnessFunction;

import java.util.List;
import java.util.Set;

import com.syncleus.dann.genetics.AbstractGeneticAlgorithmFitnessFunction;
import com.syncleus.dann.genetics.GeneticAlgorithmChromosome;
import com.syncleus.dann.genetics.wavelets.AbstractWaveletGene;

public class WaveletBinFitnessFunction extends AbstractWaveletFitnessFunction<WaveletBinFitnessFunction>{
	protected double percentFull;
	private int[] bins, items;
	private WaveletBinIndividual individual;
	
	public WaveletBinFitnessFunction(WaveletBinIndividual chrom, int[] bins, int[] items){
		super(chrom);
		this.individual = chrom;
		this.bins = bins;
		this.items = items;
	}

	@Override
	public int compareTo(WaveletBinFitnessFunction comp) {
		// TODO Auto-generated method stub
		if (percentFull < comp.percentFull)
			return -1;
		else if (percentFull > comp.percentFull)
			return 1;
		return 0;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		int currBin = 0, totalWeight = 0, currBinWeight = 0;
		WaveletBinCell c = individual.getCell();
		Set<AbstractWaveletGene> orderedGenes = c.getOrderedGenes();
		List<AbstractWaveletGene> genes = c.getGenes();
		for (AbstractWaveletGene g : orderedGenes){
			int item = items[genes.indexOf(g)];
			if (currBinWeight+item > bins[currBin])
				if (currBin < bins.length-1){
					currBin++;
					currBinWeight = 0;
				}
				else
					;
			else {
				currBinWeight+=item;
				totalWeight+=item;
			}			
		}
		int binSpace=0;
		for (int bin : bins)
			binSpace+=bin;
		
		percentFull = (double)totalWeight/binSpace;
		System.out.println(percentFull);
	}

}
