package edu.uni.lab.client;

import edu.uni.lab.model.EmployeeRepository;
import edu.uni.lab.model.Habitat;
import edu.uni.lab.model.employees.Developer;
import edu.uni.lab.model.employees.Employee;
import edu.uni.lab.model.employees.Manager;
import edu.uni.lab.utility.dto.ConnectedClientsIdListDto;
import edu.uni.lab.utility.dto.EmployeesListDto;
import edu.uni.lab.utility.dto.EmployeesRequestDto;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Client extends Thread {
	SimpleBooleanProperty transferringProperty = new SimpleBooleanProperty(false);
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Habitat habitat = null;
	private List<Integer> connectedClientsIds;

	public Client() {
	}

	public void setHabitat(Habitat habitat) {
		this.habitat = habitat;
	}

	public void connect(String address, int port) {
		try {
			this.socket = new Socket(address, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return socket.isConnected();
	}

	@Override
	public void run() {
		try {
			while (!socket.isClosed()) {
				Object dto = in.readObject();

				if (dto instanceof EmployeesRequestDto requestDto) {
					transferringProperty.set(true);
					System.out.println("Got request for " + requestDto.getEmployeeClass() + " from " + requestDto.getToClientId());
					synchronized (EmployeeRepository.getInstance().employeesList()) {
						LinkedList<Employee> list, employees = EmployeeRepository.getInstance().employeesList();

						Predicate<Employee> filter =
								(requestDto).getEmployeeClass().equals("manager") ?
								e -> e instanceof Manager :
								e -> e instanceof Developer;

						list = employees.stream().filter(filter).collect(Collectors.toCollection(LinkedList::new));

						out.writeObject(new EmployeesListDto(list, requestDto.getToClientId()));
					}

				} else if (dto instanceof EmployeesListDto) {
					System.out.println("adding requested employees");
					synchronized (EmployeeRepository.getInstance().employeesList()) {
						for (Employee employee : ((EmployeesListDto)dto).employeesList()) {
							employee.resetImageView();
							habitat.addEmployee(employee);
						}
					}
				} else if (dto instanceof ConnectedClientsIdListDto) {
					connectedClientsIds = ((ConnectedClientsIdListDto)dto).idList();
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " " + e.getCause());
			e.printStackTrace();
		}
	}

	public void requestEmployees(String employeeClass, int toClientId) {
		System.out.println("known connections" + connectedClientsIds);
		System.out.println("Requesting " + employeeClass + " from " + toClientId);
		try {
			EmployeesRequestDto requestDto = new EmployeesRequestDto(employeeClass, toClientId);
			out.writeObject(requestDto);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}

	public void disconnect() {

	}
}