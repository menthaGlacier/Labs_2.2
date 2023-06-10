package edu.uni.lab.client;


import edu.uni.lab.model.EmployeeRepository;
import edu.uni.lab.model.employees.Developer;
import edu.uni.lab.model.employees.Employee;
import edu.uni.lab.model.employees.Manager;
import edu.uni.lab.utility.dto.EmployeesListDto;
import edu.uni.lab.utility.dto.EmployeesRequestDto;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Client extends Thread {
	SimpleBooleanProperty transferingProperty = new SimpleBooleanProperty(false);
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public Client() {
	}

	public boolean connect(String address, int port) {
		try {
			this.socket = new Socket(address, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket.isConnected();
	}

	@Override
	public void run() {
		try {
			while (!socket.isClosed()) {
				Object dto = in.readObject();

				if (dto instanceof EmployeesRequestDto) {
					transferingProperty.set(true);

					synchronized (EmployeeRepository.getInstance().employeesList()) {
						LinkedList<Employee> list, employees = EmployeeRepository.getInstance().employeesList();

						Predicate<Employee> filter =
								((EmployeesRequestDto) dto).getEmployeeClass().equals("manager") ?
								e -> e instanceof Manager :
								e -> e instanceof Developer;

						list = new LinkedList<>(employees.stream().filter(filter)
									.collect(Collectors.toList()));

						out.writeObject(new EmployeesListDto(list));
					}

				} else if (dto instanceof EmployeesListDto && (((EmployeesListDto) dto).employeesList().size() > 0)) {
						synchronized (EmployeeRepository.getInstance().employeesList()) {

							EmployeeRepository employees = EmployeeRepository.getInstance();
							for (int i = 0; i < employees.size(); ++i) {
								// Employee iteratingEmployee = employees.employeesList().get(i);


								// if (elapsedTime - iteratingEmployee.getCreationTime() >= lifeTime) {
								// 	removeEmployee(iteratingEmployee);
								// 	continue;
								// }

								// ++i;
							}
						}
					}
				}
		} catch (Exception e) {

		}
	}

	public void requestEmployees(String employeeClass, int toClientId) {
		try {
			EmployeesRequestDto requestDto = new EmployeesRequestDto(employeeClass, toClientId);
			out.writeObject(requestDto);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}

	public void disconnect() {

	}

	// DEBUG!!!
	public static void main(String[] args) {
		Client client = new Client();
		client.connect("localhost", 7182);
	}
}