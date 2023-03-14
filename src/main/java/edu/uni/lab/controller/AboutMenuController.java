package edu.uni.lab.controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class AboutMenuController {
	private final Stage stage;

	@FXML
	private Button exitAboutButton;

	public AboutMenuController(Stage stage) {
		this.stage = stage;
	}

	@FXML
	private void handleAction() {
		exitAboutButton.setOnAction(actionEvent -> stage.close());
	}
}