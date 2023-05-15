package edu.uni.lab.model.employees;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

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
	private final static long trajectoryChangePeriod = 1000;
	private long lastTrajectoryChange = getCreationTime();
	private double velocityX, velocityY;
	private final static double maxVelocity = 10.0;

	static {
		periodProperty = new SimpleLongProperty(1_000);
		probabilityProperty = new SimpleDoubleProperty(0.5);
		lifeTimeProperty = new SimpleLongProperty(5_000);

		texture = new Texture(new Image(Objects.requireNonNull(Developer.class
				.getResourceAsStream("/edu/uni/lab/images/developer.gif")),
				DEVELOPER_WIDTH, DEVELOPER_HEIGHT, false, true),
				DEVELOPER_WIDTH, DEVELOPER_HEIGHT);
	}

	public Developer(double x, double y, long creationTime,
					 double habitatAreaWidth, double habitatAreaHeight) {
		super(x, y, creationTime, habitatAreaWidth, habitatAreaHeight);
		imageView = new ImageView(texture.getImage());
		imageView.setX(x);
		imageView.setY(y);
		velocityX = getRandomVelocity();
		velocityY = getRandomVelocity();
	}

	@Override
	public void move() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastTrajectoryChange >= trajectoryChangePeriod) {
			velocityX = getRandomVelocity();
			velocityY = getRandomVelocity();
			lastTrajectoryChange = currentTime;
		}

		if (getX() + velocityX < 0.0) {
			velocityX *= -1.0;
			setX(velocityX - getX());
		} else if (getX() + velocityX > habitatAreaWidth - getTexture().getWidth()) {
			velocityX *= -1.0;
			double border = habitatAreaWidth - getTexture().getWidth();
			setX(border + velocityX + border - getX());
		} else {
			setX(getX() + velocityX);
		}

		if (getY() + velocityY < 0.0) {
			velocityY *= -1.0;
			setY(velocityY - getY());
		} else if (getY() + velocityY > habitatAreaHeight - getTexture().getHeight()) {
			velocityY *= -1.0;
			double border = habitatAreaHeight - getTexture().getHeight();
			setY(border + velocityY + border - getY());
		} else {
			setY(getY() + velocityY);
		}
	}

	private double getRandomVelocity() {
		return ThreadLocalRandom.current().nextDouble(-maxVelocity, maxVelocity);
	}

	public static Texture getTexture() {
		return texture;
	}

	public static long getPeriod() {
		return periodProperty.get();
	}

	public static SimpleLongProperty period() {
		return periodProperty;
	}

	public static void setPeriod(long period) {
		periodProperty.setValue(period);
	}

	public static double getProbability() {
		return probabilityProperty.getValue();
	}

	public static SimpleDoubleProperty probability() {
		return probabilityProperty;
	}

	public static void setProbability(double probability) {
		probabilityProperty.setValue(probability);
	}

	public static long getLifeTime() {
		return lifeTimeProperty.getValue();
	}

	public static SimpleLongProperty lifeTime() {
		return lifeTimeProperty;
	}

	public static void setLifeTime(long lifeTime) {
		lifeTimeProperty.setValue(lifeTime);
	}
}