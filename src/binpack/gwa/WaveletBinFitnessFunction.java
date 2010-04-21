package binpack.gwa;

import ga.AbstractWaveletFitnessFunction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.omg.CORBA.OMGVMCID;

import com.syncleus.dann.genetics.AbstractGeneticAlgorithmFitnessFunction;
import com.syncleus.dann.genetics.GeneticAlgorithmChromosome;
import com.syncleus.dann.genetics.wavelets.AbstractWaveletGene;
import com.syncleus.dann.genetics.wavelets.SignalKey;
import com.syncleus.dann.genetics.wavelets.SignalKeyConcentration;
import com.syncleus.dann.math.wave.wavelet.CombinedWaveletFunction;

public class WaveletBinFitnessFunction extends AbstractWaveletFitnessFunction<WaveletBinFitnessFunction>{
	protected double percentFull = 0;
	private int[] bins, items;
	private double[] weights;
	private WaveletBinIndividual individual;
	private ArrayList<Integer> usedInts = new ArrayList<Integer>();
	public WaveletBinFitnessFunction(WaveletBinIndividual chrom, int[] bins, int[] items, double[] weights){
		super(chrom);

		this.individual = chrom;
		WaveletBinCell c = individual.getCell();		
		this.bins = bins;
		this.items = items;
		this.weights = weights;
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
		int[] binSpace = bins.clone();
		c.preTick();
		c.tick();
		List<SignalKeyConcentration> orderedConcentrations = new ArrayList<SignalKeyConcentration>(c.getOrderedSignals()), indexedConcentrations = new ArrayList<SignalKeyConcentration>(c.getConcentrations());
		List<Integer> usedNums = new ArrayList<Integer>();
		int currBin = 0;
		if (orderedConcentrations.size() == 0)
			return;

		int item;
		
		/*for (int i = 0; i < items.length; i++){
			if (indexedConcentrations.get(i%indexedConcentrations.size()).getConcentration() == 0)
				continue;
			item = items[i];
			if (item <= binSpace[currBin]){
				binSpace[currBin] -= item;
				usedNums.add(i);
			} else if (currBin<bins.length-1){
				currBin++;
			}
		}*/

		for (SignalKeyConcentration con : orderedConcentrations){
			boolean used = false;
			if (con.getConcentration() == 0)
				continue;
			int index = (indexedConcentrations.indexOf(con))%items.length;			
			for (int i : usedNums)
				if (i == index){
					used = true;
					break;
				}
			if (used)
				continue;
			item = items[index];
			for (currBin = 0; currBin < bins.length; currBin++)
				if (item <= binSpace[currBin]){
					binSpace[currBin] -= item;
					usedNums.add(index);
					break;
				}
			}

		for (int i = orderedConcentrations.size(); i < items.length;i++){
			boolean used = false;
			for (int j : usedNums)
				if (j == i){
					used = true;
					break;
				}
			if (used)
				continue;
			item = items[i];
			for (currBin = 0; currBin < bins.length;currBin++)
				if (item <= binSpace[currBin]){
					binSpace[currBin] -= item;
					usedNums.add(i);
					break;
				}
		}	
		percentFull = 0;
		for (int i : usedNums)
			percentFull += items[i]*weights[i];
		percentFull /= bins[0]*bins.length;
	//	System.out.println(usedNums + " " + percentFull);
		
	}
	public ArrayList<Double>[] getSolution(){
		WaveletBinCell c = individual.getCell();
		int[] binSpace = bins.clone();
		ArrayList<Double>[] solution = new ArrayList[bins.length];
		for (int i = 0; i < solution.length; i++)
			solution[i] = new ArrayList<Double>();
		List<SignalKeyConcentration> orderedConcentrations = new ArrayList<SignalKeyConcentration>(c.getOrderedSignals()), indexedConcentrations = new ArrayList<SignalKeyConcentration>(c.getConcentrations());
		List<Integer> usedNums = new ArrayList<Integer>();
		int currBin = 0;
		double totalConc = 0;
		for (SignalKeyConcentration con : orderedConcentrations)
			totalConc+=Math.abs(con.getConcentration());
		if (orderedConcentrations.size() == 0)
			return solution;

		int item;
		for (SignalKeyConcentration con : orderedConcentrations){
			boolean used = false;
			currBin = 0;
			for (int i : usedNums)
				if (i == indexedConcentrations.indexOf(con)%items.length){
					used = true;
					break;
				}
			if (used)
				continue;
			item = items[indexedConcentrations.indexOf(con)%items.length];
			if (item <= binSpace[currBin]){
				binSpace[currBin] -= item;
				solution[currBin].add((double)indexedConcentrations.indexOf(con)%items.length);
				usedNums.add(indexedConcentrations.indexOf(con)%items.length);
			} else if (currBin<bins.length-1){
				currBin++;
			}
			else
				break;
		}
		

		return solution; 
	}
	public String toString(){
		String ret = "";
		WaveletBinCell c = individual.getCell();
		List<SignalKeyConcentration> indexedConcentrations = new ArrayList<SignalKeyConcentration>(c.getConcentrations());
		int[] binSpace = bins.clone();
		Set<SignalKeyConcentration> orderedConcentrations = c.getOrderedSignals();
		List<Integer> usedNums = new ArrayList<Integer>();
		int currBin = 0, totalWeight = 0, currBinWeight = 0;		

		if (orderedConcentrations.size() == 0)
			return ret;
		int zerocount = 0;
		
		int itemIndex = 0, itemCount = 0;
		Integer place = orderedConcentrations.size()%items.length;

		for (SignalKeyConcentration conc : orderedConcentrations){
			place = (indexedConcentrations.indexOf(conc))%items.length;
			boolean used = false;
			//System.out.print(".");
			for (int j : usedNums)
				if (place == j){
					used = true;
					break;
				}
			if (used)
				continue;
			int item = items[place];	
				if (item > binSpace[currBin]){
					if (currBin < bins.length-1)
						currBin++;
				} else {	
					
					currBinWeight += item;
					itemCount++;
					usedNums.add(place);
				}
			if (itemCount >= items.length)
				break;
		}


		
		for (int i : usedNums){
			ret+= i + " : " + items[i]*weights[i] + " ";
		}
		return ret;
	}

}
