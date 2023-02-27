package edu.uni.lab.controller;

import edu.uni.lab.model.Simulation;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
		this.simulation = new Simulation();
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
					break;
				}
				default: break;
			}
		});
	}
}