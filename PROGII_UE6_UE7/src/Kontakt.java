import java.io.Serializable;

/**
 * Die Kontaktdaten f�r einen Eintrag in einem
 * Adressbuch: Name, Adresse, Telefon.
 * 
 * @author David J. Barnes und Michael K�lling.
 * @version 2008.03.30
 */
public class Kontakt implements Comparable<Kontakt>, Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2867207535900265642L;
	private String name;
    private String telefon;
    private String adresse;

    /**
     * Lege Kontaktdaten an. Bei allen Angaben werden umgebende
     * Leerzeichen entfernt. Entweder der Name oder die Telefonnummer
     * darf nicht leer sein.
     * @param name der Name.
     * @param telefon die Telefonnummer.
     * @param adresse die Adresse.
     * @throws IllegalStateException - tritt auf, wenn ein Kontakt sowohl einen leeren Namen als auch eine 
     * leere Telefonnummer besitzt.
    */
    public Kontakt(String name, String telefon, String adresse)
    {
        // Leere Strings verwenden, wenn einer der Parameter null ist.
        if(name == null) {
            name = "";
        }
        if(telefon == null) {
            telefon = "";
        }
        if(adresse == null) {
            adresse = "";
        }

        this.name = name.trim();
        this.telefon = telefon.trim();
        this.adresse = adresse.trim();
        if(name.equals("") && telefon.equals("")) 
            throw new IllegalStateException("Name und Telefonnummer d�rfen nicht beide leer sein.");
    }
    
    /**
     * @return den Namen.
     */
    public String gibName()
    {
        return name;
    }

    /**
     * @return die Telefonnummer.
     */
    public String gibTelefon()
    {
        return telefon;
    }

    /**
     * @return die Adresse.
     */
    public String gibAdresse()
    {
        return adresse;
    }
    
    /**
     * Teste dieses und jenes Objekt auf Datengleichheit.
     * @param jenes Das Objekt, das mit diesem verglichen
     *              werden soll.
     * @return true wenn das Parameterobjekt ein Kontakt ist
     *              und sich die Datenfelder paarweise gleichen.
     */
    public boolean equals(Object jenes)
    {
        if(jenes instanceof Kontakt) {
            Kontakt jenerKontakt = (Kontakt) jenes;
            return name.equals(jenerKontakt.gibName()) &&
                    telefon.equals(jenerKontakt.gibTelefon()) &&
                     adresse.equals(jenerKontakt.gibAdresse());
        }
        else {
            return false;
        }
    }

    /**
     * @return einen mehrzeiligen String mit Name, Telefon und Adresse.
     */
    public String toString()
    {
        return name + "\n" + telefon + "\n" + adresse;
    }
    
    /**
     * Vergleiche diesen Kontakt mit einem anderen, damit
     * sortiert werden kann. Kontakte werden nach Name,
     * Telefonnummer und Adresse sortiert.
     * @param jenerKontakt der Kontakt, mit dem verglichen werden soll.
     * @return einen negativen Wert, wenn dieser Kontakt vor dem Parameter
     *         liegt, Null, wenn sie gleich sind, und einen positiven Wert,
     *         wenn dieser Kontakt nach dem Parameter folgt.
     */
    public int compareTo(Kontakt jenerKontakt)
    {
        int vergleich = name.compareTo(jenerKontakt.gibName());
        if(vergleich != 0) {
            return vergleich;
        }
        vergleich = telefon.compareTo(jenerKontakt.gibTelefon());
        if(vergleich != 0) {
            return vergleich;
        }
        return adresse.compareTo(jenerKontakt.gibAdresse());
    }

    /**
     * Berechne einen Hashcode nach den Regeln des Buches
     * "Effektiv Java programmieren" von Joshua Bloch.
     * @return einen Hashcode f�r diesen Kontakt.
     */
    public int hashCode()
    {
        int code = 17;
        code = 37 * code + name.hashCode();
        code = 37 * code + telefon.hashCode();
        code = 37 * code + adresse.hashCode();
        return code;
    }
}
