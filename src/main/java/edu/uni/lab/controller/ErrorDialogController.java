package edu.uni.lab.controller;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXML;

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