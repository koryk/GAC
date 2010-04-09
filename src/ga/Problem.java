package ga;

import gui.ProblemPanel;
import binpack.BinPackProblem;

public abstract class Problem implements Comparable{
	public abstract ProblemPanel getProblemPanel();
}
