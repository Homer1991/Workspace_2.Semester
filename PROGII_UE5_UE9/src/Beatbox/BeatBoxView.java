package Beatbox;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BeatBoxView {
	// Enthaelt die Checkboxen der GUI. Diese muessen abgefragt werden, um das
	// Pattern auszulesen.
	public static final int MESSAGE = 0;
	public static final int CHATMESSAGE = 1;
	JCheckBox[][] checkboxen;
	// Das aeussere Fenster der GUI
	JFrame fenster;
	// Enthaelt das BeatBox-Objekt der View. Dieses wird im Konstruktor
	// Ÿbergeben.
	BeatBox beatBox;
	JFileChooser fc;
	JList<String> networkList;
	ArrayList<Message> listData;
	DefaultListModel<String> networkListData;
	Socket socket;
	JTextField chattext;
	JTextArea chatBox;
	JScrollPane scrollchatbox;
	PrintWriter writer;
	String user;

	public BeatBoxView(BeatBox box, String name, String ip) {
		user = name;

		listData = new ArrayList<Message>();

		fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("jBeat", "jBeat"));
		beatBox = box;
		fenster = new JFrame("UltraBeatBox 2.81    Name: " + name);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialisiere();
		fenster.setSize(800, 463);
		fenster.setResizable(false);
		fenster.setVisible(true);
		startClient(ip);
	}

	private void startClient(String ip) {
		try {
			socket = new Socket(ip, 3857);

			writer = new PrintWriter(socket.getOutputStream());

			InputStreamReader streamReader = new InputStreamReader(
					socket.getInputStream());
			BufferedReader reader = new BufferedReader(streamReader);

			try {
				String nachricht = reader.readLine();
				while (nachricht != null) {
					String[] split = nachricht.split("§§§");
					if (split[0].startsWith("Message:"))
						updateList(Message.toMessage(split[1]));
					if (split[0].startsWith("Chatmessage:"))
						updateChat(split[1]);

					nachricht = reader.readLine();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(fenster,
						"Server wurde beendet. Client nun im Offlinemodus.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initialisiere() {
		BorderLayout layoutBorder = new BorderLayout();
		final JPanel backgroundPanel = new JPanel(layoutBorder);
		backgroundPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
				10));

		// ////////////////////////////////////////////////////////////////////////////////////////////
		// // Label Panel
		// ////////////////////////////////////////////////////////////////////////////////////////////

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		labelPanel.add(Box.createRigidArea(new Dimension(0, 33)));

		for (int x = 0; x <= 15; x++) {
			labelPanel.add(new JLabel(beatBox.instrumentNamen[x]));
			labelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		}

		// ////////////////////////////////////////////////////////////////////////////////////////////
		// // Checkbox Panel
		// ////////////////////////////////////////////////////////////////////////////////////////////

		JPanel checkPanel = new JPanel(new GridLayout(17, 16));
		checkPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		for (int i = 0; i <= 15; i++) {
			if (i % 4 == 0)
				checkPanel.add(new JLabel("  " + (i / 4 + 1)));
			else
				checkPanel.add(Box.createRigidArea(new Dimension(0, 5)));

		}

		checkboxen = new JCheckBox[16][16];
		for (int x = 0; x <= 15; x++) {
			for (int y = 0; y <= 15; y++) {
				checkboxen[x][y] = new JCheckBox();
				checkPanel.add(checkboxen[x][y]);
			}
		}

		// ////////////////////////////////////////////////////////////////////////////////////////////
		// // Right Panel
		// ////////////////////////////////////////////////////////////////////////////////////////////

		// // Button
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton btPlay = new JButton("Play");
		btPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				beatBox.spielen(getArray());
			}
		});

		JButton btStop = new JButton("Stop");
		btStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				beatBox.stop();
			}
		});

		final JLabel tempLab = new JLabel("     Tempo: " + beatBox.getBPM()
				+ "BPM     ");

		JButton btUp = new JButton("Tempo erhöhen");
		btUp.setPreferredSize(new Dimension(140, 30));
		btUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tempLab.setText("     Tempo: " + beatBox.tempoErhoehen()
						+ "BPM     ");
			}
		});

		JButton btDown = new JButton("Tempo verringern");
		btDown.setPreferredSize(new Dimension(140, 30));
		btDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tempLab.setText("     Tempo: " + beatBox.tempoVerringern()
						+ "BPM     ");
			}
		});

		// // Network
		chatBox = new JTextArea();
		chatBox.setText("Chatbox");
		chatBox.setEditable(false);
		chatBox.setLineWrap(true);
		chatBox.setWrapStyleWord(true);

		scrollchatbox = new JScrollPane(chatBox);
		scrollchatbox.setPreferredSize(new Dimension(200, 80));

		chattext = new JTextField();
		chattext.setPreferredSize(new Dimension(200, 20));
		chattext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendChatMessage();
			}
		});

		networkListData = new DefaultListModel<String>();
		networkList = new JList<String>(networkListData);

		networkList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		networkList.setSize(100, 300);

		JScrollPane scrolllist = new JScrollPane(networkList);
		scrolllist.setPreferredSize(new Dimension(200, 100));

		rightPanel.add(btPlay);
		rightPanel.add(Box.createRigidArea(new Dimension(11, 0)));
		rightPanel.add(btStop);
		rightPanel.add(btUp);
		rightPanel.add(btDown);
		rightPanel.add(tempLab);

		rightPanel.add(Box.createRigidArea(new Dimension(0, 50)));

		rightPanel.add(scrollchatbox);
		rightPanel.add(chattext);
		rightPanel.add(scrolllist);

		rightPanel.setPreferredSize(new Dimension(250, 50));

		// ////////////////////////////////////////////////////////////////////////////////////////////
		// // Background Panel
		// ////////////////////////////////////////////////////////////////////////////////////////////

		backgroundPanel.add(BorderLayout.CENTER, checkPanel);
		backgroundPanel.add(BorderLayout.WEST, labelPanel);
		backgroundPanel.add(BorderLayout.EAST, rightPanel);
		backgroundPanel.add(BorderLayout.SOUTH,
				new JLabel("Copyright by Homer"));

		// ////////////////////////////////////////////////////////////////////////////////////////////
		// MenuBar
		// ////////////////////////////////////////////////////////////////////////////////////////////

		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Datei");
		MenuItem open = new MenuItem("Öffnen");
		MenuItem save = new MenuItem("Speichern");
		MenuItem upload = new MenuItem("Upload");

		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(backgroundPanel);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String file = fc.getSelectedFile().toString();
					setArray(load(file));
				}
			}
		});

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showSaveDialog(backgroundPanel);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String file = fc.getSelectedFile().toString();
					if (!file.endsWith(".jBeat"))
						file += ".jBeat";
					save(file, getArray());
				}
			}
		});

		upload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane
						.showInputDialog("Bitte geben Sie eine Nachricht ein:");
				sendMessage(MESSAGE,
						new Message(user + ": " + name, getArray()).toString());
			}
		});

		networkList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = networkList.getSelectedIndex();
					setArray(listData.get(index).getPattern());
				}
			}
		});

		menu.add(open);
		menu.add(save);
		menu.add(upload);
		menuBar.add(menu);

		fenster.setMenuBar(menuBar);
		fenster.add(backgroundPanel);
	}

	public void sendChatMessage() {
		sendMessage(CHATMESSAGE, user + ": " + chattext.getText());
		chattext.setText("");
	}

	public void updateChat(String s) {
		String out = chatBox.getText();
		out += "\n------------------------------------------\n" + s;
		chatBox.setText(out);
		Point point = new Point(0, (int) (chatBox.getSize().getHeight()));
		scrollchatbox.getViewport().setViewPosition(point);
	}

	public void updateList(Message m) {
		String temp = networkList.getSelectedValue();
		listData.add(m);
		networkListData.addElement(m.getMessage());
		ArrayList<String> liste = new ArrayList<String>();
		for (Message me : listData) {
			liste.add(me.getMessage());
		}

		networkList.setSelectedValue(temp, true);
	}

	private void sendMessage(int type, String message) {
		if (type == MESSAGE) {
			writer.println("Message:§§§" + message);
			writer.flush();
		}
		if (type == CHATMESSAGE) {
			writer.println("Chatmessage:§§§" + message);
			writer.flush();
		}
	}

	private int[][] getArray() {
		int[][] pattern = new int[16][16];
		for (int x = 0; x <= 15; x++) {
			for (int y = 0; y <= 15; y++) {
				if (checkboxen[x][y].isSelected())
					pattern[x][y] = 1;
				else
					pattern[x][y] = 0;
			}
		}
		return pattern;
	}

	private void setArray(int[][] array) {
		for (int x = 0; x <= 15; x++) {
			for (int y = 0; y <= 15; y++) {
				if (array[x][y] == 1)
					checkboxen[x][y].setSelected(true);
				else
					checkboxen[x][y].setSelected(false);
			}
		}
	}

	private void save(String file, int[][] outArray) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new GZIPOutputStream(new FileOutputStream(file)));
			out.writeObject(outArray);
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private int[][] load(String filename) {
		try {
			ObjectInputStream in = new ObjectInputStream(new GZIPInputStream(
					new FileInputStream(filename)));
			int[][] array = (int[][]) in.readObject();
			in.close();
			return array;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
}
