package edu.uni.lab;

import edu.uni.lab.controller.MainControllerFactory;
import edu.uni.lab.controller.MainController;
import edu.uni.lab.model.Simulation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
	private static final int WINDOW_WIDTH = 1280;
	private static final int WINDOW_HEIGHT = 720;

	private Scene scene;
	private Simulation simulation;
	private MainController controller;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws IOException {
		simulation = new Simulation();
		FXMLLoader loader = new FXMLLoader((getClass()
					.getResource("/edu/uni/lab/fxml/simulation.fxml")));
		loader.setControllerFactory(controllerClass ->
					new MainControllerFactory().getController(simulation));

		scene = new Scene(loader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
		controller = loader.getController();

		stage.setOnShown(controller::setup);
		stage.setTitle("Simulation");
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
}