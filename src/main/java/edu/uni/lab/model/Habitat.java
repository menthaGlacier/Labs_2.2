package edu.uni.lab.model;

import java.util.concurrent.ThreadLocalRandom;

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
	public final double habitatAreaWidth;
	public final double habitatAreaHeight;
	private final EmployeeRepository employees;
	private int developersCounter = 0;
	private int managersCounter = 0;
	private long lastDeveloperGeneration = 0;
	private long lastDeveloperGenerationTry = 0;
	private long lastManagerGeneration = 0;
	private long lastManagerGenerationTry = 0;

	public Habitat(Pane habitatArea) {
		this.habitatArea = habitatArea;
		this.habitatAreaWidth = habitatArea.getWidth();
		this.habitatAreaHeight = habitatArea.getHeight();
		this.employees = EmployeeRepository.getInstance();
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

			if (elapsedTime - lastDeveloperGenerationTry >= Developer.getPeriod()) {
				lastDeveloperGenerationTry = elapsedTime;
				if (elapsedTime - lastDeveloperGeneration >= Developer.getPeriod()
						&& ThreadLocalRandom.current()
								.nextDouble() <= Developer.getProbability()) {
					addEmployee(new Developer(
							ThreadLocalRandom.current()
									.nextDouble(0.0, habitatAreaWidth
											- Developer.getTexture().getWidth()),
							ThreadLocalRandom.current()
									.nextDouble(0.0, habitatAreaHeight
											- Developer.getTexture().getHeight()),
							elapsedTime,
							habitatAreaWidth,
							habitatAreaHeight)
					);

					lastDeveloperGeneration = elapsedTime;
				}
			}

			if (elapsedTime - lastManagerGenerationTry >= Manager.getPeriod()) {
				lastManagerGenerationTry = elapsedTime;
				if (elapsedTime - lastManagerGeneration >= Manager.getPeriod()
						&& managersCounter <= developersCounter * Manager.getRatio()) {
					addEmployee(new Manager(
							ThreadLocalRandom.current()
									.nextDouble(0.0, habitatAreaWidth
											- Manager.getTexture().getWidth()),
							ThreadLocalRandom.current()
									.nextDouble(0.0, habitatAreaHeight
											- Manager.getTexture().getHeight()),
							elapsedTime,
							habitatAreaWidth,
							habitatAreaHeight)
					);

					lastManagerGeneration = elapsedTime;
				}
			}
		}
	}

	public void addEmployee(Employee employee) {
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

	public void removeEmployee(Employee employee) {
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

	public void clear() {
		employees.clear();
		setDevelopersCounter(0);
		setManagersCounter(0);
	}

	public int getDevelopersCounter() {
		return developersCounter;
	}

	public void setDevelopersCounter(int developersCounter) {
		this.developersCounter = developersCounter;
	}

	public int getManagersCounter() {
		return managersCounter;
	}

	public void setManagersCounter(int managersCounter) {
		this.managersCounter = managersCounter;
	}
}