package v2;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class RoomPanel extends JPanel {

	private static final long serialVersionUID = -3832094569384943801L;
	private JToggleButton[] toggles;
	private JLabel label;
	private int count;

	/**
	 * Konstrucktor für das Panel, in dem die ToggleButton liegen.
	 */
	public RoomPanel() {
		toggles = new JToggleButton[21];
		count = 0;

		for (int i = 60; i <= 80; i++) {
			toggles[i - 60] = new JToggleButton("Sun" + i);
			toggles[i - 60].setEnabled(false);
			toggles[i - 60].setBackground(Color.RED);
		}

		for (JToggleButton bt : toggles)
			this.add(bt);

		label = new JLabel(count + "/21 reachable");
		this.add(label);
	}

	/**
	 * Führt die Aktualisierung aus.
	 */
	public void update() {
		count = 0;

		for (int i = 60; i <= 80; i++) {
			toggles[i - 60].setBackground(Color.YELLOW);
			int status = Ping.startPing("sun" + i);
			
			if (status == Ping.UNREACHABLE)
				toggles[i - 60].setBackground(Color.RED);
			else if (status == Ping.REACHABLE) {
				toggles[i - 60].setBackground(Color.GREEN);
				count++;
			} else
				toggles[i - 60].setBackground(Color.BLACK);
			label.setText(count + "/21 reachable");
		}
	}
}
