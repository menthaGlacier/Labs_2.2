package edu.uni.lab.controller;

import edu.uni.lab.model.EmployeeRepository;
import edu.uni.lab.model.ai.BaseAi;
import edu.uni.lab.model.ai.DeveloperAi;
import edu.uni.lab.model.ai.ManagerAi;
import edu.uni.lab.model.employees.Developer;
import edu.uni.lab.model.Habitat;
import edu.uni.lab.model.employees.Employee;
import edu.uni.lab.model.employees.Manager;
import edu.uni.lab.utility.ConfigHandler;
import edu.uni.lab.utility.NumericField;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Arrays;

public class MainController {
	private Habitat habitat;
	private final DeveloperAi developerAi;
	private final ManagerAi managerAi;
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
	private Button startButton;
	@FXML
	private Button stopButton;
	@FXML
	private Button showObjectsButton;
	@FXML
	private RadioButton showTimeRadioButton;
	@FXML
	private RadioButton hideTimeRadioButton;
	@FXML
	private CheckBox toggleInfoDialogCheckbox;
	@FXML
	private ImageView timeIdle;
	@FXML
	private Label timeLabel;
	@FXML
	private Label developerPeriodLabel;
	@FXML
	private Label developerLifeTimeLabel;
	@FXML
	private Label managerPeriodLabel;
	@FXML
	private Label managerLifeTimeLabel;
	@FXML
	private NumericField developerPeriodField;
	@FXML
	private NumericField developerLifeTimeField;
	@FXML
	private ComboBox<String> developerProbabilityComboBox;
	@FXML
	private NumericField managerPeriodField;
	@FXML
	private NumericField managerLifeTimeField;
	@FXML
	private ComboBox<String> managerRatioComboBox;
	@FXML
	private Label developerAiStatusLabel;
	@FXML
	private Button developerAiStartButton;
	@FXML
	private Button developerAiStopButton;
	@FXML
	private ChoiceBox<String> developerAiChoiceBox;
	@FXML
	private Button managerAiStartButton;
	@FXML
	private Label managerAiStatusLabel;
	@FXML
	private Button managerAiStopButton;
	@FXML
	private ChoiceBox<String> managerAiChoiceBox;

	public MainController() {
		developerAi = new DeveloperAi();
		managerAi = new ManagerAi();
	}

