package binpack.sga;

import java.util.Collections;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import com.syncleus.dann.genetics.AbstractValueGene;
import com.syncleus.dann.genetics.GeneticAlgorithmChromosome;

public class SimpleBinChromosome extends GeneticAlgorithmChromosome{

	public SimpleBinChromosome(int itemCount) {
		super(itemCount, 10.0);
		// TODO Auto-generated constructor stub
	}
	public SortedSet<AbstractValueGene> getSortedGenes()
	{
		TreeSet<AbstractValueGene> sortedGenes = new TreeSet<AbstractValueGene>(new Comparator<AbstractValueGene>()
							{
								public int compare(AbstractValueGene gene1, AbstractValueGene gene2)
								{
									if( gene1.getValue().doubleValue() < gene2.getValue().doubleValue() )
										return -1;
									else if( gene1.getValue().doubleValue() > gene2.getValue().doubleValue() )
										return 1;
									else
										return 0;
								}
							});

		sortedGenes.addAll(this.getGenes());

		return Collections.unmodifiableSortedSet(sortedGenes);
	}
	public SimpleBinChromosome mutate(double deviation)
	{
		SimpleBinChromosome mutated = (SimpleBinChromosome)super.mutate(deviation);
		return mutated;
	}
	

}
