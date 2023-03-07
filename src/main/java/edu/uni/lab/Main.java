package edu.uni.lab;

import edu.uni.lab.controller.Controller;
import edu.uni.lab.model.Simulation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
	private static final int WINDOW_WIDTH = 1280;
	private static final int WINDOW_HEIGHT = 720;

	private Controller controller;
	private Scene simulationScene;
	private Simulation simulation;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws IOException {
		URL url = getClass().getResource("fxml/simulation.fxml");
		if (url != null) {
			simulationScene = new Scene(FXMLLoader.load(url), WINDOW_WIDTH, WINDOW_HEIGHT);
		} else {
			throw new IOException();
		}

		stage.setScene(simulationScene);
		stage.setTitle("Simulation");
		stage.setResizable(false);
		stage.show();

		simulation = new Simulation();
		controller = new Controller(stage, simulation);
	}
}