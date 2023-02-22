package edu.uni.lab.model;

public class Manager extends Employee {
    private static double ratio;

    public Manager() {
        Manager.amount += 1;
    }

    public static double getRatio() {
        return ratio;
    }

    public static void setRatio(double ratio) {
        Manager.ratio = ratio;
    }
}