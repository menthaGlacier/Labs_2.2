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
			e.printStackTrace();
		}
	}

//	public BaseDto receiveObject() {
//		try {
//			return (BaseDto) in.readObject();
//		} catch (IOException | ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}

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

			}

		} catch (Exception e) {
		}
	}
}