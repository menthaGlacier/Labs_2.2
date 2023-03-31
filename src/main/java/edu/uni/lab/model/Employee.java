package edu.uni.lab.model;

import javafx.scene.image.ImageView;

public abstract class Employee implements IBehaviour {
	protected ImageView imageView;
	private final long creationTime;
	private double x;
	private double y;

	public Employee(double x, double y, long creationTime) {
		this.x = x;
		this.y = y;
		this.creationTime = creationTime;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}