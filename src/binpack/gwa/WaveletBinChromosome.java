package binpack.gwa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.syncleus.dann.genetics.MutableDouble;
import com.syncleus.dann.genetics.wavelets.AbstractKey;
import com.syncleus.dann.genetics.wavelets.AbstractWaveletGene;
import com.syncleus.dann.genetics.wavelets.Chromosome;
import com.syncleus.dann.genetics.wavelets.Mutations;
import com.syncleus.dann.genetics.wavelets.WaveletChromatid;

public class WaveletBinChromosome extends Chromosome {
	private int size;
	public WaveletBinChromosome crossOver(WaveletBinChromosome chrom, List<AbstractKey> keypool){
		WaveletChromatid left, right;
		if (Mutations.getRandom().nextBoolean()){
			left =  getLeftChromatid();
			right = chrom.getRightChromatid();
		}
		else{
			left = chrom.getLeftChromatid();
			right = getRightChromatid();
		}
			WaveletBinChromosome child = new WaveletBinChromosome(left,right);
			while (Mutations.mutationEvent(Mutations.getRandom().nextDouble()))
				child.mutate(new HashSet<AbstractKey>(keypool));
		return child;
	}
	public WaveletBinChromosome(int size){
		super();
		this.size = size;
		this.leftChromatid = WaveletChromatid.newRandomWaveletChromatid(size/2);
		this.rightChromatid = WaveletChromatid.newRandomWaveletChromatid(size/2 + size%2);
	}
	public WaveletBinChromosome(WaveletChromatid left, WaveletChromatid right){
		super();
		this.leftChromatid = left;
		this.rightChromatid = right;
	}
	public int getSize() {
		return size;
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
