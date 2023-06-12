package edu.uni.lab.utility.dto;

import edu.uni.lab.model.employees.Employee;

import java.io.Serializable;
import java.util.UUID;

public class EmployeeDto implements Serializable {
	private double x, y;

	public EmployeeDto(Employee employee) {
		x = employee.getX();
		y = employee.getY();
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
