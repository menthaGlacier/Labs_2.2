package edu.uni.lab.controller;

import edu.uni.lab.model.EmployeeRepository;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShowObjectsDialogController {
	private static class TableData {
		private final ObjectProperty<UUID> id;
		private final LongProperty creationTime;

		public TableData(UUID id, long creationTime) {
			this.id = new SimpleObjectProperty<>(id);
			this.creationTime = new SimpleLongProperty(creationTime);
		}

		public ObjectProperty<UUID> idProperty() {
			return id;
		}

		public LongProperty creationTimeProperty() {
			return creationTime;
		}

		public UUID getId() {
			return id.getValue();
		}

		public long getCreationTime() {
			return creationTime.getValue();
		}
	}

	private final Stage stage;
	private final HashMap<UUID, Long> dataMap;

	@FXML
	private TableView<TableData> tableView;
	@FXML
	private TableColumn<TableData, UUID> idColumn;
	@FXML
	private TableColumn<TableData, Long> creationTimeColumn;

	public ShowObjectsDialogController(Stage stage) {
		this.stage = stage;
		this.dataMap = EmployeeRepository.getInstance().getEmployeesCreationTime();
	}

	@FXML
	private void initialize() {
		ObservableList<TableData> items = FXCollections.observableArrayList();
		for (Map.Entry<UUID, Long> entry : dataMap.entrySet()) {
			items.add(new TableData(entry.getKey(), entry.getValue()));
		}

		tableView.setItems(items);
		idColumn.setCellValueFactory(cell -> cell.getValue().idProperty());
		creationTimeColumn.setCellValueFactory(cell -> cell.getValue().creationTimeProperty().asObject());
	}

	@FXML
	private void close() {
		stage.close();
	}
}