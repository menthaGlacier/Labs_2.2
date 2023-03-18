package edu.uni.lab.controller;

import edu.uni.lab.model.Developer;
import edu.uni.lab.model.Habitat;
import edu.uni.lab.model.Manager;
import edu.uni.lab.utility.NumericField;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Arrays;

public class MainController {
	private Habitat habitat;
	private AnimationTimer timer;
	private long startTime;
	private long lastUpdateTime = 0;
	private BooleanProperty isActive;
	private BooleanProperty isTimeToggledOn;
	private BooleanProperty isInfoDialogAllowed;

	@FXML
	private AnchorPane root;
	@FXML
	private Pane habitatArea;
	@FXML
	private Label timeLabel;
	@FXML
	private Label countersLabel;
	@FXML
	private Button startSimButton;
	@FXML
	private Button stopSimButton;
	@FXML
	private RadioButton showTimeRadioButton;
	@FXML
	private RadioButton hideTimeRadioButton;
	@FXML
	private CheckBox toggleInfoDialogCheckbox;
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

	public MainController() {
	}

	@FXML
	public void startSimulation() {
		this.habitat = new Habitat(habitatArea);
		if (isActive.getValue()) {
			return;
		}

		startTime = System.nanoTime();
		lastUpdateTime = startTime;
		timer = new AnimationTimer() {
			private static final long nanoSecondsPerFrame = 1_000_000_000 / 60;

			@Override
			public void handle(long timeNow) {
				if (timeNow - lastUpdateTime >= nanoSecondsPerFrame) {
					habitat.update((timeNow - startTime) / 1_000_000);
					lastUpdateTime = timeNow;

					timeLabel.setText("Time: "
							+ (lastUpdateTime - startTime) / 1_000_000_000
							+ "s."
					);

					countersLabel.setText("Developers: "
							+ habitat.getDevelopersCounter() + "\n"
							+ "Managers: "
							+ habitat.getManagersCounter()
					);
				}
			}
		};

		timer.start();
		isActive.setValue(true);
	}

	@FXML
	private void stopSimulation() {
		if (!(isActive.getValue())) {
			return;
		}

		timer.stop();

		if (isInfoDialogAllowed.getValue()) {
			final BooleanProperty stopSimulationProperty = new SimpleBooleanProperty(false);
			callInfoDialog(stopSimulationProperty);
			if (!(stopSimulationProperty.getValue())) {
				timer.start();
				return;
			}
		}

		isActive.setValue(false);
		habitatArea.getChildren().clear();
	}

	@FXML
	private void toggleTime() {
		isTimeToggledOn.setValue(!(isTimeToggledOn.getValue()));
	}

	@FXML
	private void onDeveloperPeriodButtonClick() {
		Integer value = (Integer) developerPeriodField.getTextFormatter().getValue();
		if (value != null && value >= 0 && value <= Habitat.DEVELOPER_PERIOD_MAX) {
			Developer.setPeriod(value);
		} else {
			callErrorDialog("Bad argument passed. Default value set");
			Developer.setPeriod(1000);
		}
	}

	@FXML
	private void onManagerPeriodButtonClick() {
		Integer value = (Integer) managerPeriodField.getTextFormatter().getValue();
		if (value != null && value >= 0 && value <= Habitat.MANAGER_PERIOD_MAX) {
			Manager.setPeriod(value);
		} else {
			callErrorDialog("Bad argument passed. Default value set");
			Manager.setPeriod(1000);
		}
	}

	@FXML
	private void onEmployeeAmountButtonClick() {
		Integer value = (Integer) employeeAmountField.getTextFormatter().getValue();
		if (value != null && value > 0 && value < 1_000) {
			Habitat.setRepositorySize(value);
		} else {
			callErrorDialog("Bad argument passed. Default value set");
			Habitat.setRepositorySize(100);
		}
	}

	@FXML
	private void onDeveloperProbabilitySelection() {
		DecimalFormat parser = new DecimalFormat("0'%'");
		try {
			Developer.setProbability(parser.parse(developerProbabilityComboBox
					.getSelectionModel().getSelectedItem()).doubleValue());
		} catch (ParseException ignored) {}
	}

	@FXML
	private void onManagerRatioSelection() {
		DecimalFormat parser = new DecimalFormat("0'%'");
		try {
			Manager.setRatio(parser.parse(managerRatioComboBox
					.getSelectionModel().getSelectedItem()).doubleValue());
		} catch (ParseException ignored) {}
	}

	@FXML
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
		dialog.setResizable(false);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(root.getScene().getWindow());
		dialog.showAndWait();
	}

	private void callInfoDialog(BooleanProperty stopSimulation) {
		final Stage dialog = new Stage();
		FXMLLoader loader = new FXMLLoader((getClass()
				.getResource("/edu/uni/lab/fxml/infoDialog.fxml")));
		loader.setControllerFactory(controllerClass->
				new InfoDialogController(dialog, stopSimulation,
						(lastUpdateTime - startTime) / 1_000_000_000,
						habitat.getDevelopersCounter(),
						habitat.getManagersCounter()));

		try {
			dialog.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			throw new RuntimeException();
		}

		dialog.setTitle("Statistics");
		dialog.setResizable(false);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(root.getScene().getWindow());
		dialog.showAndWait();
	}

	private void callErrorDialog(String errorMessage) {
		final Stage dialog = new Stage();
		FXMLLoader loader = new FXMLLoader((getClass()
				.getResource("/edu/uni/lab/fxml/errorDialog.fxml")));
		loader.setControllerFactory(controllerClass->
				new ErrorDialogController(dialog, errorMessage));

		try {
			dialog.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			throw new RuntimeException();
		}

		dialog.setTitle("Error!");
		dialog.setResizable(false);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(root.getScene().getWindow());
		dialog.showAndWait();
	}

	private void setKeyActions() {
		root.getScene().setOnKeyReleased((KeyEvent event) -> {
			switch (event.getCode()) {
			case B -> startSimulation();
			case E -> stopSimulation();
			case T -> toggleTime();
			}
		});
	}

	public void setup(WindowEvent windowEvent) {
		isActive = new SimpleBooleanProperty(false);
		isTimeToggledOn = new SimpleBooleanProperty(false);
		isInfoDialogAllowed = new SimpleBooleanProperty(false);

		developerPeriodLabel.textProperty()
				.bind(Bindings.concat("Current developers' period: ",
						Developer.getPeriodProperty()));
		managerPeriodLabel.textProperty()
				.bind(Bindings.concat("Current managers' period: ",
						Manager.getPeriodProperty()));
		employeeAmountLabel.textProperty()
				.bind(Bindings.concat("Current employee amount: ",
						Habitat.getRepositorySizeProperty()));

		startSimButton.disableProperty().bind(isActive);
		stopSimButton.disableProperty().bind(isActive.not());
		timeLabel.visibleProperty().bind(isTimeToggledOn);

		ToggleGroup shownTime = new ToggleGroup();
		showTimeRadioButton.setToggleGroup(shownTime);
		hideTimeRadioButton.setToggleGroup(shownTime);
		showTimeRadioButton.selectedProperty().bindBidirectional(isTimeToggledOn);
//		hideTimeRadioButton.selectedProperty().bindBidirectional(showTimeRadioButton.selectedProperty().not());
		toggleInfoDialogCheckbox.selectedProperty().bindBidirectional(isInfoDialogAllowed);

		String[] values = {"10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%"};
		developerProbabilityComboBox.getItems().setAll(Arrays.asList(values));
		managerRatioComboBox.getItems().setAll(Arrays.asList(values));

		setKeyActions();
	}
}