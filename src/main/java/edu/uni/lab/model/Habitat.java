package edu.uni.lab.model;

import edu.uni.lab.model.Developer;
import edu.uni.lab.model.Manager;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Habitat {
	private Employee[] employees;
	private int devsCounter, managersCounter;
	private int width, height;
	private boolean isActive;

	public Habitat(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void startSimulation() {
		employees = new Employee[100];
		devsCounter = 0;
		managersCounter = 0;
		isActive = true;

		Random random = new Random();
		Developer.setPeriod(random.nextInt(120) + 13);
		Developer.setProbability(random.nextDouble() * 0.9 + 0.1);
		Manager.setPeriod(random.nextInt(120) + 13);
		Manager.setRatio(random.nextDouble() * 0.9 + 0.1);
	}

	public void stopSimulation () {
		isActive = false;
		/* Display "1000 (tentative) krai brat" funny image" */
	}

	public void update (long elapsedTime) {
		//
	}
}