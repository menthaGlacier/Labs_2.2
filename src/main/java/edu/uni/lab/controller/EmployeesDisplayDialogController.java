package edu.uni.lab.controller;

import edu.uni.lab.model.EmployeeRepository;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.util.HashMap;
import java.util.Map;

public class EmployeesDisplayDialogController {
	private static class TableData {
		private final IntegerProperty id;
		private final LongProperty creationTime;

		public TableData(int id, long creationTime) {
			this.id = new SimpleIntegerProperty(id);
			this.creationTime = new SimpleLongProperty(creationTime);
		}

		public IntegerProperty idProperty() {
			return id;
		}

		public LongProperty creationTimeProperty() {
			return creationTime;
		}

		public int getId() {
			return id.getValue();
		}

		public long getCreationTime() {
			return creationTime.getValue();
		}
	}

	private final Stage stage;
	private final HashMap<Integer, Long> dataMap;

	@FXML
	private TableView<TableData> tableView;
	@FXML
	private TableColumn<TableData, Integer> idColumn;
	@FXML
	private TableColumn<TableData, Long> creationTimeColumn;

	public EmployeesDisplayDialogController(Stage stage) {
		this.stage = stage;
		this.dataMap = EmployeeRepository.getInstance().getEmployeesCreationTime();
	}

	@FXML
	private void initialize() {
		ObservableList<TableData> items = FXCollections.observableArrayList();
		for (Map.Entry<Integer, Long> entry : dataMap.entrySet()) {
			items.add(new TableData(entry.getKey(), entry.getValue()));
		}

		tableView.setItems(items);
		idColumn.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
		creationTimeColumn.setCellValueFactory(cell -> cell.getValue().creationTimeProperty().asObject());
	}

	@FXML
	private void close() {
		stage.close();
	}
}