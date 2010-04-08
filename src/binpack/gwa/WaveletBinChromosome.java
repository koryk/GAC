package binpack.gwa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.syncleus.dann.genetics.MutableDouble;
import com.syncleus.dann.genetics.wavelets.AbstractKey;
import com.syncleus.dann.genetics.wavelets.Chromosome;
import com.syncleus.dann.genetics.wavelets.Mutations;
import com.syncleus.dann.genetics.wavelets.WaveletChromatid;

public class WaveletBinChromosome extends Chromosome {
	public WaveletBinChromosome crossOver(WaveletBinChromosome chrom, List<AbstractKey> keypool){
		WaveletChromatid left, right;
			left =  Mutations.getRandom().nextBoolean()? getLeftChromatid().clone() : getRightChromatid().clone();
			right = Mutations.getRandom().nextBoolean()? chrom.getRightChromatid().clone() : chrom.getLeftChromatid().clone();

			WaveletBinChromosome child = new WaveletBinChromosome(left,right);
			while (Mutations.mutationEvent(2*Mutations.getRandom().nextDouble()))
				child.mutate(new HashSet<AbstractKey>(keypool));
		return child;
	}
	public WaveletBinChromosome(){
		super();
	}
	public WaveletBinChromosome(WaveletChromatid left, WaveletChromatid right){
		this.leftChromatid = left;
		this.rightChromatid = right;
	}
	private int findCrossoverPosition(){
		float deviation = Mutations.getRandom().nextFloat();
		WaveletChromatid left, right;
		
		left = getLeftChromatid();
		right = getRightChromatid();
		int crossoverPosition = 0;
		if(Mutations.getRandom().nextBoolean())
		{
			final int length = ( left.getCentromerePosition() < right.getCentromerePosition() ? left.getCentromerePosition() : right.getCentromerePosition());

			final int fromEnd = (int) Math.abs( (new MutableDouble(0d)).mutate(deviation).doubleValue() );
			if(fromEnd > length)
				crossoverPosition = 0;
			else
				crossoverPosition = -1 * (length - fromEnd);
		}
		else
		{
			final int leftLength = left.getGenes().size() - left.getCentromerePosition();
			final int rightLength = right.getGenes().size() - left.getCentromerePosition();

			final int length = ( leftLength < rightLength ? leftLength : rightLength);

			final int fromEnd = (int) Math.abs( (new MutableDouble(0d)).mutate(deviation).doubleValue() );
			if(fromEnd > length)
				crossoverPosition = 0;
			else
				crossoverPosition = (length - fromEnd);
		}
		return crossoverPosition;
		
	}
}
