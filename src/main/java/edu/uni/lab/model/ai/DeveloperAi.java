package edu.uni.lab.model.ai;

import edu.uni.lab.model.IBehaviour;
import edu.uni.lab.model.employees.Developer;
import edu.uni.lab.model.employees.Employee;

import java.util.concurrent.ThreadLocalRandom;

public class DeveloperAi extends BaseAi implements IBehaviour {
	private final static long DIRECTION_CHANGE_RATE_MAX = 10_000;
	private long lastDirectionChange;
	private long directionChangeRate;

	public DeveloperAi() {
		this.thread = new Thread(this);
		thread.start();
		lastDirectionChange = 0;
		directionChangeRate = ThreadLocalRandom.current()
				.nextLong(DIRECTION_CHANGE_RATE_MAX);
	}

	@Override
	public void move() {
		synchronized (employees.employeesList()) {
			for (Employee iterator : employees.employeesList()) {
				if (iterator instanceof Developer) {
					iterator.setX(iterator.getX()
							+ ((Developer) iterator).getHorizontalDirection()
							.getValue()
					);

					iterator.setY(iterator.getY()
							+ ((Developer) iterator).getVerticalDirection()
							.getValue()
					);
				}
			}
		}
	}

	@Override
	public void run() {
		while (running) {
			move();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void changeDirection() {
		long time = System.currentTimeMillis();
		synchronized (employees.employeesList()) {
			for (Employee iterator : employees.employeesList()) {
				if (iterator instanceof Developer
						&& time - lastDirectionChange >= directionChangeRate) {
					((Developer) iterator).setHorizontalDirection(
							getRandomHorizontalDirection());
					((Developer) iterator).setVerticalDirection(
							getRandomVerticalDirection());
					lastDirectionChange = time;
				}
			}
		}
	}

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
}