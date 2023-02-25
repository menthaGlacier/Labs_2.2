package edu.uni.lab.model;

import java.util.Random;
import edu.uni.lab.model.Employee;

public class Habitat {
	private static final int OBJECTS_LIMIT = 100;
	private Employee[] employees;
	private int devCounter, managerCounter;
	private int width, height;
	private boolean active;
	private Random random = new Random();

	public Habitat(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void debugStatistics() {
		System.out.println("Num of devs: " + devCounter);
		System.out.println("Num of managers: " + managerCounter);
	}

	public void startSimulation() {
		employees = new Employee[OBJECTS_LIMIT];
		devCounter = 0;
		managerCounter = 0;
		active = true;

		Developer.setPeriod(random.nextLong(28000) + 2000);
		Developer.setProbability(random.nextDouble() * 0.9 + 0.1);
		Manager.setPeriod(random.nextLong(28000) + 2000);
		Manager.setRatio(random.nextDouble() * 0.9 + 0.1);
	}

	public void stopSimulation () {
		active = false;
	}

	public void update (long elapsedTime) {
		if (elapsedTime % (Developer.getPeriod() / 1000) == 0
				&& random.nextDouble() <= Developer.getProbability()) {
			addEmployee(new Developer(0,0)); // Tentative coordinates
		}

		if (elapsedTime % (Manager.getPeriod() / 1000) == 0
				&& managerCounter <= (devCounter * Manager.getRatio())) {
			addEmployee(new Manager(0, 0)); // Tentative coordinates
		}
	}

	public boolean isActive() {
		return active;
	}

	private void addEmployee(Employee employee) {
		if ((devCounter + managerCounter) >= OBJECTS_LIMIT ) {
			/* Display "1000 (tentative) krai brat" funny image" */
			stopSimulation();
			return;
		}

		employees[devCounter + managerCounter] = employee;
		if (employee instanceof Developer) {
			devCounter += 1;
		} else if (employee instanceof Manager) {
			managerCounter += 1;
		}
	}
}