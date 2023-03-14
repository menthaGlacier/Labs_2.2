package edu.uni.lab.controller;

import edu.uni.lab.model.Simulation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.WindowEvent;
import javafx.fxml.FXML;

public class Controller {
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
	private MenuItem simulationMenuItemStart;
	@FXML
	private MenuItem simulationMenuItemStop;
	@FXML
	private MenuItem viewMenuItemToggleTime;
	@FXML
	private MenuItem viewMenuItemToggleModalWindow;

	public Controller(Simulation simulation) {
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
		simulationMenuItemStart.setOnAction(actionEvent -> start());
		simulationMenuItemStop.setOnAction(actionEvent -> stop());
		viewMenuItemToggleTime.setOnAction(actionEvent -> toggleTime());
		//viewMenuItemToggleModalWindow.setOnAction(actionEvent -> stop()); // TODO
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