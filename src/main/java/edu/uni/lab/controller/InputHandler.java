package edu.uni.lab.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import edu.uni.lab.model.Habitat;

public class InputHandler implements EventHandler<KeyEvent> {
	private final Habitat habitat;

	public InputHandler(Habitat habitat) {
		this.habitat = habitat;
	}

	@Override
	public void handle(KeyEvent event) {
		switch (event.getCode()) {
		case B:
			habitat.startSimulation();
			break;
		case E:
			habitat.stopSimulation();
			break;
		case T:
			// view.toggleTime();
			break;
		default:
			break;
		}
	}
}