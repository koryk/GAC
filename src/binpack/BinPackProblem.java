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
	public BinPackProblem(int numBin, int numItem){
		numBins = numBin;
		numItems = numItem;
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
	
	public int[] getBins() {
		return bins;
	}
	public int[] getItems() {
		return items;
	}
	public BinPackProblem instantiateProblem(){
		return new BinPackProblem((int)(Math.random()*10), (int)(Math.random()*100));
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
	@Override
	public ProblemPanel getProblemPanel() {
		// TODO Auto-generated method stub
		return new WaveletBinProblemPanel();
	}
}
