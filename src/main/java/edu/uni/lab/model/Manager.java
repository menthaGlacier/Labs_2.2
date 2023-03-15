package edu.uni.lab.model;

import java.util.Objects;

import javafx.beans.property.SimpleLongProperty;
import javafx.scene.image.Image;
import edu.uni.lab.utility.Texture;
import javafx.scene.image.ImageView;

public class Manager extends Employee {
	private final static int MANAGER_WIDTH = 80;
	private final static int MANAGER_HEIGHT = 80;

	private final static Texture texture;
	private static SimpleLongProperty periodProperty = new SimpleLongProperty(0);
	private static double ratio = 0.1;

	static {
		texture = new Texture(new Image(Objects.requireNonNull(Developer.class
				.getResourceAsStream("/edu/uni/lab/images/manager.png")),
				MANAGER_WIDTH, MANAGER_HEIGHT, false, true),
				MANAGER_WIDTH, MANAGER_HEIGHT);
	}

	public Manager(double x, double y) {
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

	public static double getRatio() {
		return ratio;
	}

	public static void setRatio(double ratio) {
		Manager.ratio = ratio;
	}

	public static Texture getTexture() {
		return texture;
	}
}