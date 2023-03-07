package edu.uni.lab.model;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Simulation {
	private Habitat habitat;
	private boolean active = false;
	private AnimationTimer timer;

	private Label timeLabel;
	private Label devCountLabel;
	private Label mgrCountLabel;

	public Simulation(VBox vbox) {
		timeLabel = (Label) vbox.lookup("#timeLabel");
		mgrCountLabel = (Label) vbox.lookup("#mgrCountLabel");
		devCountLabel = (Label) vbox.lookup("#devCountLabel");
	}

	public void start(Pane habitatArea) {
		if (active) {
			return;
		}

		habitat = new Habitat(habitatArea, 880, 480);

		timer = new AnimationTimer() {
			private static final long nanoSecondsPerFrame = 1_000_000_000 / 60;
			private static final long startTime = System.nanoTime();
			private long lastTime = startTime;

			@Override
			public void handle(long timeNow) {
				if (timeNow - lastTime >= nanoSecondsPerFrame) {
					habitat.update((timeNow - startTime) / 1_000_000);
					lastTime = timeNow;
					timeLabel.setText("Time elapsed: " + ((timeNow - startTime) / 1_000_000_000L) + "s");
					devCountLabel.setText("Developers: " + habitat.getDevelopersCounter());
					mgrCountLabel.setText("Managers: " + habitat.getManagersCounter());
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