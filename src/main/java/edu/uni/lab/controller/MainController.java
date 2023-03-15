package edu.uni.lab.controller;

import edu.uni.lab.model.Developer;
import edu.uni.lab.model.Habitat;
import edu.uni.lab.model.Manager;
import edu.uni.lab.model.Simulation;
import edu.uni.lab.utility.NumericField;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Arrays;

public class MainController {
	private final Simulation simulation;

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

	@FXML
	private NumericField developerPeriodField;
	@FXML
	private Label developerPeriodLabel;
	@FXML
	private ComboBox<String> developerProbabilityComboBox;

	@FXML
	private NumericField managerPeriodField;
	@FXML
	private Label managerPeriodLabel;
	@FXML
	private ComboBox<String> managerRatioComboBox;

	@FXML
	private NumericField employeeAmountField;
	@FXML
	private Label employeeAmountLabel;

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

	private void callAboutDialog() {
		final Stage dialog = new Stage();
		FXMLLoader loader = new FXMLLoader((getClass()
				.getResource("/edu/uni/lab/fxml/aboutDialog.fxml")));
		loader.setControllerFactory(controllerClass->
				new AboutMenuController(dialog));

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

	private void callStatisticsDialog() {
		final Stage dialog = new Stage();
		FXMLLoader loader = new FXMLLoader((getClass()
				.getResource("/edu/uni/lab/fxml/statisticsDialog.fxml")));
		loader.setControllerFactory(controllerClass->
				new AboutMenuController(dialog));

		try {
			dialog.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			throw new RuntimeException();
		}

		dialog.setTitle("Statistics");
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

	@FXML
	private void onDeveloperPeriodButtonClick() {
		Integer value = (Integer) developerPeriodField.getTextFormatter().getValue();
		if (value != null
				&& value < Habitat.DEVELOPER_PERIOD_MAX && value > Habitat.DEVELOPER_PERIOD_MIN) {
			Developer.setPeriod(value);
		}
	}

	@FXML
	private void onManagerPeriodButtonClick() {
		Integer value = (Integer) managerPeriodField.getTextFormatter().getValue();
		if (value != null
				&& value < Habitat.MANAGER_PERIOD_MAX && value > Habitat.MANAGER_PERIOD_MIN) {
			Manager.setPeriod(value);
		}
	}

	@FXML
	private void onEmployeeAmountButtonClick() {
		Integer value = (Integer) employeeAmountField.getTextFormatter().getValue();
		if (value != null && value > 0) {
			Habitat.setRepositorySize(value);
		}
	}

	private void setMenuItemsActions() {
		startMenu.setOnAction(actionEvent -> start());
		stopMenu.setOnAction(actionEvent -> stop());
		toggleTimeMenu.setOnAction(actionEvent -> toggleTime());
		//toggleModalWindowMenu.setOnAction(actionEvent -> callAboutWindow());
		aboutMenu.setOnAction(actionEvent -> callAboutDialog());
	}

	@FXML
	private void onDeveloperProbabilitySelection() {
		DecimalFormat parser = new DecimalFormat("0'%'");
		try {
			Developer.setProbability(parser.parse(developerProbabilityComboBox.getSelectionModel().getSelectedItem()).doubleValue());
		}
		catch (ParseException e) {
			return;
		}
	}

	@FXML
	private void onManagerRatioSelection() {
		DecimalFormat parser = new DecimalFormat("0'%'");
		try {
			Manager.setRatio(parser.parse(managerRatioComboBox.getSelectionModel().getSelectedItem()).doubleValue());
		}
		catch (ParseException e) {
			return;
		}
	}

	public void setup(WindowEvent windowEvent) {
		simulation.bindStatisticsLabels(timeLabel, countersLabel);
		startButton.disableProperty().bind(simulation.getIsActiveProperty());
		stopButton.disableProperty().bind(simulation.getIsActiveProperty().not());

		developerPeriodLabel.textProperty().bind(Bindings.concat("Current developers' period: ", Developer.getPeriodProperty()));
		managerPeriodLabel.textProperty().bind(Bindings.concat("Current managers' period: ", Manager.getPeriodProperty()));
		employeeAmountLabel.textProperty().bind(Bindings.concat("Current employee amount: ", Habitat.getRepositorySizeProperty()));

		String[] values = {"10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%"};
		developerProbabilityComboBox.getItems().setAll(Arrays.asList(values));
		managerRatioComboBox.getItems().setAll(Arrays.asList(values));

		setKeyActions();
		setButtonActions();
		setMenuItemsActions();

	}
}