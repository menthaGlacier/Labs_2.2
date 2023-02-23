package edu.uni.lab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class Main extends Application {
	private static final long FRAME_TIME = 1000 / 60; // 60 FPS
	private static final int WINDOW_WIDTH = 1280;
	private static final int WINDOW_HEIGHT = 720;
	private static long startTime = System.currentTimeMillis();

	private Pane root;
	private Rectangle rectangle;

	@Override
	public void start(Stage stage) throws IOException {
		root = FXMLLoader.load(getClass().getResource("simulation.fxml"));
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		rectangle = (Rectangle) root.lookup("#rectangle");
		stage.setTitle("Test");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}