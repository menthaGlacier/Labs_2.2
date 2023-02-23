package edu.uni.lab.model;

public abstract class Employee {
	protected static int period;

	public static int getPeriod() {
		return period;
	}

	public static void setPeriod(int period) {
		Employee.period = period;
	}
}