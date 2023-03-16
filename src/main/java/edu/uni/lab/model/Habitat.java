package edu.uni.lab.model;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.Pane;

public class Habitat {
	private static SimpleIntegerProperty repositorySizeProperty = new SimpleIntegerProperty(100);
	public static final int DEVELOPER_PERIOD_MAX = 60000;
	public static final int MANAGER_PERIOD_MAX = 60000;

	private final Pane habitatArea;
	private final EmployeeRepository employees;
	private int developersCounter = 0;
	private int managersCounter = 0;
	private long lastDeveloperGenerationTime = 0;
	private long lastManagerGenerationTime = 0;

	private final Random random = new Random();

	public Habitat(Pane habitatArea) {
		this.habitatArea = habitatArea;

		employees = EmployeeRepository.getInstance();
		employees.resize(repositorySizeProperty.get());
	}

	public void update(long elapsedTime) {
		if ((developersCounter + managersCounter) >= employees.size()) {
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
		// TODO Forbid employee increment if add was fail
		employees.add(employee, developersCounter + managersCounter);
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

	public static void setRepositorySize(int repositorySize) {
		repositorySizeProperty.setValue(repositorySize);
	}

	public static SimpleIntegerProperty getRepositorySizeProperty() {
		return repositorySizeProperty;
	}
}