package edu.uni.lab.model;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.TreeSet;

public class EmployeeRepository {
	private static final EmployeeRepository instance = new EmployeeRepository();
	private final LinkedList<Employee> employees;
	private final TreeSet<Integer> employeesID;
	private final HashMap<Integer, Long> employeesCreationTime;

	private EmployeeRepository() {
		employees = new LinkedList<>();
		employeesID = new TreeSet<>();
		employeesCreationTime = new HashMap<>();
	}

	public static EmployeeRepository getInstance() {
		return instance;
	}

	public LinkedList<Employee> getEmployeesList() {
		return employees;
	}

	public void add(Employee employee) {
		employees.add(employee);
		employeesID.add(employee.hashCode());
		employeesCreationTime.put(employee.hashCode(), employee.getCreationTime());
	}

	public void remove(Employee employee) {
		employees.remove(employee);
		employeesID.remove(employee.hashCode());
		employeesCreationTime.remove(employee.hashCode());

		System.out.println(employeesID);
	}

	public int size() {
		return employees.size();
	}

	public void clear() {
		employees.clear();
	}
}