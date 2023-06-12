package edu.uni.lab.utility.dto;

import edu.uni.lab.model.employees.Manager;

public class ManagerDto extends EmployeeDto {
	private double trajectoryRadius;
	private double circleX;
	private double circleY;
	private double angularVelocity;
	private double currentAngle;

	public ManagerDto(Manager manager) {
		super(manager);
		trajectoryRadius = manager.getTrajectoryRadius();
		circleX = manager.getCircleX();
		circleY = manager.getCircleY();
		angularVelocity = manager.getAngularVelocity();
		currentAngle = manager.getCurrentAngle();
	}

	public double getTrajectoryRadius() {
		return trajectoryRadius;
	}

	public double getAngularVelocity() {
		return angularVelocity;
	}

	public double getCircleX() {
		return circleX;
	}

	public double getCircleY() {
		return circleY;
	}

	public double getCurrentAngle() {
		return currentAngle;
	}
}
