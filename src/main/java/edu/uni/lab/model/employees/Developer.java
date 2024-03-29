package edu.uni.lab.model.employees;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import edu.uni.lab.utility.dto.DeveloperDto;
import edu.uni.lab.utility.dto.EmployeeDto;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import edu.uni.lab.utility.Texture;

public class Developer extends Employee {
	private final static Texture texture;
	private final static int DEVELOPER_WIDTH = 80;
	private final static int DEVELOPER_HEIGHT = 80;
	private final static long maxTrajectoryChangePeriod = 5000;
	private final static long minTrajectoryChangePeriod = 100;
	private final static double maxVelocity = 10.0;
	private final static SimpleLongProperty periodProperty;
	private final static SimpleDoubleProperty probabilityProperty;
	private final static SimpleLongProperty lifeTimeProperty;
	private final long trajectoryChangePeriod;
	private long lastTrajectoryChange = getCreationTime();
	private double velocityX;
	private double velocityY;

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
		trajectoryChangePeriod = ThreadLocalRandom.current()
				.nextLong(minTrajectoryChangePeriod, maxTrajectoryChangePeriod);
	}

	public Developer(DeveloperDto developerDto, long creationTime, double habitatAreaWidth, double habitatAreaHeight) {
		super(developerDto, creationTime, habitatAreaWidth, habitatAreaHeight);
		velocityX = developerDto.getVelocityX();
		velocityY = developerDto.getVelocityY();
		trajectoryChangePeriod = developerDto.getTrajectoryChangePeriod();
		lastTrajectoryChange = developerDto.getLastTrajectoryChange();
		imageView = new ImageView(texture.getImage());
		imageView.setX(x);
		imageView.setY(y);
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
			velocityX = -velocityX;
			setX(velocityX - getX());
		} else if (getX() + velocityX > habitatAreaWidth - getTexture().getWidth()) {
			velocityX = -velocityX;
			double border = habitatAreaWidth - getTexture().getWidth();
			setX(border + velocityX + border - getX());
		} else {
			setX(getX() + velocityX);
		}

		if (getY() + velocityY < 0.0) {
			velocityY = -velocityY;
			setY(velocityY - getY());
		} else if (getY() + velocityY > habitatAreaHeight - getTexture().getHeight()) {
			velocityY = -velocityY;
			double border = habitatAreaHeight - getTexture().getHeight();
			setY(border + velocityY + border - getY());
		} else {
			setY(getY() + velocityY);
		}
	}

	private double getRandomVelocity() {
		return ThreadLocalRandom.current().nextDouble(-maxVelocity, maxVelocity);
	}

	public void resetImageView() {
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

	public long getTrajectoryChangePeriod() {
		return trajectoryChangePeriod;
	}

	public long getLastTrajectoryChange() {
		return lastTrajectoryChange;
	}

	public double getVelocityX() {
		return velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	@Override
	public EmployeeDto createDto() {
		return new DeveloperDto(this);
	}
}