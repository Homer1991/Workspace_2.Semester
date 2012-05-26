import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;

public class WebServer {
  public static final int START = 0;
  public static final int ANSWER = 1;
  public static final int CLOSE = 2;
  public static final int ERROR = 3;
  private final ServerGui gui;
  private Socket cs;
  private ServerSocket ss;
  private BufferedReader br;
  private PrintWriter pw;
  private boolean running;
  private final Book book;
  private String searchString;
  private ArrayList<String> answers;
  private String url;
  private int port;
  private boolean keineEingabe;

  /**
   * Konstruktor für den Server.
   */
  public WebServer() {
    // //////////////////////////////////////////////////////////////
    // ////////// Initialisiere Server
    // //////////////////////////////////////////////////////////////
    gui = new ServerGui();
    running = true;
    keineEingabe = false;
    book = new Book();
    searchString = "";
    try {
      port = 6827;
      trace("Erstelle ServerSocket an Port: " + port);
      ss = new ServerSocket(port);
      url = InetAddress.getLocalHost().getHostAddress().toString() + ":" + port;
    } catch (Exception e) {
      fail("Fehler beim erstellen des Server\n" + e);
    }

    while (running) {
      // //////////////////////////////////////////////////////////////
      // ////////// Initialisiere Client
      // //////////////////////////////////////////////////////////////
      try {
        trace("Warte auf Client");
        cs = ss.accept();
        br = new BufferedReader(new InputStreamReader(cs.getInputStream()));
        pw = new PrintWriter(cs.getOutputStream());
        answer(readRequest());
        closeClient();
      } catch (IOException e) {
        fail("Fehler beim laufenden Server\n" + e);
      }
    }
  }

  /**
   * Liest GET des Browsers aus.
   * 
   * @return gewollte Aktion
   */
  @SuppressWarnings("deprecation")
  private int readRequest() {
    try {
      trace("Lese Request:");
      String line = br.readLine();
      trace("	" + line);
      line = line.substring((line.indexOf("GET") + 5), line.indexOf("HTTP", line.length() - 8)).trim();
      line = URLDecoder.decode(line);
      if (line.startsWith("close")) {
        return CLOSE;
      }
      if (line.contains("suche=")) {
        searchString = line.substring(7);
        trace("Suche nach: " + searchString);
        answers = book.search(searchString);
        if (answers == null) {
          keineEingabe = true;
          return START;
        }
        return ANSWER;
      }
    } catch (IOException e) {
      fail("Fehler beim lesen des Requests\n" + e);
    }
    return START;
  }

  /**
   * Erstellt HTML code für bestimmte Aktionen.
   * 
   * @param status
   *          siehe Klassenkonstanten
   */
  private void answer(int status) {
    trace("Erstelle Antwort");
    pw.println("HTTP/1.0 200 OK");
    pw.println("Connection:close");
    pw.println("Content-Type:text/html");
    pw.println("\n");

    pw.println("<html>");
    pw.println("<title>Adressbuch</title>");
    pw.println("	<body>");

    switch (status) {
    case START: {
      pw.println("		<h2>Suche nach:</h2>");
      if (keineEingabe) {
        keineEingabe = false;
        pw.println("		Sie müssen etwas eingeben um nach etwas zu suchen.");
      }
      pw.println("		<form>");
      pw.println("			<input type='text' name='suche'>");
      pw.println("			<input type='submit' value='Suche'>");
      pw.println("		</form>");
      pw.println("		<form action='http://" + url + "/close'>");
      pw.println("			<input type='submit' value='Server beenden'>");
      pw.println("		</form>");
      break;
    }
    case ANSWER: {
      pw.println("		<h2>Suchergebnisse:</h2>");
      pw.println("		<ul>");
      if (answers.isEmpty())
        pw.println("		<li>es wurde leider nichts gefunden</li>");
      for (String a : answers)
        pw.println("		<li>" + a + "</li>");
      pw.println("		</ul>");
      pw.println("		<form action='http://" + url + "'>");
      pw.println("			<input type='submit' value='zurück'>");
      pw.println("		</form>");
      pw.println("		<form action='http://" + url + "/close'>");
      pw.println("			<input type='submit' value='Server beenden'>");
      pw.println("		</form>");
      break;
    }
    case CLOSE: {
      pw.println("		<h2>Server wurde beendet</h2>");
      pw.println("	</body>");
      pw.println("</html>");

      pw.flush();
      closeServer();
      break;
    }
    case ERROR: {
      pw.println("		<h2>Es ist ein Fehler aufgetreten</h2>");
      pw.println("		Der Server wird beendet.");
      break;
    }
    }

    pw.println("	</body>");
    pw.println("</html>");

    pw.flush();
  }

  /**
   * Schließt die aktuelle Clientanfrage ab.
   */
  public void closeClient() {
    try {
      if (running) {
        trace("Schließe Client");
        cs.close();
      }
    } catch (IOException e) {
      fail("Fehler beim schließen des Clients\n" + e);
    }
  }

  /**
   * Beendet den Server.
   */
  public void closeServer() {
    trace("Beende Server");
    running = false;
    try {
      trace("Schließe Client-Socket");
      cs.close();
      trace("Schließe Server-Socket");
      ss.close();
      gui.close();
    } catch (IOException e) {
      fail("Fehler beim beenden des Servers\n" + e);
    }
  }

  /**
   * Gibt String auf der Konsole aus.
   * 
   * @param out
   *          Konsolenausgabestring
   */
  private void trace(String out) {
    gui.trace(out);
  }

  /**
   * Gibt Fehlerstring auf der Konsole aus und beendet den Server.
   * 
   * @param out
   *          Fehlerausgabestring
   */
  private void fail(String out) {
    gui.trace("Fehler:  " + out);
    answer(ERROR);
    closeServer();
  }

  /**
   * Startet den Server.
   * 
   * @param args
   *          Argumente des Programmes
   */
  public static void main(String[] args) {
    new WebServer();
  }
}