package edu.uni.lab.model.ai;

import edu.uni.lab.model.employees.Manager;
import edu.uni.lab.model.employees.Employee;

public class ManagerAi extends BaseAi {
	public ManagerAi() {
		super();
	}

	@Override
	protected void update() {
		synchronized (employees.employeesList()) {
			for (Employee iterator : employees.employeesList()) {
				if (iterator instanceof Manager) {
					iterator.move();
				}
			}
		}
	}
}
