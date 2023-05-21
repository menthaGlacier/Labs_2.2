package edu.uni.lab.model.ai;

import edu.uni.lab.model.employees.Developer;
import edu.uni.lab.model.employees.Employee;

public class DeveloperAi extends BaseAi {
	public DeveloperAi() {
		super();
	}

	@Override
	protected void update() {
		synchronized (employees.employeesList()) {
			for (Employee iterator : employees.employeesList()) {
				if (iterator instanceof Developer) {
					iterator.move();
				}
			}
		}
	}
}