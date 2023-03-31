package edu.uni.lab.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

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