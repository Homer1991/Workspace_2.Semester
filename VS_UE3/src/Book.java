import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA. User: Homer Date: 08.12.11 Time: 16:19 To change
 * this template use File | Settings | File Templates.
 */
public class Book {
	private ArrayList<String> book;

	public Book() {
		book = new ArrayList<String>();
		book.add("Blah, Andreas: 033083093123");
		book.add("Gates, Bill: 73573757");
		book.add("Gluth, Jan: 856854346");
		book.add("Muth, Holger: 0330830505123");
		book.add("Muth, Monika: 03306542323");
		book.add("Schulz, Rüdiger: 97872146");
		book.add("Tian, Chris: 52345723");
		book.add("Radam, Cassandra: 07815294");
		book.add("Schultz, Martin: 07815294");
	}

	public ArrayList<String> search(String search) {
		if (search == null || search.trim().equals("")) {
			return null;
		} else {
			ArrayList<String> out = new ArrayList<String>();
			for (String entry : book) {
				if (entry.contains(search))
					out.add(entry);
			}
			return out;
		}
	}
}
