package edu.uni.lab.client;

import edu.uni.lab.model.EmployeeRepository;
import edu.uni.lab.model.Habitat;
import edu.uni.lab.model.employees.Developer;
import edu.uni.lab.model.employees.Employee;
import edu.uni.lab.model.employees.Manager;
import edu.uni.lab.utility.dto.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class Client extends Thread {
	private final SimpleBooleanProperty transferringProperty;
	private Socket socket;
	private boolean isConnected;
	private final Object clientLock = new Object();
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Habitat habitat;
	private long startTime;
	private List<Integer> connectedClientsIds;

	public Client() {
		this.transferringProperty = new SimpleBooleanProperty(false);
		this.isConnected = false;
		this.habitat = null;
		connectedClientsIds = new LinkedList<>();
	}

	public boolean isConnected() {
		return isConnected;
	}

	public String getAddress() {
		return socket.getInetAddress().getHostName();
	}

	public List<Integer> getConnectedClientsIds() {
		return connectedClientsIds;
	}

	public void setHabitat(Habitat habitat) {
		this.habitat = habitat;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void connect(String address, int port) throws Exception {
		try {
			this.socket = new Socket(address, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			isConnected = true;
			if (this.getState() == State.NEW) {
				this.start();
			} else {
				synchronized (clientLock) {
					clientLock.notify();
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " " + e.getCause());
			e.printStackTrace();
			throw new Exception("Error occurred while trying to establish connection");
		}
	}

	@Override
	public void run() {
		while (true) {
			synchronized (clientLock) {
				while (!isConnected) {
					try {
						clientLock.wait();
					} catch (InterruptedException e) {
						System.out.println(e.getMessage() + " " + e.getCause());
						e.printStackTrace();
					}
				}
			}

			try {
				while (!socket.isClosed() && isConnected) {
					Object dto = in.readObject();

					if (dto instanceof EmployeesRequestDto requestDto) {
						transferringProperty.set(true);
						System.out.println("Got request for " +
								requestDto.getEmployeeClass() +
								" from " + requestDto.getToClientId()
						);

						synchronized (EmployeeRepository.getInstance().employeesList()) {
							LinkedList<Employee> employees = EmployeeRepository.getInstance().employeesList();
							LinkedList<EmployeeDto> dtoList = new LinkedList<>();

							Predicate<Employee> filter =
									(requestDto).getEmployeeClass().equals("manager") ?
											e -> e instanceof Manager : e -> e instanceof Developer;

							for (Employee employee : employees) {
								if (filter.test(employee)) {
									dtoList.add(employee.createDto());
								}
							}

							out.writeObject(new EmployeesListDto(dtoList,
									requestDto.getToClientId()));
						}
					} else if (dto instanceof EmployeesListDto listDto) {
						System.out.println("adding requested employees");
						Platform.runLater(() -> {
									synchronized (EmployeeRepository.getInstance().employeesList()) {
										for (EmployeeDto employeeDto : listDto.employeesDtoList()) {
											long creationTime = (System.nanoTime() - startTime) / 1_000_000;
											habitat.addEmployee(employeeDto instanceof ManagerDto ?
													new Manager((ManagerDto) employeeDto, creationTime,
															habitat.habitatAreaWidth, habitat.habitatAreaHeight) :
													new Developer((DeveloperDto) employeeDto, creationTime,
															habitat.habitatAreaWidth, habitat.habitatAreaHeight)
											);
										}
									}
								}
						);
					} else if (dto instanceof ConnectedClientsIdListDto) {
						connectedClientsIds = ((ConnectedClientsIdListDto) dto).idList();
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage() + " " + e.getCause());
				e.printStackTrace();
			}
		}
	}

	public void requestEmployees(String employeeClass, int toClientId) {
		System.out.println("known connections" + connectedClientsIds);
		System.out.println("Requesting " + employeeClass + " from " + toClientId);
		try {
			EmployeesRequestDto requestDto = new EmployeesRequestDto(employeeClass, toClientId);
			out.writeObject(requestDto);
		} catch (Exception e) {
			System.out.println(e.getMessage() + " " + e.getCause());
			e.printStackTrace();
		}
	}

	public void disconnect() throws Exception {
		try {
			out.writeObject(new DisconnectRequestDto());
			isConnected = false;
		} catch (Exception e) {
			throw new Exception(e.getMessage() + " " + e.getCause());
		}
	}
}