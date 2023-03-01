package edu.uni.lab.controller;

import edu.uni.lab.model.Simulation;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller {
	private Simulation simulation;
	private Stage primaryStage;

	public Controller(Stage stage) {
		primaryStage = stage;
		setKeyActions();
	}

	private void setKeyActions() {
		primaryStage.getScene().setOnKeyReleased((KeyEvent event) -> {
			switch (event.getCode()) {
			case B: {
				this.simulation = new Simulation((VBox) primaryStage.getScene().lookup("#statistics"));
				simulation.start((Pane) primaryStage.getScene().lookup("#habitatArea"));
				break;
			}
			case E: {
				simulation.stop();
				Pane habitatArea = (Pane) primaryStage.getScene().lookup("#habitatArea");
				habitatArea.getChildren().clear();
				break;
			}
			case T: {
				Node timeLabel = primaryStage.getScene().lookup("#timeLabel");
				timeLabel.setVisible(!timeLabel.isVisible());
			}
			default:
				break;
			}
		});
	}
}