package binpack.sga;


import java.util.List;
import java.util.SortedSet;

import com.syncleus.dann.genetics.AbstractGeneticAlgorithmFitnessFunction;
import com.syncleus.dann.genetics.AbstractValueGene;


public class SimpleBinFitnessFunction extends AbstractGeneticAlgorithmFitnessFunction<SimpleBinFitnessFunction>{
	int[] bins, items;
	public double percentFull = 0;
	public SimpleBinFitnessFunction(SimpleBinChromosome chromosome, int[] bins, int[] items) {
		super(chromosome);
		this.bins = bins;
		this.items = items;
		// TODO Auto-generated constructor stub
	}



	@Override
	public void process() {
		int currBin = 0, totalWeight = 0, currBinWeight = 0;
		final SortedSet<AbstractValueGene> sortedGenes = this.getChromosome().getSortedGenes();
		final List<AbstractValueGene> indexedGenes = this.getChromosome().getGenes();

		for (AbstractValueGene g : sortedGenes) {
			int item = items[indexedGenes.indexOf(g)];
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
	}

	public SimpleBinChromosome getChromosome(){
		return (SimpleBinChromosome)super.getChromosome();
	}
	@Override
	public int compareTo(SimpleBinFitnessFunction comp) {
		if (comp.percentFull > percentFull)
			return -1;
		else if (comp.percentFull < percentFull)
			return 1;
		return 0;
	}
	public String toString() {
		String ret ="";
		int currBin = 0, totalWeight = 0, currBinWeight = 0;
		final SortedSet<AbstractValueGene> sortedGenes = this.getChromosome().getSortedGenes();
		final List<AbstractValueGene> indexedGenes = this.getChromosome().getGenes();
		ret+="[0] ";
		for (AbstractValueGene g : sortedGenes) {
			int item = items[indexedGenes.indexOf(g)];
			if (currBinWeight+item > bins[currBin])
				if (currBin < bins.length-1){
					currBin++;
					ret +=" - " + currBinWeight;
					ret+="\n["+currBin+"] ";
					currBinWeight = 0;
				}
				else ;
			else {
				currBinWeight+=item;
				totalWeight+=item;
				ret += indexedGenes.indexOf(g) + ":" + item + " "; 
			}				
		}
		ret +=" - " + currBinWeight;
		int binSpace=0;
		for (int bin : bins)
			binSpace+=bin;
		ret += "\n " + (double)totalWeight/binSpace;
		return ret;
	}



}
