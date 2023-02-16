package edu.uni.lab;

public abstract class Employee {
	protected static int amount;
	protected static int period;

	public static int getAmount() {
		return amount;
	}

	public static void setAmount(int amount) {
		self.amount = amount;
	}

	public static int getPediod() {
		return period;
	}

	public static void setPeriod(int period) {
		self.period = period;
	}
}
