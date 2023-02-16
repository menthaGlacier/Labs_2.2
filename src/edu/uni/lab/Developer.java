package edu.uni.lab;

public class Developer extends Employee {
	private static double probability;

	public static double getProbability() {
		return generationProbability;
	}

	public static void setProbability(double probability) {
		self.probability = probability;
	}
}
