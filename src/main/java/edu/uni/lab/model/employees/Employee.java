package edu.uni.lab.model.employees;

import edu.uni.lab.model.IBehaviour;
import edu.uni.lab.utility.dto.EmployeeDto;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.UUID;

public abstract class Employee implements IBehaviour, Serializable {
	protected final double habitatAreaWidth;
	protected final double habitatAreaHeight;
	protected transient ImageView imageView;
	protected double x, y;
	private final long creationTime;
	private final UUID id;

	public Employee(double x, double y, long creationTime,
					double habitatAreaWidth, double habitatAreaHeight) {
		this.x = x;
		this.y = y;
		this.creationTime = creationTime;
		this.habitatAreaWidth = habitatAreaWidth;
		this.habitatAreaHeight = habitatAreaHeight;
		this.id = UUID.randomUUID();
	}

	public Employee(EmployeeDto employeeDto, double habitatAreaWidth, double habitatAreaHeight) {
		x = employeeDto.getX();
		y = employeeDto.getY();
		creationTime = employeeDto.getCreationTime();
		id = employeeDto.getId();
		this.habitatAreaWidth = habitatAreaWidth;
		this.habitatAreaHeight = habitatAreaHeight;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public abstract void resetImageView();

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
		synchronized (imageView) {
			imageView.setX(x);
		}
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		synchronized (imageView) {
			imageView.setY(y);
		}
	}

	public EmployeeDto createDto() {
		return new EmployeeDto(this);
	}
}