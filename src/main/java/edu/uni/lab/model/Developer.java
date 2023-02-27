package edu.uni.lab.model;

import edu.uni.lab.textureloader.TextureLoader;

public class Developer extends Employee {
	private static double probability = 0.3;
	protected static long period; // In milliseconds
	static {
		period = 3_000_000_000L;
	}

	public Developer(double x, double y) {
		super(x, y);
		imageView = TextureLoader.createDevImageView();
		imageView.setLayoutX(x);
		imageView.setLayoutY(y);
	}

	public static double getProbability() {
		return probability;
	}

	public static void setProbability(double probability) {
		Developer.probability = probability;
	}

	public static long getPeriod() {
		return period;
	}

	public static void setPeriod(long period) {
		Developer.period = period;
	}
}