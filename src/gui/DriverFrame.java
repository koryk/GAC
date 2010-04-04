package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

public class DriverFrame extends JFrame {
	private final Dimension SIZE = new Dimension(800,600);
	public DriverFrame() {
		super();
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(SIZE);
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(new ControlPanel(), BorderLayout.NORTH);
		cp.add(new DisplayPanel(), BorderLayout.CENTER);
	}
	public static void main (String[] args){
		new DriverFrame();
	}
}
