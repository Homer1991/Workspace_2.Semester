import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Eine GUI fuer ein Adressbuch. Es werden verschiedene Ansichten auf die Daten
 * des Adressbuchs angeboten:
 * 
 * Eine zum Durchsuchen des Adressbuchs.
 * 
 * Eine zum Eingeben neuer Kontaktdaten. Der Button 'Einfuegen' fuegt die Daten
 * in das Adressbuch ein.
 * 
 * Eine, um alle Eintraege des Adressbuchs anzuzeigen.
 * 
 * @author David J. Barnes und Michael Kölling.
 * @version 2008.03.30
 */
public class AdressbuchGUI {
	JFrame fenster;

	// Bevorzugte Dimensionen fuer diesen Frame
	private static final int PREFERRED_WIDTH = 550;
	private static final int PREFERRED_HEIGHT = 500;
	private static final Dimension PREFERRED_SIZE = new Dimension(
			PREFERRED_WIDTH, PREFERRED_HEIGHT);
	// Das Adressbuch, das angezeigt und manipuliert werden soll
	private Adressbuch buch;

	// SuchePanel
	private JTextField sucheEdit;
	private JTextPane suchePane;
	private JTextPane textPane;

	private JTextField nameField;
	private JTextField telField;
	private JTextArea adresseArea;
	
	/**
	 * Erzeuge den Frame mit seinen Panels.
	 * 
	 * @param buch
	 *            das Adressbuch, das manipuliert werden soll.
	 */
	public AdressbuchGUI(Adressbuch buch) {
		this.buch = buch;
		fenster = new JFrame("Adressbuch");
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialisiere();

		fenster.setSize(PREFERRED_SIZE);
		// fenster.pack();
		fenster.setVisible(true);
	}

	private void initialisiere() {
		JTabbedPane tabbedArea = new JTabbedPane();
		tabbedArea.add("Eintraege durchsuchen", suchenPanelErstellen());
		tabbedArea.add("Neue Kontakte eintragen", eingabePanelErstellen());
		tabbedArea.add("Eintraege auflisten", auflistenPanelErstellen());
		fenster.add(tabbedArea);
		fenster.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				loadBuch();
			}
			
			
			@Override
			public void windowClosing(WindowEvent e) {
				saveBuch();
			}


			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}


			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}


			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}


			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}


			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void saveBuch() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new GZIPOutputStream(new FileOutputStream("blah.blah")));
			out.writeObject(buch);
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private void loadBuch() {
		try {
			ObjectInputStream in = new ObjectInputStream(new GZIPInputStream(
					new FileInputStream("blah.blah")));
			Adressbuch loadedBook = (Adressbuch) in.readObject();
			in.close();
			buch = loadedBook;
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Stelle den Panel fuer die Dateneingabe zusammen.
	 * 
	 * @return den zusammengestellten Panel.
	 */
	private Container eingabePanelErstellen() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		JButton addKontakt = new JButton("hinzufügen");
		addKontakt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					buch.neuerKontakt(new Kontakt(nameField.getText(), telField.getText(), adresseArea.getText()));
					JOptionPane.showMessageDialog(null, "Kontakt wurde eingefügt");
				} catch (DoppelterSchluesselException e1) {
					JOptionPane.showMessageDialog(null, "Schlüssel schon vergeben");
				} catch (IllegalStateException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		JButton deleteKontakt = new JButton("löschen");
		deleteKontakt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String schluessel = nameField.getText();
				if(schluessel.equals(""))
					schluessel = telField.getText();
				
				try {
					if(buch.schluesselBekannt(schluessel)) {
						buch.entferneKontakt(schluessel);
						JOptionPane.showMessageDialog(null, "Kontakt entfernt");
					}
					else {
						JOptionPane.showMessageDialog(null, "Schluessel unbekannt");
					}
				} catch (HeadlessException e1) {
					JOptionPane.showMessageDialog(null, "ungültiger Schluessel");
				} catch (KeinPassenderKontaktException e1) {
					JOptionPane.showMessageDialog(null, "ungültiger Schluessel");
				} catch (UngueltigerSchluesselException e1) {
					JOptionPane.showMessageDialog(null, "ungültiger Schluessel");
				}
			}
		});
		buttonPanel.add(addKontakt);
		buttonPanel.add(deleteKontakt);
		
		JPanel eingabePanel = new JPanel();
		eingabePanel.setLayout(new BoxLayout(eingabePanel, BoxLayout.Y_AXIS));

		JPanel namePanel = new JPanel();
		JPanel telPanel = new JPanel();
		JPanel adressePanel = new JPanel();
		
		nameField = new JTextField(20);
		
		namePanel.add(new JLabel("                   Name: "));
		namePanel.add(nameField);

		
		telField = new JTextField(20);
		
		telPanel.add(new JLabel("Telefonnummer: "));
		telPanel.add(telField);
		
		
		adresseArea = new JTextArea(10,20);
		
		adressePanel.add(new JLabel("             Adresse: "));
		adressePanel.add(adresseArea);
		
		
		eingabePanel.add(namePanel);
		eingabePanel.add(telPanel);
		eingabePanel.add(adressePanel);
		
		mainPanel.add(BorderLayout.CENTER, eingabePanel);
		mainPanel.add(BorderLayout.SOUTH, buttonPanel);
		
		return mainPanel;
	}

	
	
	private String getBuch(String suche) {
		Kontakt[] kontakte = buch.suche(suche);
		String text = "";
		for (Kontakt k : kontakte) {
			text += k.toString() + "\n\n";
		}
		return text;
	}

	/**
	 * Stelle den Panel fuer das Suchen von Eintraegen zusammen.
	 * 
	 * @return den zusammengestellten Panel.
	 */
	private Container suchenPanelErstellen() {
		JPanel suchenPanel = new JPanel(new BorderLayout());

		sucheEdit = new JTextField("");
		sucheEdit.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				sucheUpdate();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				sucheUpdate();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				sucheUpdate();
			}
		});
		
		JPanel sucheEditPanel = new JPanel(new BorderLayout());
		sucheEditPanel.add(BorderLayout.WEST, new JLabel("Suche: "));
		sucheEditPanel.add(BorderLayout.CENTER, sucheEdit);
		suchePane = new JTextPane();
		
		suchenPanel.add(BorderLayout.NORTH, sucheEditPanel);
		suchenPanel.add(BorderLayout.CENTER, new JScrollPane(suchePane));
		sucheUpdate();

		return suchenPanel;
	}

	private void sucheUpdate() {
		suchePane.setText(getBuch(sucheEdit.getText()));
	}

	/**
	 * Stelle den Panel zum Auflisten der Eintraege zusammen.
	 * 
	 * @return den zusammengestellten Panel.
	 */
	private Container auflistenPanelErstellen() {
		JPanel auflistenPanel = new JPanel(new BorderLayout());

		JPanel buttonPanel = new JPanel();
		JButton list = new JButton("Auflisten");
		JButton clear = new JButton("Loeschen");
		list.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textPane.setText(getBuch(""));
			}
		});
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textPane.setText("");
			}
		});
		
		buttonPanel.add(list);
		buttonPanel.add(clear);
		
		textPane = new JTextPane();
		textPane.setText(getBuch(""));
		textPane.setEditable(false);
		auflistenPanel.add(BorderLayout.CENTER, new JScrollPane(textPane));
		auflistenPanel.add(BorderLayout.SOUTH, buttonPanel);

		return auflistenPanel;
	}

}
