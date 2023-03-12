package edu.uni.lab.controller;

import edu.uni.lab.model.Simulation;

public class ControllerFactory {
	public Controller getController(Simulation simulation) {
		return new Controller(simulation);
	}
}