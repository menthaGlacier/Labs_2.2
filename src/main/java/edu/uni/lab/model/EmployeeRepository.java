package edu.uni.lab.model;

import edu.uni.lab.model.employees.Employee;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.UUID;

public class EmployeeRepository {
	private static final EmployeeRepository instance = new EmployeeRepository();
	private final LinkedList<Employee> employees;
	private final TreeSet<UUID> employeesId;
	private final HashMap<UUID, Long> employeesCreationTime;

	private EmployeeRepository() {
		employees = new LinkedList<>();
		employeesId = new TreeSet<>();
		employeesCreationTime = new HashMap<>();
	}

	public static EmployeeRepository getInstance() {
		return instance;
	}

	public LinkedList<Employee> employeesList() {
		return employees;
	}

	public TreeSet<UUID> employeesId() {
		return employeesId;
	}

	public HashMap<UUID, Long> employeesCreationTime() {
		return employeesCreationTime;
	}

	public synchronized void add(Employee employee) {
		employees.add(employee);
		employeesId.add(employee.getId());
		employeesCreationTime.put(employee.getId(), employee.getCreationTime());
	}

	public synchronized void remove(Employee employee) {
		employees.remove(employee);
		employeesId.remove(employee.getId());
		employeesCreationTime.remove(employee.getId());
	}

	public int size() {
		return employees.size();
	}

	public synchronized void clear() {
		employees.clear();
		employeesId.clear();
		employeesCreationTime.clear();
	}
}