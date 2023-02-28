package edu.uni.lab.model;

import edu.uni.lab.textureloader.TextureLoader;

public class Manager extends Employee {
	private static double ratio = 0.3;
	protected static long period; // In milliseconds
	static {
		period = 3_000_000_000L;
	}

	public Manager(double x, double y) {
		super(x, y);
		imageView = TextureLoader.createMgrImageView();
		imageView.setX(x);
		imageView.setY(y);
	}

	public static double getRatio() {
		return ratio;
	}

	public static void setRatio(double ratio) {
		Manager.ratio = ratio;
	}

	public static long getPeriod() {
		return period;
	}

	public static void setPeriod(long period) {
		Manager.period = period;
	}
}