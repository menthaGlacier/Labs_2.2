package edu.uni.lab.server;

import edu.uni.lab.utility.dto.BaseDto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Session implements Runnable {
	private final Socket socket;
	private final Thread thread;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public Session(Socket socket) {
		this.socket = socket;

		try {
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.thread = new Thread(this);
		thread.start();
	}

	public void sendObject(BaseDto object) {
		try {
			out.writeObject(object);
			out.flush();
		} catch (IOException e) {
			System.out.println("Error sending object: " + e.getMessage());
			close();
		}
	}

	public BaseDto receiveObject() {
		try {
			return (BaseDto) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error receiving object: " + e.getMessage());
			close();
		}

		return null;
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
		Object data = receiveObject();
	}
}