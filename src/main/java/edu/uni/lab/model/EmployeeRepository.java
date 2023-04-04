package edu.uni.lab.model;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.UUID;

public class EmployeeRepository {
	private static final EmployeeRepository instance = new EmployeeRepository();
	private final LinkedList<Employee> employees;
	private final TreeSet<UUID> employeesID;
	private final HashMap<UUID, Long> employeesCreationTime;

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

	public TreeSet<UUID> getEmployeesID() {
		return employeesID;
	}

	public HashMap<UUID, Long> getEmployeesCreationTime() {
		return employeesCreationTime;
	}

	public void add(Employee employee) {
		employees.add(employee);
		employeesID.add(employee.getId());
		employeesCreationTime.put(employee.getId(), employee.getCreationTime());
	}

	public void remove(Employee employee) {
		employees.remove(employee);
		employeesID.remove(employee.getId());
		employeesCreationTime.remove(employee.getId());
	}

	public int size() {
		return employees.size();
	}

	public void clear() {
		employees.clear();
	}
}