import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PersonLadenTest {
	public static void main(String[] args) {
		Person person1;
		Person person2;
		Person person3;

		// Hier kommt der Code für das Laden der Personen aus einer Textdatei
		// hinein
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					"save.txt"));
			String eins = reader.readLine();
			String zwei = reader.readLine();
			String drei = reader.readLine();

			String[] einsPerson = eins.split("; ");
			person1 = new Person(einsPerson[0], einsPerson[1],
					Integer.parseInt(einsPerson[2]));
			String[] einsAdresse = einsPerson[3].split(", ");
			person1.setAdresse(new Adresse(einsAdresse[0], einsAdresse[1],
					einsAdresse[2], einsAdresse[3]));
			String[] einsBank = einsPerson[4].split(", ");
			person1.setBankverbindung(einsBank[1], einsBank[2]);
			String[] einsFreunde = einsPerson[5].split(", ");
			for (String f : einsFreunde)
				person1.addFreund(f);
			
			String[] zweiPerson = zwei.split("; ");
			person2 = new Person(zweiPerson[0], zweiPerson[1],
					Integer.parseInt(zweiPerson[2]));
			String[] zweiAdresse = zweiPerson[3].split(", ");
			person2.setAdresse(new Adresse(zweiAdresse[0], zweiAdresse[1],
					zweiAdresse[2], zweiAdresse[3]));
			String[] zweiBank = zweiPerson[4].split(", ");
			person2.setBankverbindung(zweiBank[1], zweiBank[2]);
			String[] zweiFreunde = zweiPerson[5].split(", ");
			for (String f : zweiFreunde)
				person2.addFreund(f);

			String[] dreiPerson = drei.split("; ");
			person3 = new Person(dreiPerson[0], dreiPerson[1],
					Integer.parseInt(dreiPerson[2]));
			String[] dreiAdresse = dreiPerson[3].split(", ");
			person3.setAdresse(new Adresse(dreiAdresse[0], dreiAdresse[1],
					dreiAdresse[2], dreiAdresse[3]));
			String[] dreiBank = dreiPerson[4].split(", ");
			person3.setBankverbindung(dreiBank[1], dreiBank[2]);
			String[] dreiFreunde = dreiPerson[5].split(", ");
			for (String f : dreiFreunde)
				person3.addFreund(f);
			
			System.out.println("Folgende Objekte wurden geladen:");
			System.out.println("Objekt von person1: ");
			System.out.println(person1);
			System.out.println("Objekt von person2: ");
			System.out.println(person2);
			System.out.println("Objekt von person3: ");
			System.out.println(person3);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
