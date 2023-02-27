package edu.uni.lab.model;

import java.util.Random;

import edu.uni.lab.textureloader.TextureLoader;
import javafx.scene.layout.Pane;

public class Habitat {
	private static final int ARR_LIMIT = 100;
	private Employee[] employees;
	private int devCounter = 0, mgrCounter = 0;

	private double devBorderX, devBorderY, mgrBorderX, mgrBorderY;

	private Random random = new Random();

	private Pane habitatArea;

	public Habitat(Pane habitatArea) {
		devBorderX = habitatArea.getWidth() - TextureLoader.getDevTextureWidth();
		devBorderY = habitatArea.getHeight() - TextureLoader.getDevTextureHeight();
		mgrBorderX = habitatArea.getWidth() - TextureLoader.getMgrTextureWidth();
		mgrBorderY = habitatArea.getHeight() - TextureLoader.getMgrTextureHeight();

		this.habitatArea = habitatArea;
		employees = new Employee[ARR_LIMIT];
	}

	public void update (long elapsedTime) {

		if ((devCounter + mgrCounter) < ARR_LIMIT) {
			if (elapsedTime / Developer.getPeriod() >= devCounter
					&& random.nextDouble() <= Developer.getProbability()) {
				addEmployee(new Developer(randCoord(devBorderX), randCoord(devBorderY)));
			}

			if (elapsedTime / Manager.getPeriod() >= mgrCounter
					&& mgrCounter <= (devCounter * Manager.getRatio())) {
				addEmployee(new Manager(randCoord(mgrBorderX), randCoord(mgrBorderY)));
			}
		}
	}

	private double randCoord(double border) {
		return random.nextDouble() * border;
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