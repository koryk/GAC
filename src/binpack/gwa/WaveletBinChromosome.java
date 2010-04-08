package binpack.gwa;

import com.syncleus.dann.genetics.MutableDouble;
import com.syncleus.dann.genetics.wavelets.Chromosome;
import com.syncleus.dann.genetics.wavelets.Mutations;
import com.syncleus.dann.genetics.wavelets.WaveletChromatid;

public class WaveletBinChromosome extends Chromosome {
	public WaveletBinChromosome crossOver(WaveletBinChromosome chrom){
		WaveletChromatid left, right;
			left =  Mutations.getRandom().nextBoolean()? getLeftChromatid() : getRightChromatid();
			right = Mutations.getRandom().nextBoolean()? chrom.getRightChromatid() : chrom.getLeftChromatid();
		/*System.out.println("\nparent 1");
		for (int i = 0; i < Math.max(getLeftChromatid().getGenes().size(),getRightChromatid().getGenes().size()); i++){
			if (i < getLeftChromatid().getGenes().size())
				System.out.print(getLeftChromatid().getGenes().get(i).expressionActivity() + " ");
			if (i < getRightChromatid().getGenes().size())
				System.out.print(getRightChromatid().getGenes().get(i).expressionActivity());	
			System.out.println();
		}
		System.out.println("parent 2");
		for (int i = 0; i < Math.max(chrom.getLeftChromatid().getGenes().size(),chrom.getRightChromatid().getGenes().size()); i++){
			if (i < chrom.getLeftChromatid().getGenes().size())
				System.out.print(chrom.getLeftChromatid().getGenes().get(i).expressionActivity() + " ");
			if (i < chrom.getRightChromatid().getGenes().size())
				System.out.print(chrom.getRightChromatid().getGenes().get(i).expressionActivity());
			System.out.println();
		}
		System.out.println("\nparent 3");
		for (int i = 0; i < Math.max(left.getGenes().size(),right.getGenes().size()); i++){
			if (i < left.getGenes().size())
				System.out.print(left.getGenes().get(i).expressionActivity() + " ");
			if (i < right.getGenes().size())
				System.out.print(right.getGenes().get(i).expressionActivity());
			System.out.println();
		}*/
		
		left.mutate(right.getKeys());
		right.mutate(left.getKeys());
		return new WaveletBinChromosome(left,right);
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
