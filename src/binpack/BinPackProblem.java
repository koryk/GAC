package binpack;

import java.awt.Graphics;
import java.util.ArrayList;

import binpack.gwa.WaveletBinProblemPanel;
import binpack.sga.SimpleBinProblemPanel;
import ga.Problem;
import gui.ProblemPanel;

public class BinPackProblem extends Problem{
	private final int MAXBINSIZE = 300;
	private final int MAXITEMSIZE = 130;
	private int numBins, numItems;
	private int[] bins, items;
	private double[] weights;
	private ArrayList<Double>[] solution;
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
		weights = new double[numItems];
		int binSize = (int)(Math.random()*MAXBINSIZE + 50);		
		solution = new ArrayList[bins.length];
		for (int i = 0; i < numBin; i++)
			bins[i] = binSize;
			for (int i = 0; i < numItem; i++)
			items[i] = (int)(Math.random()*MAXITEMSIZE + 30);
		for (int i = 0; i < solution.length; i++)
			solution[i] = new ArrayList<Double>();
	}
	public BinPackProblem(int numBin, int numItem, int implementation, int hardness){
		numBins = numBin;
		numItems = numItem;
		if (implementation == SGA || implementation == GWA)
			this.implementation = implementation;
		else
			throw new IllegalArgumentException("Must be valid implementation type");
		bins = new int[numBins];
		items = new int[numItems];
		weights = new double[numItems];
		int binSize = (int)(Math.random()*hardness*numItems);		
		solution = new ArrayList[bins.length];
		for (int i = 0; i < numBin; i++)
			bins[i] = binSize;
			for (int i = 0; i < numItem; i++){
				items[i] = (int)(Math.random()*binSize);
				weights[i] = Math.random();
			}
		for (int i = 0; i < solution.length; i++)
			solution[i] = new ArrayList<Double>();
	}
	public BinPackProblem(int[] bins, int[] items, double[] weights){
		this.bins = bins;
		this.items = items;
		this.weights = weights;
		this.numBins = bins.length;
		this.numItems = items.length;
		solution = new ArrayList[bins.length];
		for (int i = 0; i < solution.length; i++)
			solution[i] = new ArrayList<Double>();
	}
	public void setSolution(ArrayList<Double>[] bins){
		this.solution = bins;
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
	public double[] getWeights() {
		return weights;
	}

	public String toString(){
		String ret = "";
		
			ret+=bins.length + "x [" + bins[0] + "]:";
		ret +="\n(";
		for (int i = 0; i < numItems; i++)
			ret+=items[i]*weights[i] + ",";
		ret += ")\n";
		
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
	public void paint (Graphics g){
		int binSize = 100;
		if (solution == null || solution.length==0)
			return;
		int rows = bins.length/3;
		int x = 20;
		int y = 20;
		int binTotal = 0, binTotFull=0;
		
		for (int i : bins)
			binTotal+=i;
		for (int i = 0; i < bins.length; i++)
		{
			g.drawRect(x, y, binSize, binSize);
			double binFull = 0;
			for (Double j : solution[i])
				binFull += j;
		
			binTotFull += binFull;
			g.fillRect(x+binSize, y+binSize, binSize, (int)(binSize*((double)binFull/bins[i])));
			g.drawString((double)binFull/bins[i]+"", x, y);
			x+= 120;
			if ((i/3)%1 == 0){
				y += 120;
				x = 20;
			}
		}
	}
}
