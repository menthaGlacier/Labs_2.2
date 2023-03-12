package edu.uni.lab.controller;

import edu.uni.lab.model.Simulation;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;

public class Controller {
	private Simulation simulation = null;

	@FXML
	Pane root;

	@FXML
	Pane habitatArea;

	@FXML
	VBox statistics;

	@FXML
	Label timeLabel;

	@FXML
	Label countersLabel;

	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
		simulation.bindLabels(timeLabel, countersLabel);
	}

	public void setKeyActions() {
		root.getScene().setOnKeyReleased((KeyEvent event) -> {
			switch (event.getCode()) {
			case B:
				simulation.start(habitatArea);
				break;
			case E:
				simulation.stop();
				habitatArea.getChildren().clear();
				break;
			case T:
				timeLabel.setVisible(!(timeLabel.isVisible()));
				break;
			default:
				break;
			}
		});
	}
}