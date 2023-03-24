package edu.uni.lab.model;

import java.util.Objects;

import javafx.beans.property.SimpleLongProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import edu.uni.lab.utility.Texture;

public class Manager extends Employee {
	private final static int MANAGER_WIDTH = 80;
	private final static int MANAGER_HEIGHT = 80;
	private final static Texture texture;
	private final static SimpleLongProperty periodProperty;
	private static double ratio;
	private static long lifeTime;

	static {
		// TODO These values should be defined constants
		periodProperty = new SimpleLongProperty(1000);
		ratio = 0.1;
		lifeTime = 10_000;

		texture = new Texture(new Image(Objects.requireNonNull(Developer.class
				.getResourceAsStream("/edu/uni/lab/images/manager.png")),
				MANAGER_WIDTH, MANAGER_HEIGHT, false, true),
				MANAGER_WIDTH, MANAGER_HEIGHT);
	}

	public Manager(double x, double y, long creationTime) {
		super(x, y, creationTime);
		imageView = new ImageView(texture.getImage());
		imageView.setX(x);
		imageView.setY(y);
	}

	public static Texture getTexture() {
		return texture;
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

	public static long getLifeTime() {
		return lifeTime;
	}

	public static void setLifeTime(long lifeTime) {
		Manager.lifeTime = lifeTime;
	}
}