	@FXML
	private synchronized void saveObjects() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save");
		fileChooser.setInitialFileName("save.employees");
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("Employees", "*.employees"));
		File file = fileChooser.showSaveDialog(root.getScene().getWindow());

		ObjectOutputStream outputStream;
		EmployeeRepository repository = EmployeeRepository.getInstance();

		try {
			outputStream = new ObjectOutputStream(new FileOutputStream(file));
			for (int i = 0; i < repository.employeesList().size(); i++) {
				outputStream.writeObject(repository.employeesList().get(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private synchronized void loadObjects() {
		stopSimulation();

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load");
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("Employees", "*.employees"));
		File file = fileChooser.showOpenDialog(root.getScene().getWindow());

		ObjectInputStream inputStream;
		EmployeeRepository repository = EmployeeRepository.getInstance();
		habitat.setDevelopersCounter(0);
		habitat.setManagersCounter(0);
		repository.clear();

		try {
			boolean keepReading = true;
			inputStream = new ObjectInputStream(new FileInputStream(file));
			while (keepReading) {
				try {
					Employee employee = (Employee) inputStream.readObject();
					employee.resetImageView();
					habitat.addEmployee(employee);
				} catch (EOFException e) {
					keepReading = false;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void startSimulation() {
		if (isActive.getValue()) {
			return;
		}

		startTime = System.nanoTime();
		lastUpdateTime = startTime;

		timer.start();
		isActive.setValue(true);
		developerAi.enable();
		managerAi.enable();
	}

	@FXML
	private void stopSimulation() {
		if (!(isActive.getValue())) {
			return;
		}

		developerAi.disable();
		managerAi.disable();
		timer.stop();

		if (isInfoDialogAllowed.getValue()) {
			final BooleanProperty stopSimulationProperty
					= new SimpleBooleanProperty(false);
			callInfoDialog(stopSimulationProperty);
			if (!(stopSimulationProperty.getValue())) {
				timer.start();
				developerAi.enable();
				managerAi.enable();
				return;
			}
		}

		isActive.setValue(false);
		habitatArea.getChildren().clear();
		EmployeeRepository.getInstance().clear();
	}

	@FXML
	private void toggleTime() {
		isTimeToggledOn.setValue(!(isTimeToggledOn.getValue()));
	}

	@FXML
	private void toggleInfoDialog() {
		isInfoDialogAllowed.setValue(!(isInfoDialogAllowed.getValue()));
	}

	@FXML
	private void onDeveloperPeriodButtonClick() {
		Integer value = (Integer) developerPeriodField.getTextFormatter().getValue();
		if (value != null && value >= 0 && value <= Habitat.PERIOD_MAX) {
			Developer.setPeriod(value);
			return;
		}

		callErrorDialog("Bad argument passed. Default value set");
		Developer.setPeriod(Habitat.PERIOD_DEFAULT);
	}

	@FXML
	private void onDeveloperLifeTimeButtonClick() {
		Integer value = (Integer) developerLifeTimeField.getTextFormatter().getValue();
		if (value != null && value >= 0 && value <= Habitat.LIFETIME_MAX) {
			Developer.setLifeTime(value);
			return;
		}

		callErrorDialog("Bad argument passed. Default value set");
		Developer.setLifeTime(Habitat.LIFETIME_DEFAULT);
	}

	@FXML
	private void onDeveloperProbabilitySelection() {
		DecimalFormat parser = new DecimalFormat("0'%'");
		try {
			Developer.setProbability(parser.parse(developerProbabilityComboBox
					.getSelectionModel().getSelectedItem()).doubleValue() / 100);
		} catch (ParseException ignored) {}
	}

	@FXML
	private void onManagerPeriodButtonClick() {
		Integer value = (Integer) managerPeriodField.getTextFormatter().getValue();
		if (value != null && value >= 0 && value <= Habitat.PERIOD_MAX) {
			Manager.setPeriod(value);
			return;
		}

		callErrorDialog("Bad argument passed. Default value set");
		Manager.setPeriod(Habitat.PERIOD_DEFAULT);
	}

	@FXML
	private void onManagerLifeTimeButtonClick() {
		Integer value = (Integer) managerLifeTimeField.getTextFormatter().getValue();
		if (value != null && value >= 0 && value <= Habitat.LIFETIME_MAX) {
			Manager.setLifeTime(value);
			return;
		}

		callErrorDialog("Bad argument passed. Default value set");
		Manager.setLifeTime(Habitat.LIFETIME_DEFAULT);
	}

	@FXML
	private void onManagerRatioSelection() {
		DecimalFormat parser = new DecimalFormat("0'%'");
		try {
			Manager.setRatio(parser.parse(managerRatioComboBox
					.getSelectionModel().getSelectedItem()).doubleValue() / 100);
		} catch (ParseException ignored) {}
	}

	@FXML
	private void onDeveloperAiStartButtonClick() {
		developerAi.enable();
	}

	@FXML
	private void onDeveloperAiStopButtonClick() {
		developerAi.disable();
	}

	@FXML
	private void onManagerAiStartButtonClick() {
		managerAi.enable();
	}

	@FXML
	private void onManagerAiStopButtonClick() {
		managerAi.disable();
	}

	@FXML
	private void callShowObjectsDialog() {
		final Stage dialog = new Stage();
		FXMLLoader loader = new FXMLLoader((getClass()
				.getResource("/edu/uni/lab/fxml/showObjectsDialog.fxml")));
		loader.setControllerFactory(controllerClass->
				new ShowObjectsDialogController(dialog));

		try {
			dialog.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			throw new RuntimeException();
		}

		developerAi.disable();
		managerAi.disable();
		timer.stop();

		dialog.setTitle("Current objects");
		dialog.setResizable(false);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(root.getScene().getWindow());
		dialog.showAndWait();

		timer.start();
		developerAi.enable();
		managerAi.enable();
	}

	@FXML
	private void callAboutDialog() {
		final Stage dialog = new Stage();
		FXMLLoader loader = new FXMLLoader((getClass()
				.getResource("/edu/uni/lab/fxml/aboutDialog.fxml")));
		loader.setControllerFactory(controllerClass->
				new AboutDialogController(dialog));

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
	private void callTerminalDialog() {
		final Stage dialog = new Stage();
		FXMLLoader loader = new FXMLLoader((getClass()
				.getResource("/edu/uni/lab/fxml/terminalDialog.fxml")));
		loader.setControllerFactory(controllerClass->
				new TerminalDialogController(dialog, habitat));

		try {
			dialog.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			throw new RuntimeException();
		}

		dialog.setTitle("Terminal");
		dialog.setResizable(false);
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
						habitat.getManagersCounter()
				)
		);

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

	private void onPriorityChoice(ChoiceBox<String> choiceBox, BaseAi ai) {
		choiceBox.setItems(FXCollections.observableArrayList("1", "2", "3", "4",
				"5", "6", "7", "8", "9", "10"));
		choiceBox.getSelectionModel()
				.selectedItemProperty()
				.addListener((observable, oldValue, newValue) ->
						ai.setPriority(Integer.parseInt(newValue)));
	}

	private void setKeyActions() {
		root.getScene().setOnKeyReleased((KeyEvent event) -> {
			switch (event.getCode()) {
			case B -> startSimulation();
			case E -> stopSimulation();
			case T -> toggleTime();
			case BACK_QUOTE -> callTerminalDialog();
			}
		});
	}

	public void setup(WindowEvent windowEvent) {
		this.habitat = new Habitat(habitatArea);
		try {
			ConfigHandler configHandler = new ConfigHandler();
			configHandler.load(developerAi, managerAi);
		} catch (IOException e) {
			e.printStackTrace();
		}

		isActive = new SimpleBooleanProperty(false);
		isTimeToggledOn = new SimpleBooleanProperty(false);
		isInfoDialogAllowed = new SimpleBooleanProperty(false);

		developerPeriodLabel.textProperty()
				.bind(Bindings.concat("Period: ",
						Developer.period()));
		developerLifeTimeLabel.textProperty()
				.bind(Bindings.concat("Life time: ",
						Developer.lifeTime()));
		managerPeriodLabel.textProperty()
				.bind(Bindings.concat("Period: ",
						Manager.period()));
		managerLifeTimeLabel.textProperty()
				.bind(Bindings.concat("Life time: ",
						Manager.lifeTime()));

		startButton.disableProperty().bind(isActive);
		stopButton.disableProperty().bind(isActive.not());
		showObjectsButton.disableProperty().bind(isActive.not());
		timeLabel.visibleProperty().bind(isTimeToggledOn);
		timeIdle.visibleProperty().bind(isTimeToggledOn.not());

		ToggleGroup shownTime = new ToggleGroup();
		showTimeRadioButton.setToggleGroup(shownTime);
		hideTimeRadioButton.setToggleGroup(shownTime);
		showTimeRadioButton.selectedProperty().bindBidirectional(isTimeToggledOn);
		toggleInfoDialogCheckbox.selectedProperty().bindBidirectional(isInfoDialogAllowed);

		String[] values = {"10%", "20%", "30%", "40%", "50%",
							"60%", "70%", "80%", "90%", "100%"};
		developerProbabilityComboBox.getItems().setAll(Arrays.asList(values));
		developerProbabilityComboBox.setValue((int) (Developer.getProbability() * 100) + "%");
		managerRatioComboBox.getItems().setAll(Arrays.asList(values));
		managerRatioComboBox.setValue((int) (Manager.getRatio() * 100) + "%");

		developerAiStartButton.disableProperty().bind(developerAi.running());
		developerAiStopButton.disableProperty().bind(developerAi.running().not());
		developerAiStatusLabel.textProperty().bind(Bindings
				.when(developerAi.running())
				.then("Status: active").otherwise("Status: inactive"));

		managerAiStartButton.disableProperty().bind(managerAi.running());
		managerAiStopButton.disableProperty().bind(managerAi.running().not());
		managerAiStatusLabel.textProperty().bind(Bindings
				.when(managerAi.running())
				.then("Status: active").otherwise("Status: inactive"));

		developerAiChoiceBox.setValue(String.valueOf(developerAi.getPriority()));
		onPriorityChoice(developerAiChoiceBox, developerAi);
		managerAiChoiceBox.setValue(String.valueOf(managerAi.getPriority()));
		onPriorityChoice(managerAiChoiceBox, managerAi);

		setKeyActions();

		timer = new AnimationTimer() {
			static final long nanoSecondsPerFrame = 1_000_000_000 / 60;

			@Override
			public void handle(long timeNow) {
				if (timeNow - lastUpdateTime >= nanoSecondsPerFrame) {
					habitat.update((timeNow - startTime) / 1_000_000);
					lastUpdateTime = timeNow;

					timeLabel.setText("Time: "
							+ (lastUpdateTime - startTime) / 1_000_000_000
							+ "s."
					);
				}
			}
		};
	}

	public void exit() {
		try {
			ConfigHandler configHandler = new ConfigHandler();
			configHandler.save(developerAi, managerAi);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}