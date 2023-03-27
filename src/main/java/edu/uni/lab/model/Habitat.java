package edu.uni.lab.model;

import java.util.Random;

import javafx.scene.layout.Pane;

public class Habitat {
	public static final int PERIOD_DEFAULT = 5_000;
	public static final int PERIOD_MAX = 1_000_000;
	public static final int LIFETIME_DEFAULT = 20_000;
	public static final int LIFETIME_MAX = 1_000_000;

	private final Pane habitatArea;
	private final EmployeeRepository employeesRepository;
	private int developersCounter = 0;
	private int managersCounter = 0;
	private long lastDeveloperGenerationTime = 0;
	private long lastManagerGenerationTime = 0;

	private final Random random = new Random();

	public Habitat(Pane habitatArea) {
		this.habitatArea = habitatArea;
		employeesRepository = EmployeeRepository.getInstance();
	}

	public void update(long elapsedTime) {
		for (Employee iterator : employeesRepository.getEmployeesList()) {
			long lifeTime = 0;
			if (iterator instanceof Developer) {
				lifeTime = Developer.getLifeTime();
			} else if (iterator instanceof Manager) {
				lifeTime = Manager.getLifeTime();
			}

			if (elapsedTime - iterator.getCreationTime() >= lifeTime) {
				removeEmployee(iterator);
			}
		}

		if (elapsedTime - lastDeveloperGenerationTime >= Developer.getPeriod()
				&& random.nextDouble() <= Developer.getProbability()) {
			addEmployee(new Developer(
					random.nextDouble() * (habitatArea.getWidth()
							- Developer.getTexture().getWidth()),
					random.nextDouble() * (habitatArea.getHeight()
							- Developer.getTexture().getHeight()),
					elapsedTime));

			lastDeveloperGenerationTime = elapsedTime;
		}

		if (elapsedTime - lastManagerGenerationTime >= Manager.getPeriod()
				&& managersCounter <= developersCounter * Manager.getRatio()) {
			addEmployee(new Manager(
					random.nextDouble() * (habitatArea.getWidth()
							- Manager.getTexture().getWidth()),
					random.nextDouble() * (habitatArea.getHeight()
							- Manager.getTexture().getHeight()),
					elapsedTime));

			lastManagerGenerationTime = elapsedTime;
		}
	}

	private void addEmployee(Employee employee) {
		employeesRepository.add(employee);
		habitatArea.getChildren().add(employee.getImageView());
		if (employee instanceof Developer) {
			developersCounter += 1;
		} else if (employee instanceof Manager) {
			managersCounter += 1;
		}
	}

	private void removeEmployee(Employee employee) {
		employeesRepository.remove(employee);
		habitatArea.getChildren().remove(employee.getImageView());
		if (employee instanceof Developer) {
			developersCounter -= 1;
		} else if (employee instanceof Manager) {
			managersCounter -= 1;
		}
	}
	public int getDevelopersCounter() {
		return developersCounter;
	}

	public int getManagersCounter() {
		return managersCounter;
	}
}