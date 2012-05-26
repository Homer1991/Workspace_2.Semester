import java.awt.*;
import java.util.Random;

import javax.swing.*;

/**
 * KreisZeichenPanel ist eine Klasse, die ein Zeichenpanel erstellt, auf dem 
 * Kreise in bestimmter Groe�e, Farbe und Position gezeichnet werden k�nnen.
 * Den gewuenschten Radius, die gewuenschte Groesse und Farbe kann man vor dem Zeichnen 
 * des Kreises einstellen. 
 * Das Panel laesst sich auch komplett loeschen.
 * @author: Simone Strippgen
 * 
 */

public class KreisZeichnenPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9080811828764946642L;
	private int xPos,yPos;
	private int radius;
	private Color farbe;
	private boolean kreisZeichnen;
	private boolean allesLoeschen;
	private boolean zufallsFarbe;
	
	public KreisZeichnenPanel() {
		kreisZeichnen = false;
		// Bei der Initialisierung muss alles geloescht werden
		allesLoeschen = true;
		zufallsFarbe = false;
		farbe = Color.CYAN;
		radius = 10;
	}

	/**
	 * Ueberschriebene, von JPanel geerbte Methode. Sie wird immer aufgerufen, wenn das Fenster neu gezeichnet werden soll.
	 * Hier wird - je nach Wert der Attribute allesLoeschen, kreisZeichnen und zufallsFarbe - das Panel komplett mit
	 * Schwarz gefuellt (loeschen) oder es wird ein Kreis in der eingestellten Gr��e (radius) an der eingestellten
	 * Position (xPos,yPos) mit der eingestellten Farbe (farbe) gezeichnet.
	 * Wenn das Attribut zufallsFarbe den Wert true hat, so wird vor dem Zeichnen des Kreises eine zufaellige
	 * Farbe eingestellt.
	 * 
	 * * @param g  enthaelt die Zeichenflaeche, diese wird bei Aufruf von der Laufzeitumgebung uebergeben.
	 */
	public void paintComponent(Graphics g) {
		if(allesLoeschen) {
			g.setColor(new Color(130, 130, 130));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		if(kreisZeichnen) {
			if(zufallsFarbe) {
				Random zufall = new Random();
				int rot = zufall.nextInt(256);
				int gruen = zufall.nextInt(256);
				int blau = zufall.nextInt(256);
		
				farbe = new Color(rot, gruen, blau);
			}
			g.setColor(farbe);
			g.fillOval(xPos-radius, yPos-radius, radius*2, radius*2);
			kreisZeichnen = false;
		}
	}
	
	/**
	 * Hier wird die Mittelpunktposition eines zu zeichnenden Kreises uebergeben.
	 * Die x- und y-Position werden als Attributwerte gesetzt, das Attribut f�r das Zeichnen des
	 * Kreises wird auf true gesetzt und das Panel neu gezeichnet (repaint).
	 * 
	 * @param x  enthaelt die x-Position an der der Kreis gezeichnet werden soll (Mitte).
	 * @param y  enthaelt die y-Position an der der Kreis gezeichnet werden soll (Mitte).
	 */
	public void kreisZeichnen(int x, int y) {
		//warte(10);
		this.xPos = x;
		this.yPos = y;
		kreisZeichnen = true;
		allesLoeschen = false;
		this.repaint();
	}
	
	/**
	 * Hier wird das Attribut fuer die zufaellig ausgewaehlte Farbe gesetzt.
	 */
	public boolean zufaelligeFarbeEinstellen(){
		//Hier muss kurz gewartet werden, damit die vorhergehende Zeichenoperation komplett abgearbeitet
		//ist.
		warte(10);
		if(zufallsFarbe)
			zufallsFarbe = false;
		else
			zufallsFarbe = true;
		return zufallsFarbe;
	}
	
	public boolean getZufall(){
		//Hier muss kurz gewartet werden, damit die vorhergehende Zeichenoperation komplett abgearbeitet
		//ist.
		return zufallsFarbe;
	}
	
	
	/**
	 * Hier wird der Wert fuer Radius des naechsten Kreises gesetzt.
	 * 
	 * @param radius  enth�lt den Radius.
	 */
	public void setzeRadius(int radius) {
		//Hier muss kurz gewartet werden, damit die vorhergehende Zeichenoperation komplett abgearbeitet
		//ist.
		warte(10);
		this.radius = radius;
	}
	
	/**
	 * Hier wird der Wert fuer die Farbe des naechsten Kreises gesetzt.
	 * 
	 * @param farbe  enth�lt den String mit der Farbangabe.
	 */
	public void setzeFarbe(String farbe) {
		//Hier muss kurz gewartet werden, damit die vorhergehende Zeichenoperation komplett abgearbeitet
		//ist.
		warte(10);
		zufallsFarbe = false;
		if(farbe.equals("blau")) this.farbe = Color.BLUE;
		if(farbe.equals("cyan")) this.farbe = Color.CYAN;
		if(farbe.equals("gruen")) this.farbe = Color.GREEN;
		if(farbe.equals("magenta")) this.farbe = Color.MAGENTA;
		if(farbe.equals("orange")) this.farbe = Color.ORANGE;
		if(farbe.equals("pink")) this.farbe = Color.PINK;
		if(farbe.equals("rot")) this.farbe = Color.RED;
		if(farbe.equals("weiss")) this.farbe = Color.WHITE;
		if(farbe.equals("gelb")) this.farbe = Color.YELLOW;
	}
	
	/**
	 * Hier wird das gesamte Zeichenpanel geloescht.
	 */
	public void loeschen() {
		//Hier muss kurz gewartet werden, damit die vorhergehende Zeichenoperation komplett abgearbeitet
		//ist.
		warte(10);
		allesLoeschen = true;
		this.repaint();
	}
	
	/**
	 * Hier wird das Panel gewzungen eine bestimmte Wartezeit verstreichen zu lassen.
	 * 
	 * @param millisekunden  enth�lt die Wartezeit in Millisekunden.
	 */
	private void warte(int millisekunden) {
		try {
			Thread.sleep(millisekunden);
		} catch (Exception e) {
		}
	}
	
}
