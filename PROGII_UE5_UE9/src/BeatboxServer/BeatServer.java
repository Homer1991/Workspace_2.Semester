package BeatboxServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class BeatServer {
	BeatServerGui gui;
	ArrayList<PrintWriter> clientOutputStreams;
	ArrayList<String> log;
	ServerSocket serverSock;
	boolean running;

	public BeatServer() {
		try {
			gui = new BeatServerGui();
		}
		catch (Exception e) {
			gui = null;
		}
		log = new ArrayList<String>();
		clientOutputStreams = new ArrayList<PrintWriter>();
		running = true;
		startServer();
	}

	private void startServer() {
		trace("Erstelle Server an Port: 3857");
		try {
			serverSock = new ServerSocket(3857);

			while (running) {
				trace("Warte auf Client");
				Socket sock = serverSock.accept();
				trace("Abfrage von Client");
				PrintWriter outStream = new PrintWriter(sock.getOutputStream());
				writerInitialisieren(outStream);
				clientOutputStreams.add(outStream);
				Thread t = new Thread(new ClientJob(sock));
				t.start();
			}
			trace("Beende Server");
			serverSock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writerInitialisieren(PrintWriter os) {
		trace("Initialisiere neuen Client");
		for (String m : log) {
			sendMessage(m, os);
			trace("Sende Message: \n" + "       " + m);
		}
	}

	private void sendMessageAll(String m) {
		log.add(m);
		trace("Sende Message an alle: \n" + "       " + m);

		for (PrintWriter out : clientOutputStreams) {
			out.println(m);
			out.flush();
		}
	}

	private void sendMessage(String m, PrintWriter os) {
		os.println(m);
		os.flush();
	}

	public static void main(String[] args) {
		new BeatServer();
	}

	private class ClientJob implements Runnable {
		BufferedReader reader;

		public ClientJob(Socket clientSocket) {
			InputStreamReader streamReader;
			try {
				streamReader = new InputStreamReader(
						clientSocket.getInputStream());
				reader = new BufferedReader(streamReader);
			} catch (IOException e) {
				// TODO: handle exception
			}
		}

		public void run() {
			trace("ClientJob gestartet.");
			
			try {
				String nachricht = reader.readLine();

				while (nachricht != null) {
					trace("Empfange Message: \n" + "       " + nachricht);
					sendMessageAll(nachricht);
					nachricht = reader.readLine();
				}
			} catch (Exception e) {
				trace("Wahrscheinlich hat sich ein Client abgemeldet.\n  "
						+ e);
			}
		}
	}

	public void trace(String string) {
		if(gui!=null)
			gui.trace(string);
		else
			System.out.println(string);
	}
}
