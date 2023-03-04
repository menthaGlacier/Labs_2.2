package edu.uni.lab.model;

import java.util.Random;
import javafx.scene.layout.Pane;

public class Habitat {
	private static final int ARR_LIMIT = 100;
	private static final int DEVELOPER_PERIOD_MIN = 3000;
	private static final int DEVELOPER_PERIOD_MAX = 12000;
	private static final int MANAGER_PERIOD_MIN = 1000;
	private static final int MANAGER_PERIOD_MAX = 9000;
	private static final double DEVELOPER_PROBABILITY_MIN = 0.3;
	private static final double DEVELOPER_PROBABILITY_MAX = 0.9;
	private static final double MANAGER_RATIO_MIN = 0.2;
	private static final double MANAGER_RATIO_MAX = 0.7;

	private final Pane habitatArea;
	private final int width;
	private final int height;
	private final Employee[] employees;
	private int developersCounter = 0;
	private int managersCounter = 0;
	private long lastDeveloperGenerationTime = 0;
	private long lastManagerGenerationTime = 0;

	private final Random random = new Random();

	public Habitat(Pane habitatArea, int width, int height) {
		this.habitatArea = habitatArea;
		this.width = width;
		this.height = height;

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