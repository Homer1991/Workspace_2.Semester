import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PersonSpeichernTest {
	public static void main(String[] args) {
		// Hier muss der Code hinein, der drei Instanzen der Klasse Person
		// erzeugt und diesen
		// alle nštigen Informationen (Adresse, Bankverbindung, Freunde)
		// hinzufŸgt

		String[] freunde1 = { "Karl", "Egon", "Erna" };
		Person eins = new Person("Mundt", "Jan", 18);
		eins.setAdresse(new Adresse("Blah", "39a", "16775", "Gransee"));
		eins.setBankverbindung("39847329847", "16030000");
		for (String f : freunde1)
			eins.addFreund(f);

		String[] freunde2 = { "Willibald", "Herribert" };
		Person zwei = new Person("Waldi", "Troll", 999);
		zwei.setAdresse(new Adresse("Polen Allee", "498", "03654",
				"Hohenwutzen"));
		zwei.setBankverbindung("464534234", "1607800");
		for (String f : freunde2)
			zwei.addFreund(f);

		String[] freunde3 = { "Helga", "Ursula" };
		Person drei = new Person("Wolfgang", "Pulla", 67);
		drei.setAdresse(new Adresse("Hermannstrasse", "16c", "42355",
				"Düsseldorf"));
		drei.setBankverbindung("87687587", "1608700");
		for (String f : freunde3)
			drei.addFreund(f);

		// Hier kommt der Code fŸr das Speichern der Personen in einer Textdatei
		// (CSV) hinein
		// Alle Infos zu einer Person soll in einer Zeile der Textdatei
		// gespeichert werden

		System.out.println("Folgende Objekte werden gespeichert:");
		System.out.println(eins);
		System.out.println(zwei);
		System.out.println(drei);

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("save.txt"));
			writer.write(eins.toString() + "\n");
			writer.write(zwei.toString() + "\n");
			writer.write(drei.toString() + "\n");
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}