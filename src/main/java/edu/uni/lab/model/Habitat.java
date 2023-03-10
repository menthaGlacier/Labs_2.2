package edu.uni.lab.model;

import java.util.Random;
import javafx.scene.layout.Pane;

public class Habitat {
	private static final int ARR_LIMIT = 100;
	private static final int DEVELOPER_PERIOD_MIN = 1000;
	private static final int DEVELOPER_PERIOD_MAX = 4000;
	private static final int MANAGER_PERIOD_MIN = 500;
	private static final int MANAGER_PERIOD_MAX = 3000;
	private static final double DEVELOPER_PROBABILITY_MIN = 0.5;
	private static final double DEVELOPER_PROBABILITY_MAX = 0.7;
	private static final double MANAGER_RATIO_MIN = 0.4;
	private static final double MANAGER_RATIO_MAX = 0.9;

	private final Pane habitatArea;

	private final Employee[] employees;
	private int developersCounter = 0;
	private int managersCounter = 0;
	private long lastDeveloperGenerationTime = 0;
	private long lastManagerGenerationTime = 0;

	private final Random random = new Random();

	public Habitat(Pane habitatArea) {
		this.habitatArea = habitatArea;

		Developer.setPeriod(random
				.nextInt(DEVELOPER_PERIOD_MAX - DEVELOPER_PERIOD_MIN + 1)
				+ DEVELOPER_PERIOD_MIN);
		Manager.setPeriod(random
				.nextInt(MANAGER_PERIOD_MAX - MANAGER_PERIOD_MIN + 1)
				+ MANAGER_PERIOD_MIN);
		Developer.setProbability(random
				.nextDouble(DEVELOPER_PROBABILITY_MAX - DEVELOPER_PROBABILITY_MIN)
				+ DEVELOPER_PROBABILITY_MIN);
		Manager.setRatio(random
				.nextDouble(MANAGER_RATIO_MAX - MANAGER_RATIO_MIN)
				+ MANAGER_RATIO_MIN);

		System.out.println("Debug:");
		System.out.println("Developer period: " + Developer.getPeriod());
		System.out.println("Developer probability: " + Developer.getProbability());
		System.out.println("Manager period: " + Manager.getPeriod());
		System.out.println("Manager ratio: " + Manager.getRatio());

		employees = new Employee[ARR_LIMIT];
	}

	public void update(long elapsedTime) {
		if ((developersCounter + managersCounter) >= ARR_LIMIT) {
			return;
		}

		if (elapsedTime - lastDeveloperGenerationTime >= Developer.getPeriod()
				&& random.nextDouble() <= Developer.getProbability()) {
			addEmployee(new Developer(
					random.nextDouble() * (habitatArea.getWidth()
							- Developer.getTexture().getWidth()),
					random.nextDouble() * (habitatArea.getHeight()
							- Developer.getTexture().getHeight())));
			lastDeveloperGenerationTime = elapsedTime;
		}

		if (elapsedTime - lastManagerGenerationTime >= Manager.getPeriod()
				&& managersCounter <= developersCounter * Manager.getRatio()) {
			addEmployee(new Manager(
					random.nextDouble() * (habitatArea.getWidth()
							- Manager.getTexture().getWidth()),
					random.nextDouble() * (habitatArea.getHeight()
							- Manager.getTexture().getHeight())));
			lastManagerGenerationTime = elapsedTime;
		}
	}

	private void addEmployee(Employee employee) {
		if ((developersCounter + managersCounter) >= ARR_LIMIT) {
			return;
		}

		employees[developersCounter + managersCounter] = employee;
		habitatArea.getChildren().add(employee.getImageView());
		if (employee instanceof Developer) {
			developersCounter += 1;
		} else if (employee instanceof Manager) {
			managersCounter += 1;
		}
	}

	public int getDevelopersCounter() {
		return developersCounter;
	}

	public int getManagersCounter() {
		return managersCounter;
	}
}