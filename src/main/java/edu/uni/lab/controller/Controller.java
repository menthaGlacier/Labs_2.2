package edu.uni.lab.controller;

import edu.uni.lab.model.Simulation;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class Controller {
	private Simulation simulation = null;

	@FXML
	Pane root;

	@FXML
	Pane habitatArea;

	public Controller() {
		//	setKeyActions();
	}

	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}

	@FXML
	private void setKeyActions(KeyEvent event) {
		//root.getScene().setOnKeyReleased((KeyEvent event) -> {
			switch (event.getCode()) {
			case B:
				//simulation.start(habitatArea);
				System.out.println("B");
				break;
			case E:
				//simulation.stop();
				//habitatArea.getChildren().clear();
				System.out.println("E");
				break;
			case T:
				//timeLabel.setVisible(!(timeLabel.isVisible()));
				System.out.println("T");
				break;
			default:
				break;
			}
		//});
	}
}