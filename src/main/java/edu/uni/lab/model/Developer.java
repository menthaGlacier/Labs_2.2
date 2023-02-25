package edu.uni.lab.model;

public class Developer extends Employee {
	private static double probability;

	public Developer(double x, double y) {
		super(x, y);
	}

	public static double getProbability() {
		return probability;
	}

	public static void setProbability(double probability) {
		Developer.probability = probability;
	}
}