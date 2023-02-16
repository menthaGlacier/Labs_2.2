package edu.uni.lab;

public class Developer extends Employee {
	private static double probability;

	public static double getProbability() {
		return probability;
	}

	public static void setProbability(double probability) {
		Developer.probability = probability;
	}
}
