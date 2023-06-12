package edu.uni.lab.controller;

import edu.uni.lab.client.Client;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import java.util.List;
import java.util.Map;

public class ClientsDialogController {
	private static class TableData {
		private final IntegerProperty id;

		public TableData(int id) {
			this.id = new SimpleIntegerProperty(id);
		}

		public IntegerProperty id() {
			return id;
		}

		public int getId() {
			return id.getValue();
		}
	}

	private final Stage stage;
	private final Client client;
	private final List<Integer> clientsIds;

	@FXML
	private TableView<TableData> tableView;
	@FXML
	private TableColumn<TableData, Integer> idColumn;
	@FXML
	private TableColumn<TableData, Button> exchangeEmployeesColumn;

	public ClientsDialogController(Stage stage, Client client, LinkedList<Integer> clientsIds) {
		this.stage = stage;
		this.client = client;
		this.clientsIds = clientsIds;
	}

	@FXML
	private void initialize() {
		ObservableList<TableData> items = FXCollections.observableArrayList();
		for (Integer id : clientsIds) {
			items.add(new TableData(id));
		}

		tableView.setItems(items);
		idColumn.setCellValueFactory(cell -> cell.getValue().id().asObject());
		this.exchangeEmployeesColumn.setCellValueFactory(cell -> {
			Button button = new Button("Exchange employees");
			button.setOnAction(event -> {
				client.requestEmployees("developers", cell.getValue().getId());
			});

			return new SimpleObjectProperty<>(button);
		});
	}

	@FXML
	private void close() {
		stage.close();
	}
}