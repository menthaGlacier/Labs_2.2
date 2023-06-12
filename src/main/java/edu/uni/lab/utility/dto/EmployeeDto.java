package edu.uni.lab.utility.dto;

import edu.uni.lab.model.employees.Employee;

import java.io.Serializable;
import java.util.UUID;

public class EmployeeDto implements Serializable {
	private double x, y;
	private long creationTime;
	private UUID id;

	public EmployeeDto(Employee employee) {
		x = employee.getX();
		y = employee.getY();
		creationTime = employee.getCreationTime();
		id = employee.getId();
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public UUID getId() {
		return id;
	}

}
