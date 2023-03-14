package edu.uni.lab.controller;

import edu.uni.lab.model.Simulation;

public class MainControllerFactory {
	public MainController getController(Simulation simulation) {
		return new MainController(simulation);
	}
}