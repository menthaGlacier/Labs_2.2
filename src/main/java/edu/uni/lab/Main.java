package edu.uni.lab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

import edu.uni.lab.controller.Controller;

public class Main extends Application {
	private static final long FRAME_TIME = 1000 / 60; // 60 FPS
	private static final int WINDOW_WIDTH = 1280;
	private static final int WINDOW_HEIGHT = 720;
	private static final int HABITAT_AREA_WIDTH = 880;
	private static final int HABITAT_AREA_HEIGHT = 480;

	private Controller controller;
	private Scene startScene, simulationScene, statisticsScene;

	@Override
	public void start(Stage stage) throws IOException {

		//habitat = new Habitat(HABITAT_AREA_WIDTH, HABITAT_AREA_HEIGHT);


		URL url = getClass().getResource("simulation.fxml");
		if (url != null) {
			simulationScene = new Scene(FXMLLoader.load(url), 1280, 720);
		} else { throw new IOException(); }

		stage.setScene(simulationScene);
		stage.setTitle("Simulation");
		stage.setResizable(false);
		stage.show();
		controller = new Controller(stage);
	}

	public static void main(String[] args) {
		launch();
	}
}