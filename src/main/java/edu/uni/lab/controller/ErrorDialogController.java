package edu.uni.lab.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorDialogController {
	private final Stage stage;
	private final String errorMessageText;

	@FXML
	private Label errorMessage;

	public ErrorDialogController(Stage stage, String errorMessageText) {
		this.stage = stage;
		this.errorMessageText = errorMessageText;
	}

	@FXML
	private void initialize() {
		errorMessage.setText(errorMessageText);
	}

	@FXML
	private void close() {
		stage.close();
	}
}