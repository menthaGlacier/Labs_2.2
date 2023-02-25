package edu.uni.lab;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;

import edu.uni.lab.model.Habitat;
import edu.uni.lab.controller.InputHandler;

public class Main extends Application {
	private static final int FPS = 60;
	private static final int WINDOW_WIDTH = 1280;
	private static final int WINDOW_HEIGHT = 720;
	private static final int HABITAT_AREA_WIDTH = 880;
	private static final int HABITAT_AREA_HEIGHT = 480;

	private Habitat habitat;
	private InputHandler inputHandler;
	private long startTime;
	private Stage primaryStage;

	@Override
	public void start(Stage stage) throws IOException {
		habitat = new Habitat(HABITAT_AREA_WIDTH, HABITAT_AREA_HEIGHT);
		inputHandler = new InputHandler(habitat);

		Pane root = FXMLLoader.load(getClass()
				.getResource("fxml/simulation.fxml"));
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.setOnKeyPressed(inputHandler);

		AnimationTimer animationTimer = new AnimationTimer() {
			private long lastTime = 0;

			@Override
			public void handle(long now) {
				if (!(habitat.isActive())) {
					return;
				}

				if (now - lastTime >= 1e9 / FPS) {
					habitat.update(now - lastTime / 1_000_000);
					habitat.debugStatistics();
					lastTime = now;
				}
			}
		};

		stage.setTitle("Simulation");
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();

		animationTimer.start();
	}

	public static void main(String[] args) {
		launch();
	}
}