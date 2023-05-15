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
	private final EmployeeRepository employees;
	private int developersCounter = 0;
	private int managersCounter = 0;
	private long lastDeveloperGeneration = 0;
	private long lastDeveloperGenerationTry = 0;
	private long lastManagerGeneration = 0;
	private long lastManagerGenerationTry = 0;

	private final Random random = new Random();

	public Habitat(Pane habitatArea) {
		this.habitatArea = habitatArea;
		employees = EmployeeRepository.getInstance();
	}

	public void update(long elapsedTime) {
		synchronized (employees.employeesList()) {
			for (int i = 0; i < employees.size();) {
				Employee iteratingEmployee = employees.employeesList().get(i);
				long lifeTime = 0;
				if (iteratingEmployee instanceof Developer) {
					lifeTime = Developer.getLifeTime();
				} else if (iteratingEmployee instanceof Manager) {
					lifeTime = Manager.getLifeTime();
				}

				if (elapsedTime - iteratingEmployee.getCreationTime() >= lifeTime) {
					removeEmployee(iteratingEmployee);
					continue;
				}
				++i;
			}

			final double habitatAreaWidth = habitatArea.getWidth(),
					habitatAreaHeight = habitatArea.getHeight();

			if (elapsedTime - lastDeveloperGenerationTry >= Developer.getPeriod()) {
				lastDeveloperGenerationTry = elapsedTime;
				if (elapsedTime - lastDeveloperGeneration >= Developer.getPeriod()
						&& random.nextDouble() <= Developer.getProbability()) {
					addEmployee(new Developer(
							random.nextDouble() * (habitatAreaWidth
									- Developer.getTexture().getWidth()),
							random.nextDouble() * (habitatAreaHeight
									- Developer.getTexture().getHeight()),
							elapsedTime, habitatAreaWidth, habitatAreaHeight)
					);

					lastDeveloperGeneration = elapsedTime;
				}
			}

			if (elapsedTime - lastManagerGenerationTry >= Manager.getPeriod()) {
				lastManagerGenerationTry = elapsedTime;
				if (elapsedTime - lastManagerGeneration >= Manager.getPeriod()
						&& managersCounter <= developersCounter * Manager.getRatio()) {
					addEmployee(new Manager(
							random.nextDouble(0, habitatAreaWidth
									- Manager.getTexture().getWidth()),
							random.nextDouble(0,habitatAreaHeight
									- Manager.getTexture().getHeight()),
							elapsedTime, habitatAreaWidth, habitatAreaHeight));

					lastManagerGeneration = elapsedTime;
				}
			}
		}
	}

	private void addEmployee(Employee employee) {
		employees.add(employee);
		synchronized (habitatArea) {
			habitatArea.getChildren().add(employee.getImageView());
		}
		if (employee instanceof Developer) {
			developersCounter += 1;
		} else if (employee instanceof Manager) {
			managersCounter += 1;
		}
	}

	private void removeEmployee(Employee employee) {
		employees.remove(employee);
		synchronized (habitatArea) {
			habitatArea.getChildren().remove(employee.getImageView());
		}
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