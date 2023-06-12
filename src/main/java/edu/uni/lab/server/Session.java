package edu.uni.lab.server;

import edu.uni.lab.utility.dto.ConnectedClientsIdListDto;
import edu.uni.lab.utility.dto.EmployeesListDto;
import edu.uni.lab.utility.dto.EmployeesRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Session extends Thread {
	private final Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private int sessionId;

	public Session(Socket socket, int sessionId) {
		this.socket = socket;
		this.sessionId = sessionId;

		try {
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
			sendConnectedClientsIdList();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getIp() {
		return socket.getInetAddress().getHostAddress();
	}

	public int getSessionId() {
		return sessionId;
	}

	public void sendConnectedClientsIdList() {
		try {
			List<Session> sessions = Server.getSessions();
			List <Integer> idList = new ArrayList<>();
			for (Session session : sessions) {
				if (session != this) {
					idList.add(session.sessionId);
				}
			}
			out.writeObject(new ConnectedClientsIdListDto(idList));
		}
		catch (IOException exception){
			System.out.println(exception.getMessage() + "\n" + exception.getCause());
			exception.printStackTrace();
		}
	}

	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (!socket.isClosed()) {
				Object dto = in.readObject();
				System.out.println("got some dto");
				if (dto instanceof EmployeesRequestDto requestDto) {
					System.out.println("Requesting " + requestDto.getEmployeeClass() + " from " + requestDto.getToClientId());
					Server.getSessions().get(requestDto.getToClientId()).out.writeObject(new EmployeesRequestDto(
							requestDto.getEmployeeClass(), sessionId));
					out.writeObject(new EmployeesRequestDto(
							requestDto.getEmployeeClass().equals("manager") ?
									"developer" : "manager", requestDto.getToClientId()));
				} else if (dto instanceof EmployeesListDto listDto) {
					System.out.println("sending to " + listDto.getToClientId());
					Server.getSessions().get(listDto.getToClientId()).out.writeObject(listDto);
				}
			}

		} catch (Exception e) {
				System.out.println(e.getMessage() + " " + e.getCause());
				e.printStackTrace();
		}
	}
}