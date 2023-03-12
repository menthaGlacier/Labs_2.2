package edu.uni.lab.model;

public class EmployeeRepository {
	private static EmployeeRepository instance = new EmployeeRepository();
	private Employee[] employees;
	private int size;

	private EmployeeRepository() {
		employees = new Employee[0];
		size = 0;
	}

	public static EmployeeRepository getInstance() {
		return instance;
	}

	public void add(Employee employee, int index) {
		if (index >= size) {
			return;
		}

		employees[index] = employee;
	}

	public int size() {
		return size;
	}

	public void resize(int size) {
		this.size = size;
		employees = new Employee[this.size];
	}
}