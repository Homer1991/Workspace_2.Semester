package v2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

public class GUI {
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 185;
	private static GUI g;

	private JFrame frame;
	private RoomPanel panel;
	private JButton update;
	private JLabel label;

	/**
	 * Main-Methode, diese startet das gesammte Programm.
	 * 
	 * @param args
	 *            Argumente des Programmes.
	 */
	public static void main(String[] args) {
		g = new GUI();
		g.update();
	}

	/**
	 * Der Konstruktor für die GUI. Dieser baut die GUI auf.
	 */
	public GUI() {
		panel = new RoomPanel();
		update = new JButton("Update");
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				g.update();
			}
		});
		label = new JLabel("         Update...");
		frame = new JFrame("Ping with " + System.getProperty("os.name"));
		frame.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.add(label, BorderLayout.NORTH);
		frame.add(panel, BorderLayout.CENTER);
		frame.add(update, BorderLayout.SOUTH);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * Zur Aktualisierung der Anzeige.
	 */
	private void update() {
		g.label.setText("         Update...");
		g.update.setEnabled(false);
		g.panel.update();
		g.label.setText("         Last Update: " + this.getTime());
		g.update.setEnabled(true);
	}

	/**
	 * Holt sich die Zeit vom BS und formatiert Sie.
	 * 
	 * @return Formatierte Zeit als String.
	 */
	private String getTime() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss, dd.MM.yyyy");
		Date date = new Date();
		
		return dateFormat.format(date);
	}
}
