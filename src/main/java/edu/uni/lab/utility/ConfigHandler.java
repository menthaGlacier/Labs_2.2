package edu.uni.lab.utility;

import edu.uni.lab.model.ai.BaseAi;
import edu.uni.lab.model.employees.Developer;
import edu.uni.lab.model.employees.Manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigHandler {
	public void save(BaseAi developerAi, BaseAi managerAi) throws IOException {
		FileWriter fileWriter = new FileWriter("config.txt");

		fileWriter.write("developer_period=" + Developer.getPeriod() + "\n");
		fileWriter.write("developer_probability=" + Developer.getProbability() + "\n");
		fileWriter.write("developer_lifetime=" + Developer.getLifeTime() + "\n");
		fileWriter.write("developer_ai_priority=" + developerAi.getPriority() + "\n");
		fileWriter.write("developer_ai_activity=" + developerAi.isRunning() + "\n");
		fileWriter.write("manager_period=" + Manager.getPeriod() + "\n");
		fileWriter.write("manager_ratio=" + Manager.getRatio() + "\n");
		fileWriter.write("manager_lifetime=" + Manager.getLifeTime() + "\n");
		fileWriter.write("manager_ai_priority=" + managerAi.getPriority() + "\n");
		fileWriter.write("manager_ai_activity=" + managerAi.isRunning() + "\n");

		fileWriter.close();
	}

	public void load(BaseAi developerAi, BaseAi managerAi) throws IOException {
		FileReader fileReader = new FileReader("config.txt");
		BufferedReader reader = new BufferedReader(fileReader);
		String readLine;
		while ((readLine = reader.readLine()) != null) {
			String[] parts = readLine.split("=");

			if (parts.length == 2) {
				String fieldName = parts[0];
				String fieldValue = parts[1];

				switch (fieldName) {
				case "developer_period" ->
						Developer.setPeriod(Long.parseLong(fieldValue));
				case "developer_probability" ->
						Developer.setProbability(Double.parseDouble(fieldValue));
				case "developer_lifetime" ->
						Developer.setLifeTime(Long.parseLong(fieldValue));
				case "developer_ai_priority" ->
						developerAi.setPriority(Integer.parseInt(fieldValue));
				case "developer_ai_activity" -> {
					if (Boolean.parseBoolean(fieldValue)) {
						developerAi.enable();
					} else {
						developerAi.disable();
					}}
				case "manager_period" ->
						Manager.setPeriod(Long.parseLong(fieldValue));
				case "manager_ratio" ->
						Manager.setRatio(Double.parseDouble(fieldValue));
				case "manager_lifetime" ->
						Manager.setLifeTime(Long.parseLong(fieldValue));
				case "manager_ai_priority" ->
						managerAi.setPriority(Integer.parseInt(fieldValue));
				case "manager_ai_activity" -> {
					if (Boolean.parseBoolean(fieldValue)) {
						managerAi.enable();
					} else {
						managerAi.disable();
					}
				}}
			}
		}

		reader.close();
		fileReader.close();
	}
}