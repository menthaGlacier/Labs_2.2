package edu.uni.lab.controller;

import edu.uni.lab.client.Client;
import edu.uni.lab.model.EmployeeRepository;
import edu.uni.lab.model.Habitat;
import edu.uni.lab.model.employees.Employee;
import edu.uni.lab.model.employees.Manager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.concurrent.ThreadLocalRandom;

public class TerminalDialogController {
	private final Stage stage;
	private final Habitat habitat;
	private final Client client;

	@FXML
	private TextField inputTextField;
	@FXML
	private TextArea outputTextArea;

	public TerminalDialogController(Stage stage, Habitat habitat, Client client) {
		this.stage = stage;
		this.habitat = habitat;
		this.client = client;
	}

	private void appendText(String message) {
		outputTextArea.appendText(message);
	}

	private void appendTextLn(String message) {
		appendText(message + "\n");
	}

	private boolean checkAmountOfArguments(String[] tokens, int min, int max) {
		if (tokens.length < min) {
			appendTextLn("Not enough arguments were passed");
			return false;
		}

		if (tokens.length > max) {
			appendTextLn("Too many arguments were passed");
			return false;
		}

		return true;
	}

	private void executeConnectCommand(String address, String port) {
		if (client.isConnected()) {
			appendTextLn("Connection already established. Use \"disconnect\"");
			return;
		}

		int portInt;
		try {
			portInt = Integer.parseInt(port);
		} catch (NumberFormatException e) {
			portInt = -1;
		}

		try {
			client.connect(address, portInt);
		} catch (Exception e) {
			appendTextLn(e.getMessage());
		}
	}

	private void executeDisconnectCommand() {
		if (!client.isConnected()) {
			appendTextLn("Can't disconnect: no connection was established");
			return;
		}

		try {
			client.disconnect();
			appendTextLn("Disconnecting from: " + client.getAddress());
		} catch (Exception e) {
			appendTextLn(e.getMessage());
		}
	}

	private void executeHireCommand(String type, String quantity) {
		EmployeeRepository employees = EmployeeRepository.getInstance();
		int quantityInt;
		try {
			quantityInt = Integer.parseInt(quantity);
		} catch (NumberFormatException e) {
			quantityInt = -1;
		}

		if (type.equals("managers")) {
			if (quantityInt >= 1) {
				synchronized (employees.employeesList()) {
					for (int i = 0; i < quantityInt; i++) {
						habitat.addEmployee(new Manager(
								ThreadLocalRandom.current()
										.nextDouble(0.0, habitat.habitatAreaWidth
										- Manager.getTexture().getWidth()),
								ThreadLocalRandom.current()
										.nextDouble(0.0, habitat.habitatAreaHeight
										- Manager.getTexture().getHeight()),
								System.nanoTime(),
								habitat.habitatAreaWidth,
								habitat.habitatAreaHeight
							)
						);
					}
				}
			} else {
				appendTextLn("Bad argument passed");
			}
		} else {
			appendTextLn("Bad argument passed");
		}
	}

	private void executeFireCommand(String type, String quantity) {
		EmployeeRepository employees = EmployeeRepository.getInstance();
		if (type.equals("managers")) {
			if (quantity.equals("all")) {
				synchronized (employees.employeesList()) {
					for (int i = 0; i < employees.size();) {
						Employee employee = employees.employeesList().get(i);
						if (employee instanceof Manager) {
							habitat.removeEmployee(employee);
						} else {
							i++;
						}
					}
				}
			} else {
				appendTextLn("Bad argument passed");
			}
		} else {
			appendTextLn("Bad argument passed");
		}
	}

	private void executeHelpCommand() {
		appendText(
                """
				connect <address> <port>: connect to ADDRESS:PORT server
				disconnect: close established connection
				hire <employee type> <quantity>: hire QUANTITY of TYPE
				fire <employee type> <quantity>: fire QUANTITY of TYPE
				help: display this message
				exit: exit terminal session
				"""
		);
	}

	private void executeExitCommand() {
		stage.close();
	}

	private void processCommand(String command) {
		String[] tokens = command.split(" ");

		switch (tokens[0]) {
		case "connect" -> {
			if (checkAmountOfArguments(tokens, 3, 3))
				executeConnectCommand(tokens[1], tokens[2]);
		}
		case "disconnect" -> {
			if (checkAmountOfArguments(tokens, 1, 1))
				executeDisconnectCommand();
		}
		case "hire" -> {
			if (checkAmountOfArguments(tokens, 3, 3))
				executeHireCommand(tokens[1], tokens[2]);
		}
		case "fire" -> {
			if (checkAmountOfArguments(tokens, 3, 3))
				executeFireCommand(tokens[1], tokens[2]);
		}
		case "help" -> {
			if (checkAmountOfArguments(tokens, 1, 1))
				executeHelpCommand();
		}
		case "exit" -> {
			if (checkAmountOfArguments(tokens, 1, 1))
				executeExitCommand();
		}
		default -> appendTextLn("Хорошее мнение, но есть одна " +
				"маленькая проблема. Я заложил мину в неизвестном месте " +
				"внутри твоего дома. Каждый твой шаг - рисковый ход");
		}
	}

	@FXML
	private void setKeyActions() {
		inputTextField.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				String command = inputTextField.getText().trim();
				inputTextField.clear();
				appendTextLn(">" + command);
				processCommand(command);
			}
		});
	}

	@FXML
	private void initialize() {
		setKeyActions();

		outputTextArea.setEditable(false);
		outputTextArea.setWrapText(true);
		inputTextField.requestFocus();
		outputTextArea.setFocusTraversable(false);
		appendTextLn("Welcome to the Le Terminal");
	}
}