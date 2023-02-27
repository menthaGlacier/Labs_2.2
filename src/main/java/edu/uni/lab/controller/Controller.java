package edu.uni.lab.controller;

import edu.uni.lab.model.Simulation;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Controller {
	private Simulation simulation;
	private Stage primaryStage;

	public Controller(Stage stage) throws IOException {
		primaryStage = stage;
		this.simulation = new Simulation((Label) primaryStage.getScene().lookup("#timeLabel"));
		setKeyActions();
	}

	private void setKeyActions() {
		primaryStage.getScene().setOnKeyReleased((KeyEvent event) -> {
			switch (event.getCode())
			{
				case B: {
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
				default: break;
			}
		});
	}
}