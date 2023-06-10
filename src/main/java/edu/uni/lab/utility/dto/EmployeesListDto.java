package edu.uni.lab.utility.dto;

import edu.uni.lab.model.employees.Employee;

import java.io.Serializable;
import java.util.LinkedList;

public class EmployeesListDto implements Serializable {
	private LinkedList<Employee> list;
	private int toClientId;

	public EmployeesListDto(LinkedList<Employee> list, int toClientId) {
		this.toClientId = toClientId;
		this.list = list;
	}

	public LinkedList<Employee> employeesList() {
		return list;
	}

	public int getToClientId() { return toClientId; }
}
