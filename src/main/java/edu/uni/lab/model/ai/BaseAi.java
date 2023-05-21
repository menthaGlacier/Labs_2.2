package edu.uni.lab.model.ai;

import edu.uni.lab.model.EmployeeRepository;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class BaseAi implements Runnable {
	protected final EmployeeRepository employees;
	protected final BooleanProperty running;
	private final Object runningMutex = new Object();
	protected Thread thread;

	public BaseAi() {
		this.employees = EmployeeRepository.getInstance();
		this.thread = new Thread(this);
		this.running = new SimpleBooleanProperty(false);
		thread.start();
	}

	protected abstract void update();

	@Override
	public void run() {
		while (true) {
			try {
				synchronized (runningMutex) {
					while (!isRunning()) {
						runningMutex.wait();
					}
				}

				update();
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setPriority(int priority) {
		thread.setPriority(priority);
	}

	public synchronized void disable() {
		running.set(false);
	}

	public synchronized BooleanProperty running() {
		return running;
	}

	public synchronized boolean isRunning() {
		return running.get();
	}

	public void enable() {
		synchronized (runningMutex) {
			running.set(true);
			runningMutex.notify();
		}
	}
}