package edu.uni.lab.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {
	private static final List<Session> sessions = new LinkedList<>();
	private static int count = 0;
	private synchronized void addSession(Session session) {
		sessions.add(session);
	}

	private synchronized void removeSession(Session session) {
		sessions.remove(session);
	}

	public static List<Session> getSessions() {
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

		try (ServerSocket serverSocket = new ServerSocket(port, 0, InetAddress.getByName(null))) {
			while (true) {
				Socket clientSocket = serverSocket.accept();
				Session newSession = new Session(clientSocket, count++);
				newSession.start();
				sessions.add(newSession);
				for (Session session : sessions) {
					session.sendConnectedClientsIdList();
				}
				System.out.println("Connected: "); // DEBUG!!!
				for (Session session : sessions) {
					System.out.println("Session ID: " + session.getSessionId() + " IP: " + session.getIp());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}