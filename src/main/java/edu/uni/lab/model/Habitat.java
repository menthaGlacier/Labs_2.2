package edu.uni.lab.model;

import java.util.Random;

import edu.uni.lab.model.employees.Developer;
import edu.uni.lab.model.employees.Employee;
import edu.uni.lab.model.employees.Manager;
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
	private long lastDeveloperGeneration = 0;
	private long lastDeveloperGenerationTry = 0;
	private long lastManagerGeneration = 0;
	private long lastManagerGenerationTry = 0;

	private final Random random = new Random();

	public Habitat(Pane habitatArea) {
		this.habitatArea = habitatArea;
		employeesRepository = EmployeeRepository.getInstance();
	}

	public void update(long elapsedTime) {
		synchronized (employeesRepository.getEmployeesList()) {
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
		}

		if (elapsedTime - lastDeveloperGenerationTry >= Developer.getPeriod()) {
			lastDeveloperGenerationTry = elapsedTime;
			if (elapsedTime - lastDeveloperGeneration >= Developer.getPeriod()
					&& random.nextDouble() <= Developer.getProbability()) {
				addEmployee(new Developer(
						random.nextDouble() * (habitatArea.getWidth()
								- Developer.getTexture().getWidth()),
						random.nextDouble() * (habitatArea.getHeight()
								- Developer.getTexture().getHeight()),
						elapsedTime)
				);

				lastDeveloperGeneration = elapsedTime;
			}
		}

		if (elapsedTime - lastManagerGenerationTry >= Manager.getPeriod()) {
			lastManagerGenerationTry = elapsedTime;
			if (elapsedTime - lastManagerGeneration >= Manager.getPeriod()
					&& managersCounter <= developersCounter * Manager.getRatio()) {
				addEmployee(new Manager(
						random.nextDouble() * (habitatArea.getWidth()
								- Manager.getTexture().getWidth()),
						random.nextDouble() * (habitatArea.getHeight()
								- Manager.getTexture().getHeight()),
						elapsedTime));

				lastManagerGeneration = elapsedTime;
			}
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
		habitatArea.getChildren().remove(employee.getImageView());
		employeesRepository.remove(employee);
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