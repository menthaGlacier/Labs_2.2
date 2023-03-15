package edu.uni.lab.controller;

import edu.uni.lab.model.Developer;
import edu.uni.lab.model.Habitat;
import edu.uni.lab.model.Manager;
import edu.uni.lab.utility.NumericField;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainController {
	private Habitat habitat;
	private AnimationTimer timer;
	private long startTime;
	private long lastUpdateTime = 0;
	private boolean isActive = false;

	@FXML
	private AnchorPane root;
	@FXML
	private Pane habitatArea;
	@FXML
	private Label timeLabel;
	@FXML
	private Label countersLabel;

	@FXML
	private NumericField developerPeriodField;
	@FXML
	private Label developerPeriodLabel;

	@FXML
	private NumericField managerPeriodField;
	@FXML
	private Label managerPeriodLabel;

	@FXML
	private NumericField employeeAmountField;
	@FXML
	private Label employeeAmountLabel;

	public MainController() {
	}

	@FXML
	public void start() {
		this.habitat = new Habitat(habitatArea);
		if (isActive) {
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
		isActive = true;
	}

	@FXML
	private void stop() {
		if (!isActive) {
			return;
		}

		timer.stop();
		isActive = false;
		habitatArea.getChildren().clear();
		callStatisticsDialog();
	}

	@FXML
	private void toggleTime() {
		timeLabel.setVisible(!(timeLabel.isVisible()));
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

	@FXML
	private void callStatisticsDialog() {
		final Stage dialog = new Stage();
		final BooleanProperty stopSimulation = new SimpleBooleanProperty(false);
		FXMLLoader loader = new FXMLLoader((getClass()
				.getResource("/edu/uni/lab/fxml/statisticsDialog.fxml")));
		loader.setControllerFactory(controllerClass->
				new StatisticsDialogController(dialog, stopSimulation,
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

	@FXML
	private void onDeveloperPeriodButtonClick() {
		Integer value = (Integer) developerPeriodField.getTextFormatter().getValue();
		if (value != null && value > 0 && value < Habitat.DEVELOPER_PERIOD_MAX) {
			Developer.setPeriod(value);
		}
	}

	@FXML
	private void onManagerPeriodButtonClick() {
		Integer value = (Integer) managerPeriodField.getTextFormatter().getValue();
		if (value != null && value > 0 && value < Habitat.MANAGER_PERIOD_MAX) {
			Manager.setPeriod(value);
		}
	}

	@FXML private void onEmployeeAmountButtonClick() {
		Integer value = (Integer) employeeAmountField.getTextFormatter().getValue();
		if (value != null && value > 0) {
			Habitat.setRepositorySize(value);
		}
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

	public void setup(WindowEvent windowEvent) {
		developerPeriodLabel.textProperty()
				.bind(Bindings.concat("Current developers' period: ",
						Developer.getPeriodProperty()));
		managerPeriodLabel.textProperty()
				.bind(Bindings.concat("Current managers' period: ",
						Manager.getPeriodProperty()));
		employeeAmountLabel.textProperty()
				.bind(Bindings.concat("Current employee amount: ",
						Habitat.getRepositorySizeProperty()));

		setKeyActions();
	}
}