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

	private Scene scene;
	private Controller controller;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader((getClass()
					.getResource("/edu/uni/lab/fxml/simulation.fxml")));
		scene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
		controller = loader.getController();
		controller.setSimulation(new Simulation());
		controller.setKeyActions();

		stage.setScene(scene);
		stage.setTitle("Simulation");
		stage.setResizable(false);
		stage.show();
	}
}