package v2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Ping {
	public static int REACHABLE = 1;
	public static int UNREACHABLE = 0;

	private static int WINDOWS = 0;
	private static int LINUX = 1;

	/**
	 * Prueft welches OS benutzt wird und fuer Pingabfrage aus.
	 * 
	 * @param ip
	 *            Angabe der IP-Adresse.
	 * 
	 * @return Ergebnis des Ping-Requests als int.
	 */
	public static int startPing(String ip) {
		if (System.getProperty("os.name").contains("Windows"))
			return ping(WINDOWS, ip);
		else if (System.getProperty("os.name").contains("Linux"))
			return ping(LINUX, ip);
		else
			return -1;
	}

	/**
	 * Fuehrt die Pingabfrage aus.
	 * 
	 * @param os
	 *            Angabe der Betriebssystems.
	 * @param ip
	 *            Angabe der IP-Adresse.
	 * 
	 * @return Ergebnis des Ping-Requests als int.
	 */
	private static int ping(int os, String ip) {

		String[] cmd = new String[3];
		if (os == WINDOWS) {
			cmd[0] = "cmd";
			cmd[1] = "/c";
			cmd[2] = "ping -n 1 " + ip;
		} else if (os == LINUX) {
			cmd[0] = "/bin/bash";
			cmd[1] = "-c";
			cmd[2] = "ping -c 1 " + ip;
		}

		ProcessBuilder pb = new ProcessBuilder(cmd);

		try {
			Process p = pb.start();
			p.waitFor();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String zeile;
			String match = null;

			if (os == WINDOWS)
				match = "Empfangen = 1";
			else if (os == LINUX)
				match = "1 received";

			boolean found = false;
			while ((zeile = br.readLine()) != null) {
				if (zeile.contains(match))
					found = true;
			}

			int out = -1;

			if (found)
				out = REACHABLE;
			else
				out = UNREACHABLE;

			return out;

		} catch (Exception e) {
			System.out
					.println("Eventuell wird ein nicht unterstuetztes OS verwendet.");
			System.out.println(e);
		}
		return -1;
	}
}