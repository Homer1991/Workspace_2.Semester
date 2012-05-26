import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Eine Klasse zur Verwaltung einer beliebigen Anzahl von 
 * Kontakten. Die Daten werden sowohl ueber den Namen
 * als auch ueber die Telefonnummer indiziert.
 * @author David J. Barnes und Michael Kölling.
 * @version 2008.03.30
 */
public class Adressbuch implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -301157946575391474L;
	// Speicher fuer beliebige Anzahl von Kontakten.
    private TreeMap<String, Kontakt> buch;
    private int anzahlEintraege;

    /**
     * Initialisiere ein neues Adressbuch.
     */
    public Adressbuch()
    {
        buch = new TreeMap<String, Kontakt>();
        anzahlEintraege = 0;
    }
    
    /**
     * Schlage einen Namen oder eine Telefonnummer
     * nach und liefere den zugehoerigen Kontakt.
     * @param schluessel der Name oder die Nummer zum Nachschlagen.
     * @return den zum Schluessel gehoerenden Kontakt.
     * @throws UngueltigerSchluesselException wenn ein leerer String als 
     *          Schluessel eingegeben wurde
     * @throws KeinPassenderKontaktException wenn kein Kontakt fŸr den  
     *          Schluessel gefunden wurde
     */
    public Kontakt gibKontakt(String schluessel) throws UngueltigerSchluesselException, KeinPassenderKontaktException
    {
        if(schluesselBekannt(schluessel))
            return buch.get(schluessel);
            else throw new KeinPassenderKontaktException(schluessel);
    }

    /**
     * Ist der gegebene Schluessel in diesem Adressbuch bekannt?
     * @param schluessel der Name oder die Nummer zum Nachschlagen.
     * @return true wenn der Schluessel eingetragen ist, false sonst.
     * @throws UngueltigerSchluesselException wenn ein leerer String als 
     *          Schluessel eingegeben wurde
     */
    public boolean schluesselBekannt(String schluessel) throws UngueltigerSchluesselException
    {
        if(schluessel == null) 
                throw new IllegalArgumentException("Das Argument schluessel darf nicht den Wert null haben.");
        if(schluessel.trim().length() == 0) throw new UngueltigerSchluesselException(schluessel);
        return buch.containsKey(schluessel);
    }

    /**
     * Fuege einen neuen Kontakt in dieses Adressbuch ein.
     * @param kontakt der neue Kontakt.
     * @throws DoppelterSchluesselException wenn Name oder Telefonnummer des 
     *          neuen Kontakts bereits als Schluessel im Adressbuch vorkommen.
     */
    public void neuerKontakt(Kontakt kontakt) throws DoppelterSchluesselException
    {
        if(kontakt == null)
            throw new IllegalArgumentException("Null-Wert in neuerKontakt().");
        String name = kontakt.gibName();
        String telNr = kontakt.gibTelefon();
        try {
            if(schluesselBekannt(name))
                throw new DoppelterSchluesselException(name);
            if(schluesselBekannt(telNr))
                throw new DoppelterSchluesselException(telNr);
            }
        catch (UngueltigerSchluesselException e) {
            if(e instanceof DoppelterSchluesselException)
                throw (DoppelterSchluesselException) e;
        }
        buch.put(kontakt.gibName(), kontakt);
        buch.put(kontakt.gibTelefon(), kontakt);
        anzahlEintraege++;
    }
    
    /**
     * Aendere die Kontaktdaten, die bisher unter dem gegebenen
     * Schluessel eingetragen waren.
     * @param alterSchluessel einer der verwendeten Schlüssel.
     * @param daten die neuen Kontaktdaten.
     * @throws UngueltigerSchluesselException wenn ein leerer String als 
     *          Schluessel eingegeben wurde
     * @throws KeinPassenderKontaktException wenn kein Kontakt fŸr den  
     *          Schluessel gefunden wurde
     */
    public void aendereKontakt(String alterSchluessel,
                                        Kontakt daten) throws UngueltigerSchluesselException, KeinPassenderKontaktException
    {
        if(daten == null)
            throw new IllegalArgumentException("Null-Wert in aendereKontakt().");
        if(schluesselBekannt(alterSchluessel)) {
            if(!alterSchluessel.equals(daten.gibName()) && !alterSchluessel.equals(daten.gibTelefon()))
                throw new SchluesselUngleichException(alterSchluessel);
            entferneKontakt(alterSchluessel);
            neuerKontakt(daten);  
        }
        else throw new KeinPassenderKontaktException(alterSchluessel);
    }
    
    /**
     * Suche nach allen Kontakten mit einem Schluessel, der mit dem
     * gegebenen Praefix beginnt.
     * @param praefix der Schluesselpraefix, nach dem gesucht werden
     *               soll.
     * @return ein Array mit den gefundenen Kontakten.
     */
    public Kontakt[] suche(String praefix)
    {
        // Eine Liste fuer die Treffer anlegen.
        List<Kontakt> treffer = new LinkedList<Kontakt>();
        // Finden von Schluesseln, die gleich dem oder groesser als
        // der Praefix sind.
        SortedMap<String, Kontakt> rest = buch.tailMap(praefix);
        Iterator<String> it = rest.keySet().iterator();
        // Stoppen bei der ersten Nichtuebereinstimmung.
        boolean sucheBeendet = false;
        while(!sucheBeendet && it.hasNext()) {
            String schluessel = it.next();
            if(schluessel.startsWith(praefix)) {
                treffer.add(buch.get(schluessel));
            }
            else {
                sucheBeendet = true;
            }
        }
        Kontakt[] ergebnisse = new Kontakt[treffer.size()];
        treffer.toArray(ergebnisse);
        return ergebnisse;
    }

    /**
     * @return die momentane Anzahl der Einträge in diesem Adressbuch.
     */
    public int gibAnzahlEintraege()
    {
        return anzahlEintraege;
    }

    /**
     * Entferne den Eintrag mit dem gegebenen Schluessel aus
     * diesem Adressbuch.
     * @param schluessel einer der Schluessel des Eintrags, 
     *                  der entfernt werden soll.
     * @throws UngueltigerSchluesselException wenn ein leerer String als 
     *          Schluessel eingegeben wurde
     * @throws KeinPassenderKontaktException wenn kein Kontakt fŸr den  
     *          Schluessel gefunden wurde
     */
    public void entferneKontakt(String schluessel) throws UngueltigerSchluesselException, KeinPassenderKontaktException
    {
        if(schluesselBekannt(schluessel)) {
            Kontakt kontakt = buch.get(schluessel);
            buch.remove(kontakt.gibName());
            buch.remove(kontakt.gibTelefon());
            anzahlEintraege--;
        }
        else throw new KeinPassenderKontaktException(schluessel);
            
    }

    /**
     * @return alle Kontaktdaten, sortiert nach der
     * Sortierreihenfolge, die die Klasse Kontakt definiert.
     */
    public String alleKontaktdaten()
    {
        // Weil jeder Satz unter zwei Schlüsseln eingetragen ist,
        // muss ein Set mit den Kontakten gebildet werden. Dadurch
        // werden Duplikate entfernt.
        StringBuffer alleEintraege = new StringBuffer();
        Set<Kontakt> sortierteDaten = new TreeSet<Kontakt>(buch.values());
        for(Kontakt kontakt : sortierteDaten) {
            alleEintraege.append(kontakt);
            alleEintraege.append('\n');
            alleEintraege.append('\n');
        }
        return alleEintraege.toString();
    }
}
