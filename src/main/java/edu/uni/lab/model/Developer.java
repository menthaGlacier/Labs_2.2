package edu.uni.lab.model;

import java.util.Objects;

import javafx.beans.property.SimpleLongProperty;
import javafx.scene.image.Image;
import edu.uni.lab.utility.Texture;
import javafx.scene.image.ImageView;

public class Developer extends Employee {
	private final static int DEVELOPER_WIDTH = 80;
	private final static int DEVELOPER_HEIGHT = 80;
	private final static Texture texture;
	private static SimpleLongProperty periodProperty = new SimpleLongProperty(0);
	private static double probability = 0.1;

	static {
		texture = new Texture(new Image(Objects.requireNonNull(Developer.class
				.getResourceAsStream("/edu/uni/lab/images/developer.gif")),
				DEVELOPER_WIDTH, DEVELOPER_HEIGHT, false, true),
				DEVELOPER_WIDTH, DEVELOPER_HEIGHT);
	}

	public Developer(double x, double y) {
		super(x, y);
		imageView = new ImageView(texture.getImage());
		imageView.setX(x);
		imageView.setY(y);
	}

	public static long getPeriod() {
		return periodProperty.get();
	}

	public static SimpleLongProperty getPeriodProperty() {
		return periodProperty;
	}

	public static void setPeriod(long period) {
		periodProperty.setValue(period);
	}

	public static double getProbability() {
		return probability;
	}

	public static void setProbability(double probability) {
		Developer.probability = probability;
	}

	public static Texture getTexture() {
		return texture;
	}
}