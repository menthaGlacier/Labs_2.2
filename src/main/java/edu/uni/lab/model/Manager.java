package edu.uni.lab.model;

public class Manager extends Employee {
	private static double ratio;

	public Manager(double x, double y) {
		super(x, y);
	}

	public static double getRatio() {
		return ratio;
	}

	public static void setRatio(double ratio) {
		Manager.ratio = ratio;
	}
}