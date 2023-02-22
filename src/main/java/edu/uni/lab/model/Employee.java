package edu.uni.lab.model;

public abstract class Employee {
    protected static int amount;
    protected static int period;

    public static int getAmount() {
        return amount;
    }

    public static void setAmount(int amount) {
        Employee.amount = amount;
    }

    public static int getPeriod() {
        return period;
    }

    public static void setPeriod(int period) {
        Employee.period = period;
    }
}