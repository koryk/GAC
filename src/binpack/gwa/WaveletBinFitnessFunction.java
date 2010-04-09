package binpack.gwa;

import ga.AbstractWaveletFitnessFunction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.syncleus.dann.genetics.AbstractGeneticAlgorithmFitnessFunction;
import com.syncleus.dann.genetics.GeneticAlgorithmChromosome;
import com.syncleus.dann.genetics.wavelets.AbstractWaveletGene;
import com.syncleus.dann.genetics.wavelets.SignalKey;
import com.syncleus.dann.genetics.wavelets.SignalKeyConcentration;
import com.syncleus.dann.math.wave.wavelet.CombinedWaveletFunction;

public class WaveletBinFitnessFunction extends AbstractWaveletFitnessFunction<WaveletBinFitnessFunction>{
	protected double percentFull = 0;
	private int[] bins, items;
	private WaveletBinIndividual individual;
	
	public WaveletBinFitnessFunction(WaveletBinIndividual chrom, int[] bins, int[] items){
		super(chrom);

		this.individual = chrom;
		WaveletBinCell c = individual.getCell();		
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
		return (comp.hashCode() > hashCode())? 1 : (comp.hashCode() < hashCode())? -1 : 0;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		WaveletBinCell c = individual.getCell();
		List<SignalKeyConcentration> indexedConcentrations = new ArrayList<SignalKeyConcentration>(c.getConcentrations());
		int[] binSpace = bins.clone();
		c.preTick();
		c.tick();
		Set<SignalKeyConcentration> orderedConcentrations = c.getOrderedSignals();
		List<Integer> usedNums = new ArrayList<Integer>();
		int currBin = 0, totalWeight = 0, currBinWeight = 0;		

		if (orderedConcentrations.size() == 0)
			return;
		int zerocount = 0;
		
		int itemIndex = 0, itemCount = 0;
		
		for (SignalKeyConcentration conc : orderedConcentrations){
			Integer place = indexedConcentrations.indexOf(conc)%items.length;
			if (usedNums.contains(place))
				continue;
			int item = items[place];	
			for (currBin = 0; currBin < bins.length; currBin++)
			if (item > binSpace[currBin])
				continue;
			else {				
				binSpace[currBin]-=item;
				itemCount++;
				usedNums.add(place);
			}
			if (itemCount >= items.length)
				break;
		}


		int binRoom=0;
		for (int bin : bins)
			binRoom+=bin;
		
		for (int bin : binSpace)
			totalWeight += bin;
		percentFull = 1-(double)totalWeight/binRoom;
	}
	public ArrayList<Integer>[] getSolution(){
		WaveletBinCell c = individual.getCell();
		List<SignalKeyConcentration> indexedConcentrations = new ArrayList<SignalKeyConcentration>(c.getConcentrations());
		int[] binSpace = bins.clone();
		ArrayList<Integer>[] solution = new ArrayList[bins.length];
		for (int i = 0; i < solution.length; i++)
			solution[i] = new ArrayList<Integer>();
		c.preTick();
		c.tick();
		Set<SignalKeyConcentration> orderedConcentrations = c.getOrderedSignals();
		List<Integer> usedNums = new ArrayList<Integer>();
		int currBin = 0, totalWeight = 0, currBinWeight = 0;		

		if (orderedConcentrations.size() == 0)
			return null;
		int zerocount = 0;
		
		int itemIndex = 0, itemCount = 0;
		
		for (SignalKeyConcentration conc : orderedConcentrations){
			Integer place = indexedConcentrations.indexOf(conc)%items.length;
			if (usedNums.contains(place))
				continue;
			int item = items[place];	
			for (currBin = 0; currBin < bins.length; currBin++)
			if (item > binSpace[currBin])
				continue;
			else {				
				binSpace[currBin]-=item;
				itemCount++;
				solution[currBin].add(item);
				usedNums.add(place);
			}
			if (itemCount >= items.length)
				break;
		}

		return solution; 
	}
	public String toString(){
		String ret = "";
		WaveletBinCell c = individual.getCell();
		List<SignalKeyConcentration> indexedConcentrations = new ArrayList<SignalKeyConcentration>(c.getConcentrations());
			
		Set<SignalKeyConcentration> orderedConcentrations = c.getOrderedSignals();
		List<Integer> usedNums = new ArrayList<Integer>();
		int currBin = 0, totalWeight = 0, currBinWeight = 0;		

		if (orderedConcentrations.size() == 0)
			return ret;
		int zerocount = 0;
		
		int itemIndex = 0, itemCount = 0;
		
		ret += "[0] ";
		for (SignalKeyConcentration conc : orderedConcentrations){
			Integer place = indexedConcentrations.indexOf(conc)%items.length;
			if (usedNums.contains(place))
				continue;
			int item = items[place];	
			if (currBinWeight+item > bins[currBin])
				if (currBin < bins.length-1){
					currBin++;
					currBinWeight = 0;
					ret += "\n[" + currBin + "]" ;
				}
				else
					;
			else {
				
				currBinWeight+=item;
				totalWeight+=item;
				itemCount++;
				ret+= place + ":" + item + " ";
				usedNums.add(place);
			}
			if (itemCount >= items.length)
				break;
		}


		int binSpace=0;
		for (int bin : bins)
			binSpace+=bin;
		ret += "\n" + percentFull;
		return ret;
	}

}
