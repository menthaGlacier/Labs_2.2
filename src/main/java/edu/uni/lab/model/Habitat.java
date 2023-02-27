package edu.uni.lab.model;

import java.util.Random;

import javafx.scene.layout.Pane;

public class Habitat {
	private static final int ARR_LIMIT = 100;
	private Employee[] employees;
	private int devCounter = 0, mgrCounter = 0;
	private int width, height;
	private Random random = new Random();

	private Pane habitatArea;


	public Habitat(int width, int height, Pane habitatArea) {
		this.width = width;
		this.height = height;
		this.habitatArea = habitatArea;
		employees = new Employee[ARR_LIMIT];

	}

	public void update (long elapsedTime) {

		System.out.format("devs:%d mgrs:%d\n", devCounter, mgrCounter);
		if (elapsedTime / Developer.getPeriod() >= devCounter
				&& random.nextDouble() <= Developer.getProbability()) {
			addEmployee(new Developer(random.nextDouble() * habitatArea.getWidth(), random.nextDouble() * habitatArea.getHeight())); // Tentative coordinates
		}

		if (elapsedTime / Manager.getPeriod() >= mgrCounter
				&& mgrCounter <= (devCounter * Manager.getRatio())) {
			addEmployee(new Manager(random.nextDouble() * habitatArea.getWidth(), random.nextDouble() * habitatArea.getHeight()));
		}
	}

	private boolean addEmployee(Employee employee) {
		if ((devCounter + mgrCounter) >= ARR_LIMIT) {
			/* Display "1000 (tentative) krai brat" funny image" */
			return false;
		}

		employees[devCounter + mgrCounter] = employee;
		habitatArea.getChildren().add(employee.getImageView());
		if (employee instanceof Developer) {
			devCounter += 1;
		} else if (employee instanceof Manager) {
			mgrCounter += 1;
		}

		return true;
	}

}