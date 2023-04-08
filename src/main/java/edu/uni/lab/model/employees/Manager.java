package edu.uni.lab.model.employees;

import edu.uni.lab.model.employees.Developer;
import edu.uni.lab.model.employees.Employee;
import edu.uni.lab.utility.Texture;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Manager extends Employee {
	private final static int MANAGER_WIDTH = 80;
	private final static int MANAGER_HEIGHT = 80;
	private final static Texture texture;
	private final static SimpleLongProperty periodProperty;
	private final static SimpleDoubleProperty ratioProperty;
	private final static SimpleLongProperty lifeTimeProperty;

	static {
		periodProperty = new SimpleLongProperty(5_000);
		ratioProperty = new SimpleDoubleProperty(0.1);
		lifeTimeProperty = new SimpleLongProperty(20_000);

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

	public static SimpleLongProperty periodProperty() {
		return periodProperty;
	}

	public static void setPeriod(long period) {
		periodProperty.setValue(period);
	}

	public static double getRatio() {
		return ratioProperty.getValue();
	}

	public static SimpleDoubleProperty ratioProperty() {
		return ratioProperty;
	}

	public static void setRatio(double probability) {
		ratioProperty.setValue(probability);
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