package edu.uni.lab.utility.dto;

import java.io.Serializable;

public class EmployeesRequestDto implements Serializable {
	private int toClientId;
	private String employeeClass;

	public  EmployeesRequestDto(String employeeClass, int toClientId) {
		this.employeeClass = employeeClass;
		this.toClientId = toClientId;
	}

	public String getEmployeeClass() { return employeeClass; }
}
