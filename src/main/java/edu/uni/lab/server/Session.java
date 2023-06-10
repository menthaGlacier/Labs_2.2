package edu.uni.lab.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Session extends Thread {
	private final Socket socket;
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
	}

	public void sendObject(Object object) {
		try {
			out.writeObject(object);
			out.flush();
		} catch (IOException e) {
			System.out.println("Error sending object: " + e.getMessage());
			close();
		}
	}

	public Object receiveObject() {
		try {
			return in.readObject();
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
		try {
			while (!socket.isClosed()) {
				Object data = in.readObject();
			}

		} catch (Exception ignored) {}
	}
}