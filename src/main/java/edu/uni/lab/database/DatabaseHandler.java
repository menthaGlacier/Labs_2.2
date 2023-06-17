package edu.uni.lab.database;

import edu.uni.lab.model.EmployeeRepository;
import edu.uni.lab.model.employees.Employee;
import edu.uni.lab.model.employees.Manager;

import java.io.File;
import java.sql.*;
import java.util.LinkedList;

public class DatabaseHandler {
    private static final String DB_NAME = "lab_employees.db";
    private static Connection connection;

    private static void executeQuery(String query) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void connect() {
        try {
            String url = "jdbc:sqlite:" + DB_NAME;
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

            createDatabase();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void closeDatabase() {
        try{
            connection.close();
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    private static void removeDatabase() {
        try {
            connection.close();
            if (!new File(DB_NAME).delete()) {
                System.out.println("Failed to delete the db file");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createDatabase() {
        executeQuery("CREATE TABLE IF NOT EXISTS " +
                "employees (" +
                "id INT, type TEXT, " +
                "x REAL, y REAL" +
                ")"
        );
    }

    private static void dropTables() {
        executeQuery("DROP TABLE IF EXISTS employees");
    }

    private static void clearDatabase() {
        executeQuery("DELETE FROM employees");
    }

    private static void addEmployee(Employee employee, int id) {
        String type;
        if (employee instanceof Manager) {
            type = "manager";
        } else {
            type = "developer";
        }

        executeQuery("INSERT INTO employees (id, type, x, y) VALUES " +
                String.format("('%d', '%s', '%f', '%f')",
                        id, type, employee.getX(), employee.getY()
                )
        );
    }

    private static void removeEmployee(int id) {
        executeQuery("DELETE FROM employees WHERE id = " + id);
    }

    public static synchronized void saveEmployeesToDatabase() {
        LinkedList<Employee> employees =
                EmployeeRepository.getInstance().employeesList();

        clearDatabase();
        int id = 0;
        for (Employee employee : employees) {
            addEmployee(employee, id++);
        }
    }

    public static synchronized ResultSet loadEmployeesFromDatabase() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees");
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}