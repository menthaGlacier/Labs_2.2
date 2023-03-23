package edu.uni.lab.model;

public class EmployeeRepository {
	private static final EmployeeRepository instance = new EmployeeRepository();
	private Employee[] employees;

	private EmployeeRepository() {
		employees = new Employee[0];
	}

	public static EmployeeRepository getInstance() {
		return instance;
	}

	public void add(Employee employee, int index) {
		if (index >= employees.length) {
			return;
		}

		employees[index] = employee;
	}

	public int size() {
		return employees.length;
	}

	public void resize(int size) {
		if (size < 0) {
			size = 0;
		}

		employees = new Employee[size];
	}
}