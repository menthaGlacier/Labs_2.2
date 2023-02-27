package edu.uni.lab.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Developer extends Employee {
	private static double probability = 0.3;
	protected static long period; // In milliseconds
	static {
		imagePath = new File("").getAbsolutePath() + "/src/main/resources/images/dev.gif";
		image = new Image(imagePath, 30.0, 30.0, false, true);

		period = 3_000_000_000L;
	}

	public Developer(double x, double y) {
		super(x, y);
		imageView = new ImageView();
		imageView.setImage(image);
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