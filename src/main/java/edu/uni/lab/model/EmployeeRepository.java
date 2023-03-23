package edu.uni.lab.model;

import java.util.LinkedList;

public class EmployeeRepository {
	private static final EmployeeRepository instance = new EmployeeRepository();
	private final LinkedList<Employee> employees;

	private EmployeeRepository() {
		employees = new LinkedList<>();
	}

	public static EmployeeRepository getInstance() {
		return instance;
	}

	public void add(Employee employee) {
		employees.add(employee);
	}

	public int size() {
		return employees.size();
	}

	public void clear() {
		employees.clear();
	}
}