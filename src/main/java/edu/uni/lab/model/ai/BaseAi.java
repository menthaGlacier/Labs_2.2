package edu.uni.lab.model.ai;

import edu.uni.lab.model.EmployeeRepository;

public abstract class BaseAi implements Runnable {
	protected final EmployeeRepository employees;
	protected volatile Thread thread;
	protected volatile boolean running = false;
	private final Object runningMutex = new Object();

	public BaseAi() {
		this.employees = EmployeeRepository.getInstance();
		this.thread = new Thread(this);
		thread.start();
	}

	protected abstract void update();

	@Override
	public void run() {
		while (true) {
			try {
				synchronized (runningMutex) {
					while (!isRunning()) { runningMutex.wait(); }
				}
				update();
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void disable() {
		running = false;
	}

	public synchronized boolean isRunning() { return running; }

	public void enable() {
		synchronized (runningMutex) {
			running = true;
			runningMutex.notify();
		}
	}
}