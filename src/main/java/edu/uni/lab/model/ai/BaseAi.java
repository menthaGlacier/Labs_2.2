package edu.uni.lab.model.ai;

import edu.uni.lab.model.EmployeeRepository;

public abstract class BaseAi implements Runnable {
	protected final EmployeeRepository employees;
	protected Thread thread;
	protected boolean running;

	public BaseAi() {
		this.employees = EmployeeRepository.getInstance();
	}

	public abstract void run();

	public synchronized void stop() throws InterruptedException {
		running = false;
		wait();
	}

	public synchronized void resume() {
		running = true;
		notify();
	}
}