package edu.uni.lab;

public class Manager extends Employee {
	private static double ratio;

	public static double getRatio() {
		return ratio;
	}

	public static void setRatio(double ratio) {
		self.ratio = ratio;
	}
}
