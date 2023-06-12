package edu.uni.lab.controller;

import edu.uni.lab.client.Client;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ClientsDialogController {
	private static class TableData {
		private final ObjectProperty<String> ip;
		private final ObjectProperty<Client> client;

		public TableData(String ip, Client client) {
			this.ip = new SimpleObjectProperty<>(ip);
			this.client = new SimpleObjectProperty<>(client);
		}

		public ObjectProperty<String> ip() {
			return ip;
		}

		public ObjectProperty<Client> client() {
			return client;
		}

		public String getId() {
			return ip.getValue();
		}

		public Client getClient() {
			return client.getValue();
		}
	}

	private final Stage stage;
	private final HashMap<String, Client> dataMap;

	@FXML
	private TableView<TableData> tableView;
	@FXML
	private TableColumn<TableData, String> ipColumn;
	@FXML
	private TableColumn<TableData, Button> exchangeEmployeesColumn;

	public ClientsDialogController(Stage stage, LinkedList<Client> clients) {
		this.stage = stage;
		this.dataMap = new HashMap<>();

		long dud = 0; // DEBUG!!!
		for (Client client : clients) {
			dataMap.put(String.valueOf(dud), client);
			dud += 1; // DEBUG!!!
		}
	}

	@FXML
	private void initialize() {
		ObservableList<TableData> items = FXCollections.observableArrayList();
		for (Map.Entry<String, Client> entry : dataMap.entrySet()) {
			items.add(new TableData(entry.getKey(), entry.getValue()));
		}

		tableView.setItems(items);
		ipColumn.setCellValueFactory(cell -> cell.getValue().ip());
		this.exchangeEmployeesColumn.setCellValueFactory(cell -> {
			Button button = new Button("Exchange employees");
			button.setOnAction(event -> {}); // TODO
			return new SimpleObjectProperty<>(button);
		});
	}

	@FXML
	private void close() {
		stage.close();
	}
}