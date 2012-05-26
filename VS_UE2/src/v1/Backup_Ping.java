package v1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Backup_Ping {
	public static int WINDOWS = 0;
	public static int LINUX = 1;

	/**
	 * Pr�ft welches OS benutzt wird und f�r Pingabfrage aus.
	 * 
	 * @param args
	 *            Argumente des Programmes
	 */
	public static void main(String[] args) {
		if (System.getProperty("os.name").contains("Windows"))
			ping(WINDOWS);
		else if (System.getProperty("os.name").contains("Linux"))
			ping(LINUX);
		else
			System.out.println("Ihr System wird leider nicht Unterstuetzt.");

	}

	/**
	 * F�hrt die Pingabfrage aus.
	 * 
	 * @param os
	 *            Angabe der Betriebssystems.
	 */
	private static void ping(int os) {
		int founds = 0; // Z�hler der Funde.
		String list = ""; // F�r eine Listenausgabe am Ende.

		System.out.println("Anpingen der Rechner in D138 unter " + System.getProperty("os.name") + "\n");
		
		System.out
				.println("---------------------------------------------------------");

		for (int i = 60; i <= 80; i++) {
			try {
				String[] cmd = new String[3];
				if (os == WINDOWS) {
					cmd[0] = "cmd";
					cmd[1] = "/c";
					cmd[2] = "ping -n 1 sun" + i;
				} else {
					cmd[0] = "/bin/bash";
					cmd[1] = "-c";
					cmd[2] = "ping -c 1 sun" + i;
				}

				ProcessBuilder pb = new ProcessBuilder(cmd);
				Process p = pb.start();
				p.waitFor();

				BufferedReader br = new BufferedReader(new InputStreamReader(
						p.getInputStream()));
				String zeile;
				String proof;

				if (os == WINDOWS)
					proof = "Empfangen = 1";
				else
					proof = "1 received";

				boolean found = false;
				while ((zeile = br.readLine()) != null) {
					if (zeile.contains(proof))
						found = true;
				}

				if (found) {
					System.out.print("|  Up   ");
					founds++;
					list += "Sun" + i + ": Up\n";
				} else {
					System.out.print("|  Down ");
					list += "Sun" + i + ": Down\n";
				}
				if ((i-3)%7==0) {
					System.out.println("|");
					System.out
							.println("---------------------------------------------------------");
				}
			} catch (Exception e) {
				System.out
						.println("Eventuell wird ein nicht unterstuetztes OS verwendet.");
				System.out.println(e);
			}
		}
		System.out.println();
		System.out.print(list); // Liste und Anzahl der erreichbaren Rechner
								// wird ausgegeben.
		System.out.println("\n" + founds + " von 21 Rechnern erreichbar.");
		System.out.println();
	}
}
