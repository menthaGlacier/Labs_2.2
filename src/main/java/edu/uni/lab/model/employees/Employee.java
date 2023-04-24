package edu.uni.lab.model.employees;

import javafx.scene.image.ImageView;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Employee {
	protected ImageView imageView;
	private final long creationTime;
	private final UUID id;
	private double velocity;
	private double x;
	private double y;

	public Employee(double x, double y, long creationTime) {
		this.x = x;
		this.y = y;
		this.creationTime = creationTime;
		this.id = UUID.randomUUID();
		this.velocity = ThreadLocalRandom.current().nextDouble();
	}

	public ImageView getImageView() {
		return imageView;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public UUID getId() {
		return id;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
		imageView.setX(x);
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		imageView.setY(y);
	}
}