package ga;

import gui.ProblemPanel;
import binpack.BinPackProblem;

public abstract class Problem {
	public abstract Problem instantiateProblem();
	public abstract ProblemPanel getProblemPanel();
}
