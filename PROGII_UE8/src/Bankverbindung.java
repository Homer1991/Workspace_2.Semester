
public class Bankverbindung {
	String kontoinhaber;
	String kontonummer;
	String blz;
	
	public Bankverbindung(String kontoinhaber, String kontonummer, String blz) {
		this.kontoinhaber = kontoinhaber;
		this.kontonummer = kontonummer;
		this.blz = blz;
	}
	
	/**
	 * Diese Methode soll die Informationen Ÿber eine Bankverbindung so ausgeben, dass diese gut lesbar formatiert
	 * auf der Konsole ausgegeben wird.
	 */
	public String toString() {
		return (kontoinhaber + ", " + kontonummer + ", " + blz);
	}
}
