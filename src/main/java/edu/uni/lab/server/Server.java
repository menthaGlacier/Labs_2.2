package edu.uni.lab.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {
	private static final List<Session> sessions = new LinkedList<>();

	private synchronized void addSession(Session session) {
		sessions.add(session);
	}

	private synchronized void removeSession(Session session) {
		sessions.remove(session);
	}

	public synchronized List<Session> getSessions() {
		return sessions;
	}

	public static void main(String[] args) {
		int port = 7182;

		if (args.length == 1) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.err.println("Invalid port provided. " +
						"Using default port: " + port
				);
			}
		} else {
			System.err.println("Invalid args provided. " +
					"Using default port: " + port
			);
		}

		try (ServerSocket serverSocket = new ServerSocket(port)) {
			while (true) {
				Socket clientSocket = serverSocket.accept();
				sessions.add(new Session(clientSocket));
				System.out.println(sessions); // DEBUG!!!
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}