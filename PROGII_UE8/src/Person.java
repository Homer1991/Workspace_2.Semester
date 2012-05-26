import java.util.ArrayList;


public class Person {
	String name;
	String vorname;
	int alter;
	Adresse adresse;
	Bankverbindung konto;
	ArrayList<String> freunde;
	
	public Person(String name, String vorname, int alter) {
		this.name = name;
		this.vorname = vorname;
		this.alter = alter;
		freunde = new ArrayList<String>();
	}
	
	/**
	 * Eine Bankverbindung mit den Angaben soll erzeugt und als Bankverbindung der Person gesetzt werden. 
	 * @param kontonummer Kontonummer der Bankverbindung
	 * @param blz	Bankleitzahl der Bankverbindung
	 */
	public void setBankverbindung(String kontonummer, String blz) {
		konto = new Bankverbindung(name, kontonummer, blz);
	}
	
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	
	/**
	 *  Der Name des Freundes wird der ArrayList freunde hinzugefuegt
	 * @param freund Name des Freundes
	 */
	public void addFreund(String freund) {
		freunde.add(freund);
	}
	
	/**
	 * Diese Methode soll die Informationen Ÿber eine Person so ausgeben, dass diese gut lesbar formatiert
	 * auf der Konsole ausgegeben wird.
	 */
	public String toString() {
		String freundeString = "";
		for(String f : freunde)
			freundeString += f + ", ";
		if(!freundeString.equals("")) {
			freundeString = freundeString.substring(0, (freundeString.length()-2));
		}
		return (name + "; " + vorname + "; " + alter + "; " + adresse.toString() + "; " + konto.toString() + "; " + freundeString);
	}
}
