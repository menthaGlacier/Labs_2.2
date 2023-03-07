package edu.uni.lab.model;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

public class Simulation {
	private Habitat habitat;
	private boolean active = false;
	private AnimationTimer timer;

	public Simulation() {
	}

	public void start(Pane habitatArea) {
		if (active) {
			return;
		}

		habitat = new Habitat(habitatArea);

		timer = new AnimationTimer() {
			private static final long nanoSecondsPerFrame = 1_000_000_000 / 60;
			private static final long startTime = System.nanoTime();
			private long lastTime = startTime;

			@Override
			public void handle(long timeNow) {
				if (timeNow - lastTime >= nanoSecondsPerFrame) {
					habitat.update((timeNow - startTime) / 1_000_000);
					lastTime = timeNow;

					// TODO Statistics
				}
			}
		};

		timer.start();
		active = true;
	}

	public void stop() {
		if (!active) {
			return;
		}

		timer.stop();
		active = false;
	}

	public Habitat getHabitat() {
		return habitat;
	}

	public boolean isActive() {
		return active;
	}
}