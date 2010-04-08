package binpack;

import binpack.gwa.WaveletBinProblemPanel;
import binpack.sga.SimpleBinProblemPanel;
import ga.Problem;
import gui.ProblemPanel;

public class BinPackProblem extends Problem{
	private final int MAXBINSIZE = 300;
	private final int MAXITEMSIZE = 130;
	private int numBins, numItems;
	private int[] bins, items;
	public final static int SGA = 13, GWA = 14;
	private int implementation;
	public BinPackProblem(int numBin, int numItem, int implementation){
		numBins = numBin;
		numItems = numItem;
		if (implementation == SGA || implementation == GWA)
			this.implementation = implementation;
		else
			throw new IllegalArgumentException("Must be valid implementation type");
		bins = new int[numBins];
		items = new int[numItems];
		for (int i = 0; i < numBin; i++)
			bins[i] = (int)(Math.random()*MAXBINSIZE);
		for (int i = 0; i < numItem; i++)
			items[i] = (int)(Math.random()*MAXITEMSIZE);
	}
	public BinPackProblem(int[] bins, int[] items){
		this.bins = bins;
		this.items = items;
		this.numBins = bins.length;
		this.numItems = items.length;
	}
	public void setImplementation (int implementation){
		if (implementation == SGA || implementation == GWA)
			this.implementation = implementation;
		else
			throw new IllegalArgumentException("Must be valid implementation type");
	}
	public int[] getBins() {
		return bins;
	}
	public int[] getItems() {
		return items;
	}

	public String toString(){
		String ret = "";
		for (int i = 0; i < numBins; i++)
			ret+="[" + bins[i] + "]";
		ret +="\n";
		for (int i = 0; i < numItems; i++)
			ret+="(" + items[i] + ")";
		
		return ret;
	}

	public int compareTo(Object o) {
		if (o instanceof BinPackProblem){
			return ((BinPackProblem)o).implementation == implementation? 0 : 1;
		}
		return -1;
	}
	@Override
	public ProblemPanel getProblemPanel() {
		switch (implementation){
		case SGA:
			return new SimpleBinProblemPanel(this);
		case GWA:
			return new WaveletBinProblemPanel(this);
		}
		return null;
	}
}
