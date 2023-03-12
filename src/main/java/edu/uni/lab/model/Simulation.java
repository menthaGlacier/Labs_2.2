package edu.uni.lab.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Simulation {
	private Habitat habitat;
	private boolean active = false;
	private AnimationTimer timer;
	private final StringProperty timeLabelText;
	private final StringProperty countersLabelText;

	public Simulation() {
		timeLabelText = new SimpleStringProperty("Time: 0s.");
		countersLabelText = new SimpleStringProperty("Developers: 0" + "\n" +
														"Managers: 0");
	}

	public void start(Pane habitatArea) {
		if (active) {
			return;
		}

		habitat = new Habitat(habitatArea);
		timer = new AnimationTimer() {
			private static final long nanoSecondsPerFrame = 1_000_000_000 / 60;
			private final long startTime = System.nanoTime();
			private long lastTime = startTime;

			@Override
			public void handle(long timeNow) {
				if (timeNow - lastTime >= nanoSecondsPerFrame) {
					habitat.update((timeNow - startTime) / 1_000_000);
					lastTime = timeNow;
					timeLabelText.set("Time: "
									+ (lastTime - startTime) / 1_000_000_000
									+ "s."
					);

					countersLabelText.set("Developers: "
									+ habitat.getDevelopersCounter() + "\n"
									+ "Managers: "
									+ habitat.getManagersCounter()
					);
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

	public void bindStatisticsLabels(Label timeLabel, Label countersLabel) {
		timeLabel.textProperty().bind(timeLabelText);
		countersLabel.textProperty().bind(countersLabelText);
	}

	public Habitat getHabitat() {
		return habitat;
	}

	public boolean isActive() {
		return active;
	}
}