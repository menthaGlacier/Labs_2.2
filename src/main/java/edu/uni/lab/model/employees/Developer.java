package edu.uni.lab.model.employees;

import java.util.Objects;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import edu.uni.lab.utility.Texture;

public class Developer extends Employee {
	private final static int DEVELOPER_WIDTH = 80;
	private final static int DEVELOPER_HEIGHT = 80;
	private final static Texture texture;
	private final static SimpleLongProperty periodProperty;
	private final static SimpleDoubleProperty probabilityProperty;
	private final static SimpleLongProperty lifeTimeProperty;

	static {
		periodProperty = new SimpleLongProperty(5_000);
		probabilityProperty = new SimpleDoubleProperty(0.1);
		lifeTimeProperty = new SimpleLongProperty(20_000);

		texture = new Texture(new Image(Objects.requireNonNull(Developer.class
				.getResourceAsStream("/edu/uni/lab/images/developer.gif")),
				DEVELOPER_WIDTH, DEVELOPER_HEIGHT, false, true),
				DEVELOPER_WIDTH, DEVELOPER_HEIGHT);
	}

	public Developer(double x, double y, long creationTime) {
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

	public static SimpleLongProperty periodProperty() {
		return periodProperty;
	}

	public static void setPeriod(long period) {
		periodProperty.setValue(period);
	}

	public static double getProbability() {
		return probabilityProperty.getValue();
	}

	public static SimpleDoubleProperty probabilityProperty() {
		return probabilityProperty;
	}

	public static void setProbability(double probability) {
		probabilityProperty.setValue(probability);
	}

	public static long getLifeTime() {
		return lifeTimeProperty.getValue();
	}

	public static SimpleLongProperty lifeTimeProperty() {
		return lifeTimeProperty;
	}

	public static void setLifeTime(long lifeTime) {
		lifeTimeProperty.setValue(lifeTime);
	}
}