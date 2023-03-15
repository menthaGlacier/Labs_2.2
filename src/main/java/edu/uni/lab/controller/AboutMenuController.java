package edu.uni.lab.controller;

import javafx.stage.Stage;
import javafx.fxml.FXML;

public class AboutMenuController {
	private final Stage stage;

	public AboutMenuController(Stage stage) {
		this.stage = stage;
	}

	@FXML
	private void close() {
		stage.close();
	}
}