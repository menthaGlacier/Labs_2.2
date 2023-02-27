package edu.uni.lab.model;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

public class Simulation {
	private Habitat habitat;
	private boolean active = false;
	private AnimationTimer timer;

	public void start(Pane habitatArea) {
		if (active) { return; }

		habitat = new Habitat(habitatArea);

		timer = new AnimationTimer() {
			private long startingTime = System.nanoTime();
			private long lastTime = startingTime;
			private final long nanosPerFrame = 1_000_000_000 / 60;
			@Override
			public void handle(long timeNow) {
				if (timeNow - lastTime >= nanosPerFrame) {
					habitat.update(timeNow-startingTime);
					lastTime = timeNow;
				}
			}
		};

		timer.start();
		active = true;
	}

	public void stop() {
		if (!active) { return; }

		timer.stop();
		active = false;
	}

	public Habitat getHabitat() { return habitat; }

	public boolean isActive() { return active; }
}
