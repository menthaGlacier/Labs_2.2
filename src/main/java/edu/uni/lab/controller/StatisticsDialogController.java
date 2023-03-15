package edu.uni.lab.controller;

import edu.uni.lab.model.Developer;
import edu.uni.lab.model.Manager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class StatisticsDialogController {
	private final Stage stage;
	private final BooleanProperty stopSimulationProperty;
	private final int developerCounter;
	private final int managerCounter;
	private final long timePassed;

	@FXML
	private Label developerInfo;
	@FXML
	private Label managerInfo;
	@FXML
	private Label timeInfo;

	public StatisticsDialogController(Stage stage, long timePassed,
									int developerCounter, int managerCounter) {
		this.stage = stage;
		this.developerCounter = developerCounter;
		this.managerCounter = managerCounter;
		this.timePassed = timePassed;

		stopSimulationProperty = new SimpleBooleanProperty(false);
	}

	@FXML
	private void cancel() {
		stopSimulationProperty.setValue(false);
		stage.close();
	}

	@FXML
	private void ok() {
		stopSimulationProperty.setValue(true);
		stage.close();
	}

	@FXML
	private void initialize() {
		developerInfo.setText("Total amount:" + developerCounter + "\n"
				+ "Period: " + Developer.getPeriod() + "ms." + "\n"
				+ "Probability: " + Developer.getProbability()
		);

		managerInfo.setText("Total amount:" + managerCounter + "\n"
				+ "Period: " + Manager.getPeriod() + "ms." + "\n"
				+ "Ratio: " + Manager.getRatio()
		);

		timeInfo.setText("Total time passed: " + timePassed + "s.");
	}

	public BooleanProperty getStopSimulationProperty() {
		return stopSimulationProperty;
	};
}