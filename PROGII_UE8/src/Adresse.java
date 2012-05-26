
public class Adresse {
	String strasse;
	String nummer;
	String plz;
	String stadt;
	
	public Adresse(String strasse, String nummer, String plz, String stadt) {
		this.strasse = strasse;
		this.nummer = nummer;
		this.plz = plz;
		this.stadt = stadt;
	}
	
	/**
	 * Diese Methode soll die Informationen Ÿber eine Adresse so ausgeben, dass diese gut lesbar formatiert
	 * auf der Konsole ausgegeben wird.
	 */
	public String toString() {
		return (strasse + ", " + nummer + ", " + plz + ", " + stadt);
	}
}
