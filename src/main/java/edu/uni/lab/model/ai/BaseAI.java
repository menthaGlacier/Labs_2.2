package edu.uni.lab.model.ai;

public abstract class BaseAI implements Runnable {
	protected boolean running;

	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		notify();
	}

	public synchronized void resume() {
		running = true;
		notify();
	}

	protected abstract void move();

	@Override
	public void run() {
		running = true;
		while (running) {
			move();
		}

		synchronized (this) {
			while (!running) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}