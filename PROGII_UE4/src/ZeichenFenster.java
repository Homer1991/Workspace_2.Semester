import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * ZeichenFenster ist eine Klasse, die ein Fenster oeffnet, in dem ein
 * Zeichenpanel fuer das Zeichnen von Kreisen angezeigt wird. Die Klasse hat
 * eine main-Methode in der eine Instanz der Klasse ZeichenFenster erzeugt und
 * mit Hilfe der Methode zeichnungTesten verschiedene Kreise gezeichnet werden.
 * 
 * @author: Simone Strippgen
 * 
 */
public class ZeichenFenster {

	private static final int FENSTER_BREITE = 800;
	private static final int FENSTER_HOEHE = 700;

	private JFrame frame;
	private KreisZeichnenPanel zeichnung;
	private JButton zufallButton;
	private JList farbAuswahl;
	private JSlider sizeSlider;

	class MausklickListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			if (event.getButton() == MouseEvent.BUTTON1)
				zeichnung.kreisZeichnen(event.getX(), event.getY());
			if (event.getButton() == MouseEvent.BUTTON3)
				zeichnung.loeschen();
		}
	}

	class MausziehenListener extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent event) {
			zeichnung.kreisZeichnen(event.getX(), event.getY());
			aktuallisieren();
		}
	}
	
	class ZufallButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (zeichnung.zufaelligeFarbeEinstellen())
				zufallButton.setText("Zufallsfarbe: An");
			else {
				zufallButton.setText("Zufallsfarbe: Aus");
				zeichnung.setzeFarbe(farbAuswahl.getSelectedValue().toString()
						.toLowerCase());
			}
		}
	}

	class FarbwahlListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent event) {
			zeichnung.setzeFarbe(farbAuswahl.getSelectedValue().toString()
					.toLowerCase());
			aktuallisieren();
		}
	}

	class RadiusListener implements ChangeListener {
		public void stateChanged(ChangeEvent event) {
			zeichnung.setzeRadius(sizeSlider.getValue());
			aktuallisieren();
		}

	}

	public ZeichenFenster() {
		frame = new JFrame("Zeichenfenster");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(FENSTER_BREITE, FENSTER_HOEHE);
		initialisieren();
		frame.setVisible(true);
	}

	/**
	 * Diese Methode initialisiert das Fenster, d.h. das Fenster wird mit den
	 * gewuenschten Komponenten bestueckt. In diesem Fall wird eine neue Instanz
	 * von KreisZeichenPanel erstellt und dem Fenster hinzugefuegt.
	 */
	public void initialisieren() {
		zeichnung = new KreisZeichnenPanel();
		zeichnung.addMouseListener(new MausklickListener());
		zeichnung.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		zeichnung.addMouseMotionListener(new MausziehenListener());
		zufallButton = new JButton("Zufallsfarben: Aus");
		zufallButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		zufallButton.addActionListener(new ZufallButtonListener());
		String[] data = { "Blau", "Cyan", "Gruen", "Magenta", "Orange", "Pink",
				"Rot", "Weiss", "Gelb" };
		farbAuswahl = new JList(data);
		farbAuswahl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		farbAuswahl.setSelectedIndex(1);
		farbAuswahl.setToolTipText("Setzt die Pinselfarbe");
		farbAuswahl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		farbAuswahl.addListSelectionListener(new FarbwahlListener());
		sizeSlider = new JSlider(JSlider.VERTICAL, 0, 60, 10);
		sizeSlider.setMajorTickSpacing(10);
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setPaintLabels(true);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setToolTipText("Setzt die Groesse des Pinsels. Aktuell: "
				+ sizeSlider.getValue());
		sizeSlider.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sizeSlider.addChangeListener(new RadiusListener());
		frame.setTitle("AssiPaint");
		frame.add(BorderLayout.EAST, sizeSlider);
		frame.add(BorderLayout.SOUTH, farbAuswahl);
		frame.add(BorderLayout.NORTH, zufallButton);
		frame.add(BorderLayout.CENTER, zeichnung);
	}

	public void aktuallisieren() {
		if (zeichnung.getZufall())
			zufallButton.setText("Zufallsfarbe: An");
		else
			zufallButton.setText("Zufallsfarbe: Aus");
		sizeSlider.setToolTipText("Setzt die Groesse des Pinsels. Aktuell: "
				+ sizeSlider.getValue());
	}

	public static void main(String[] args) {
		new ZeichenFenster();
	}
}
