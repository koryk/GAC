package binpack.gwa;

import ga.AbstractWaveletFitnessFunction;

import java.util.ArrayList;
import java.util.Arrays;
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
		individual.preTick();
		individual.tick();
		WaveletBinCell c = individual.getCell();
		List<AbstractWaveletGene> genes = new ArrayList<AbstractWaveletGene>(c.getGenes());
		List<Integer> itemList = new ArrayList<Integer>();
		double geneTotal = 0;
		for (AbstractWaveletGene g : genes){
			geneTotal += g.expressionActivity();
		}
		for (int i = 0; i < items.length; i++)
			itemList.add(items[i]);
		for (int i = 0; i < genes.size() && itemList.size()!=0; i++){
			System.out.print((int)(((Math.abs(genes.get(i).expressionActivity()/geneTotal))*itemList.size())) + " ");
			int item = itemList.remove((int)(((Math.abs(genes.get(i).expressionActivity()/geneTotal))*itemList.size())%itemList.size()));
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
		System.out.println();
		int binSpace=0;
		for (int bin : bins)
			binSpace+=bin;
		
		percentFull = (double)totalWeight/binSpace + Math.random()/10000;
	}

}
