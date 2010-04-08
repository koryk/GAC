package binpack.gwa;

import ga.AbstractWaveletFitnessFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.syncleus.dann.genetics.AbstractGeneticAlgorithmFitnessFunction;
import com.syncleus.dann.genetics.GeneticAlgorithmChromosome;
import com.syncleus.dann.genetics.wavelets.AbstractWaveletGene;
import com.syncleus.dann.math.wave.wavelet.CombinedWaveletFunction;

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
		List<AbstractWaveletGene> genes = new ArrayList<AbstractWaveletGene>(c.getGenes());
		List<AbstractWaveletGene> sortedGenes = new ArrayList<AbstractWaveletGene>(c.getOrderedGenes()); 
		List<Integer> orderedList = new ArrayList<Integer>(), usedList = new ArrayList<Integer>(), notUsedList = new ArrayList<Integer>();
		for (int i : items){
			notUsedList.add(i);
			orderedList.add(i);
		}
			
		double geneTotal = 0;
		individual.preTick();
		individual.tick();
		for (AbstractWaveletGene g : genes){
			geneTotal += Math.abs(g.expressionActivity());
		}
		int currItem = (int)(Math.abs(sortedGenes.get(0).expressionActivity())/geneTotal*items.length);
		for (int i = 0; i < sortedGenes.size() && i < items.length; i++){
			currItem+= genes.indexOf(sortedGenes.get(i));
			currItem%=notUsedList.size();
			int item = items[currItem];
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
				usedList.add(orderedList.indexOf(new Integer(item)));
				notUsedList.remove(currItem);
			}
			
		}
		int binSpace=0;
		for (int bin : bins)
			binSpace+=bin;
		percentFull = (double)totalWeight/binSpace;
	}
	public String toString(){
		String ret = "";

		WaveletBinCell c = individual.getCell();
		List<AbstractWaveletGene> genes = new ArrayList<AbstractWaveletGene>(c.getGenes());
		List<AbstractWaveletGene> sortedGenes = new ArrayList<AbstractWaveletGene>(c.getOrderedGenes()); 
		int currBin = 0, totalWeight = 0, currBinWeight = 0;		
		double geneTotal=0;
		for (AbstractWaveletGene g : genes){
			geneTotal += Math.abs(g.expressionActivity());
		}
		
		int currItem = (int)(Math.abs(sortedGenes.get(0).expressionActivity())/geneTotal*items.length);
		System.out.print("[0]");	
		for (int i = 0; i < genes.size(); i++){
			currItem%=items.length;
			int item = items[currItem];	
			if (currBinWeight+item > bins[currBin])
				if (currBin < bins.length-1){
					System.out.print(currBinWeight);
					currBin++;
					System.out.print("\n[" + currBin + "]");
					currBinWeight = 0;
				}
				else
					;
			else {				
				currBinWeight+=item;
				totalWeight+=item;
				System.out.print(currItem + ":" + item + " ");
			}
			currItem++;
		}
		System.out.println(currBinWeight);

		int binSpace=0;
		for (int bin : bins)
			binSpace+=bin;
		
		return ret;
	}

}
