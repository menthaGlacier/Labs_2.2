package edu.uni.lab.model;

import javafx.scene.image.ImageView;

public abstract class Employee implements IBehaviour {
	protected ImageView imageView;
	private double x, y;

	public Employee(double x, double y) {
		this.x = x;
		this.y = y;
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

	public ImageView getImageView() {
		return imageView;
	}
}