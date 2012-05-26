import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerGui {
  JFrame frame;
  JTextArea text;
  JScrollPane scrollText;

  /*
   * Erstellt eine einfache GUI für den Webserver, auf der einige Ausgaben
   * gemacht werden.
   */
  public ServerGui() {
    frame = new JFrame("Serverlog");
    text = new JTextArea("Serverlog: \n");
    text.setEditable(false);
    scrollText = new JScrollPane(text);
    scrollText.setPreferredSize(new Dimension(300, 500));

    frame.add(scrollText, BorderLayout.CENTER);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(new Dimension(300, 500));
    frame.setVisible(true);
  }

  /*
   * Mit der Methode kann eine Ausgabe auf der GUI gemacht werden.
   * 
   * @param newText auszugebender Text
   */
  public void trace(String newText) {
    text.setText(text.getText() + "\n" + newText);
    Point point = new Point(0, (int) (text.getSize().getHeight()));
    scrollText.getViewport().setViewPosition(point);
  }

  /*
   * Damit wird die GUI beendet.
   */
  public void close() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      trace("Fehler: Beim schließen der GUIO ist ein Fehler aufgetreten.\n" + e);
    }
    System.exit(0);
  }
}
