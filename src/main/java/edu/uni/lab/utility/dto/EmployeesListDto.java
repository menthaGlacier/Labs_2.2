package edu.uni.lab.utility.dto;

import java.io.Serializable;
import java.util.LinkedList;

public class EmployeesListDto implements Serializable {
	private LinkedList<EmployeeDto> list;
	private int toClientId;

	public EmployeesListDto(LinkedList<EmployeeDto> list, int toClientId) {
		this.toClientId = toClientId;
		this.list = list;
	}

	public LinkedList<EmployeeDto> employeesDtoList() {
		return list;
	}

	public int getToClientId() { return toClientId; }
}