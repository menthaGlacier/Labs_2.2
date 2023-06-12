package edu.uni.lab.utility.dto;

import edu.uni.lab.model.employees.Developer;

import java.util.UUID;

public class DeveloperDto extends EmployeeDto {
	private long trajectoryChangePeriod;
	private long lastTrajectoryChange;
	private double velocityX;
	private double velocityY;

	public DeveloperDto(Developer developer) {
		super(developer);
		trajectoryChangePeriod = developer.getTrajectoryChangePeriod();
		lastTrajectoryChange = developer.getLastTrajectoryChange();
		velocityX = developer.getVelocityX();
		velocityY = developer.getVelocityY();
	}

	public double getVelocityX() {
		return velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public long getLastTrajectoryChange() {
		return lastTrajectoryChange;
	}

	public long getTrajectoryChangePeriod() {
		return trajectoryChangePeriod;
	}
}
