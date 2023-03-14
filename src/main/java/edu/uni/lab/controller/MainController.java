package edu.uni.lab.controller;

import edu.uni.lab.model.Simulation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainController {
	private Simulation simulation;

	@FXML
	private AnchorPane root;
	@FXML
	private Pane habitatArea;
	@FXML
	private Label timeLabel;
	@FXML
	private Label countersLabel;
	@FXML
	private Button startButton;
	@FXML
	private Button stopButton;
	@FXML
	private MenuItem startMenu;
	@FXML
	private MenuItem stopMenu;
	@FXML
	private MenuItem toggleTimeMenu;
	@FXML
	private MenuItem toggleModalWindowMenu;
	@FXML
	private MenuItem aboutMenu;

	public MainController(Simulation simulation) {
		this.simulation = simulation;
	}

	private void start() {
		simulation.start(habitatArea);
	}

	private void stop() {
		simulation.stop();
		habitatArea.getChildren().clear();
	}

	private void toggleTime() {
		timeLabel.setVisible(!(timeLabel.isVisible()));
	}

	private void callAboutWindow() {
		final Stage dialog = new Stage();
		FXMLLoader loader = new FXMLLoader((getClass()
				.getResource("/edu/uni/lab/fxml/about.fxml")));
		loader.setControllerFactory(clazz->new AboutMenuController(dialog));
		try {
			dialog.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			throw new RuntimeException();
		}

		dialog.setTitle("About");
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(root.getScene().getWindow());
		dialog.showAndWait();
	}

	private void setKeyActions() {
		root.getScene().setOnKeyReleased((KeyEvent event) -> {
			switch (event.getCode()) {
			case B -> start();
			case E -> stop();
			case T -> toggleTime();
			}
		});
	}

	private void setButtonActions() {
		startButton.setOnAction(actionEvent -> start());
		stopButton.setOnAction(actionEvent -> stop());
	}

	private void setMenuItemsActions() {
		startMenu.setOnAction(actionEvent -> start());
		stopMenu.setOnAction(actionEvent -> stop());
		toggleTimeMenu.setOnAction(actionEvent -> toggleTime());
		//toggleModalWindowMenu.setOnAction(actionEvent -> callAboutWindow());
		aboutMenu.setOnAction(actionEvent -> callAboutWindow());
	}
	public void setup(WindowEvent windowEvent) {
		simulation.bindStatisticsLabels(timeLabel, countersLabel);
		startButton.disableProperty().bind(simulation.getIsActiveProperty());
		stopButton.disableProperty().bind(simulation.getIsActiveProperty().not());

		setKeyActions();
		setButtonActions();
		setMenuItemsActions();
	}
}