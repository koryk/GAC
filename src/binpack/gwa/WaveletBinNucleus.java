package binpack.gwa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.syncleus.dann.genetics.Gene;
import com.syncleus.dann.genetics.wavelets.AbstractKey;
import com.syncleus.dann.genetics.wavelets.Chromosome;
import com.syncleus.dann.genetics.wavelets.Mutations;
import com.syncleus.dann.genetics.wavelets.Nucleus;
import com.syncleus.dann.genetics.wavelets.AbstractWaveletGene;;


public class WaveletBinNucleus extends Nucleus{
	public WaveletBinNucleus(ArrayList<Chromosome> chroms){
		super();
		this.chromosomes = chroms;
	}
	public WaveletBinNucleus(){
		super();
		this.chromosomes = new ArrayList<Chromosome>();


		//make sure there is atleast one starting chromosome.
		this.chromosomes.add(new WaveletBinChromosome());

		//there is a chance more chromosomes can be created
		while(Mutations.mutationEvent(mutability))
			this.chromosomes.add(new WaveletBinChromosome());
	}
	public List<AbstractWaveletGene> getGenes(){
		List<AbstractWaveletGene> retVal = new ArrayList<AbstractWaveletGene>();
		for (Chromosome c : this.getChromosomes()){
			for (AbstractWaveletGene g : c.getGenes())
				retVal.add(g);
		}
		return retVal;
	}
	public void mutate()
	{
		HashSet<AbstractKey> allKeys = new HashSet<AbstractKey>();
		for(Chromosome chromosome : this.chromosomes)
			allKeys.addAll(chromosome.getKeys());
		for(Chromosome chromosome : this.chromosomes)
			chromosome.mutate(allKeys);
	}
	public WaveletBinNucleus mate (WaveletBinNucleus partner){
		List<Chromosome> chroms = new ArrayList<Chromosome>(this.getChromosomes());
		List<Chromosome> partnerChroms = new ArrayList<Chromosome>(partner.getChromosomes());
		ArrayList<Chromosome> resultingChroms = new ArrayList<Chromosome>();
		List<AbstractKey> keys = new ArrayList<AbstractKey>();
		if (Math.min(chroms.size(), partnerChroms.size()) != chroms.size()){
			chroms = partnerChroms;
			partnerChroms = new ArrayList<Chromosome>(this.getChromosomes());
		}
		for (Chromosome chrom : partnerChroms)
			keys.addAll(chrom.getKeys());
			
		for (Chromosome chrom : chroms)
			keys.addAll(chrom.getKeys());

		int i = 0;
		
		for (i=0; i < chroms.size(); i++){
			resultingChroms.add(((WaveletBinChromosome)chroms.get(i)).crossOver((WaveletBinChromosome)partnerChroms.get(i),keys));
			
		}

		return new WaveletBinNucleus(resultingChroms);	
			
	}
}
