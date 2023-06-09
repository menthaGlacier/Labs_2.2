package edu.uni.lab.client;

import edu.uni.lab.server.Session;

import java.io.IOException;
import java.net.Socket;

public class Client {
	private Session session;
	private boolean connected;

	public Client() {
		this.connected = false;
	}

	public void connect(String address, int port) {
		try {
			this.session = new Session(new Socket(address, port));
			this.connected = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}