package edu.uni.lab.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Manager extends Employee {
	private static double ratio = 0.3;
	protected static long period; // In milliseconds
	static {
		imagePath = new File("").getAbsolutePath() + "/src/main/resources/images/mgr.png";
		image = new Image(imagePath, 30.0, 30.0, false, true);

		period = 10_000_000_000L;
	}

	public Manager(double x, double y) {
		super(x, y);
		imageView = new ImageView(image);
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