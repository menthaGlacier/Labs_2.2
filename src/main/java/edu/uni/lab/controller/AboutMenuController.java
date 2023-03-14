package edu.uni.lab.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AboutMenuController {
	private Stage stage;

	@FXML
	private AnchorPane root;
	@FXML
	private Button exitAboutButton;

	public AboutMenuController(Stage stage) {
		this.stage = stage;
	}

	@FXML
	private void handleAction() {
		exitAboutButton.setOnAction(actionEvent -> {
			stage.close();
		});
	}
}