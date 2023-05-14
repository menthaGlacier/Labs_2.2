package edu.uni.lab.model.ai;

import edu.uni.lab.model.employees.Developer;
import edu.uni.lab.model.employees.Employee;

import java.util.concurrent.ThreadLocalRandom;

public class DeveloperAi extends BaseAi {
	//private final static long DIRECTION_CHANGE_RATE_MAX = 10_000;

	public DeveloperAi() { super(); }

	protected void update() {
		synchronized (employees.employeesList()) {
			for (Employee iterator : employees.employeesList()) {
				if (iterator instanceof Developer) { iterator.move(); }
			}
		}
	}



	/*
	public Developer.HorizontalDirection getRandomHorizontalDirection() {
		return Developer.HorizontalDirection.values()[ThreadLocalRandom
			.current().nextInt(Developer.HorizontalDirection.values().length)];
	}

	public Developer.VerticalDirection getRandomVerticalDirection() {
		return Developer.VerticalDirection.values()[ThreadLocalRandom
			.current().nextInt(Developer.VerticalDirection.values().length)];
	}

	public long getDirectionChangeRate() {
		return directionChangeRate;
	}

	public void setDirectionChangeRate(long directionChangeRate) {
		this.directionChangeRate = directionChangeRate;
	}
	 */
}