package edu.uni.lab.model.employees;

import edu.uni.lab.model.IBehaviour;
import javafx.scene.image.ImageView;

import java.util.UUID;

public abstract class Employee implements IBehaviour {
	protected ImageView imageView;
	private final long creationTime;
	private final UUID id;
	private double x, y;

	public Employee(double x, double y, long creationTime) {
		this.x = x;
		this.y = y;
		this.creationTime = creationTime;
		this.id = UUID.randomUUID();
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