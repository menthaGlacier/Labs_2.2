package edu.uni.lab.controller;

import edu.uni.lab.model.Simulation;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import javafx.fxml.FXML;

public class Controller {
	private Simulation simulation = null;

	@FXML
	private Pane root;
	@FXML
	private Pane habitatArea;
	@FXML
	private Label timeLabel;
	@FXML
	private Label countersLabel;

	public Controller(Simulation simulation) {
		this.simulation = simulation;
	}

	private void setKeyActions() {
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

	public void setup(WindowEvent windowEvent) {
		simulation.bindStatisticsLabels(timeLabel, countersLabel);
		setKeyActions();
	}
}