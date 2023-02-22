package edu.uni.lab.model;

import edu.uni.lab.model.Developer;
import edu.uni.lab.model.Manager;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Habitat {
    private Employee[] employees;
    private int employeesCounter;
    private int width, height;
    Timer timer;

    public Habitat(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void startSimulation() {
        employees = new Employee[100];
        employeesCounter = 0;
        width = 640;
        height = 640;

        Random random = new Random();
        Developer.setPeriod(random.nextInt(120) + 13); // Magic number
        Developer.setProbability(random.nextDouble() * 0.9 + 0.1);
        Manager.setPeriod(random.nextInt(120) + 13);
        Manager.setRatio(random.nextDouble() * 0.9 + 0.1);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (random.nextDouble() <= Developer.getProbability()) {
                    employees[employeesCounter] = new Developer();
                    employeesCounter += 1;
                }
            }
        }, 0, Developer.getPeriod());
    }

    public void stopSimulation() {
    }

    public void update() {
    }
}
