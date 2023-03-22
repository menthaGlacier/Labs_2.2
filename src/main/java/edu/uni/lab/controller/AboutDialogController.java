package edu.uni.lab.controller;

import javafx.stage.Stage;
import javafx.fxml.FXML;

public class AboutDialogController {
	private final Stage stage;

	public AboutDialogController(Stage stage) {
		this.stage = stage;
	}

	@FXML
	private void close() {
		stage.close();
	}
}