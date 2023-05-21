package edu.uni.lab.model.employees;

import edu.uni.lab.utility.Texture;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;

public class Manager extends Employee {
	private final static Texture texture;
	private final static int MANAGER_WIDTH = 80;
	private final static int MANAGER_HEIGHT = 80;
	private final static double minRadius = 50.0;
	private final static double maxRadius = 150.0;
	private final static double maxLinearVelocity = 10.0;
	private final static SimpleLongProperty periodProperty;
	private final static SimpleDoubleProperty ratioProperty;
	private final static SimpleLongProperty lifeTimeProperty;
	private final double trajectoryRadius;
	private final double circleX;
	private final double circleY;
	private double angularVelocity;
	private double currentAngle;

	static {
		periodProperty = new SimpleLongProperty(5_000);
		ratioProperty = new SimpleDoubleProperty(0.1);
		lifeTimeProperty = new SimpleLongProperty(20_000);

		texture = new Texture(new Image(Objects.requireNonNull(Developer.class
				.getResourceAsStream("/edu/uni/lab/images/manager.png")),
				MANAGER_WIDTH, MANAGER_HEIGHT, false, true),
				MANAGER_WIDTH, MANAGER_HEIGHT);
	}

	public Manager(double x, double y, long creationTime,
				   double habitatAreaWidth, double habitatAreaHeight) {
		super(x, y, creationTime, habitatAreaWidth, habitatAreaHeight);
		imageView = new ImageView(texture.getImage());
		imageView.setX(x);
		imageView.setY(y);

		trajectoryRadius = ThreadLocalRandom.current().nextDouble(minRadius, maxRadius);
		double direction = ThreadLocalRandom.current().nextBoolean() ? 1.0 : -1.0;

		angularVelocity = direction * ThreadLocalRandom.current()
				.nextDouble(0.1 * maxLinearVelocity, maxLinearVelocity) / trajectoryRadius;
		double vecX = ThreadLocalRandom.current().nextDouble(-trajectoryRadius, trajectoryRadius);
		double vecY = sqrt(trajectoryRadius*trajectoryRadius - vecX * vecX);

		circleX = vecX + x;
		circleY = vecY + y;
		currentAngle = acos(vecX/trajectoryRadius) + PI;
	}

	@Override
	public void move() {
		currentAngle += angularVelocity;
		double newX = circleX + trajectoryRadius * cos(currentAngle);
		double newY = circleY + trajectoryRadius * sin(currentAngle);
		if (newX < 0 || newX > habitatAreaWidth - getTexture().getWidth()
				|| newY < 0 || newY > habitatAreaHeight - getTexture().getHeight()) {
			angularVelocity = -angularVelocity;
			currentAngle += angularVelocity * 2;
			setX(circleX + trajectoryRadius * cos(currentAngle));
			setY(circleY + trajectoryRadius * sin(currentAngle));
		} else {
			setX(newX);
			setY(newY);
		}
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

	public static double getRatio() {
		return ratioProperty.getValue();
	}

	public static SimpleDoubleProperty ratio() {
		return ratioProperty;
	}

	public static void setRatio(double probability) {
		ratioProperty.setValue(probability);
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