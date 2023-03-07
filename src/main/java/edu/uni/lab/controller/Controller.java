package edu.uni.lab.controller;

import edu.uni.lab.model.Simulation;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class Controller {
	private final Simulation simulation;
	private final Stage primaryStage;

	@FXML
	Pane root;

	@FXML
	Pane habitatArea;

	@FXML
	Label timeLabel;


	public Controller(Stage primaryStage, Simulation simulation) {
		this.primaryStage = primaryStage;
		this.simulation = simulation;
		setKeyActions();
	}

	private void setKeyActions() {
		primaryStage.getScene().setOnKeyReleased((KeyEvent event) -> {
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