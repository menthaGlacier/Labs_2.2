package edu.uni.lab.controller;

import edu.uni.lab.model.Developer;
import edu.uni.lab.model.Manager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.stage.WindowEvent;

public class StatisticsDialogController {
	private final Stage stage;
	private final BooleanProperty stopSimulationProperty;
	private int developerCounter;
	private int managerCounter;
	private long timePassed;

	@FXML
	private Label developerInfo;
	@FXML
	private Label managerInfo;
	@FXML
	private Label timeInfo;
	@FXML
	private Button returnToSimulationButton;
	@FXML
	private Button exitSimulationButton;

	public StatisticsDialogController(Stage stage, long timePassed,
									int developerCounter, int managerCounter) {
		this.stage = stage;
		stopSimulationProperty = new SimpleBooleanProperty(false);
		this.developerCounter = developerCounter;
		this.managerCounter = managerCounter;
		this.timePassed = timePassed;
	}

	@FXML
	private void setButtonActions() {
		exitSimulationButton.setOnAction(actionEvent -> {
			stopSimulationProperty.setValue(false);
			stage.close();
		});

		returnToSimulationButton.setOnAction(actionEvent -> {
			stopSimulationProperty.setValue(true);
			stage.close();
		});
	}

	public void setup(WindowEvent windowEvent) {
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