package edu.uni.lab.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Employee {
	protected static long period; // In milliseconds
	//protected static Image image;
	//protected ImageView imageView;
	protected double x, y;

	public Employee(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public static long getPeriod() {
		return period;
	}

	public static void setPeriod(long period) {
		Employee.period = period;
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