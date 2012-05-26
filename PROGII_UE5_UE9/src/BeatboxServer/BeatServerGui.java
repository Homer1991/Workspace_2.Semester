package BeatboxServer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class BeatServerGui {
	JTextArea text;
	JScrollPane scrollText;

	public BeatServerGui() {		
		JFrame frame = new JFrame("Beatbox Server");
		text = new JTextArea("Serverlog: \n");
		text.setEditable(false);
		scrollText = new JScrollPane(text);
		scrollText.setPreferredSize(new Dimension(300, 500));

		frame.add(scrollText, BorderLayout.CENTER);

		frame.setSize(new Dimension(300, 500));
		frame.setVisible(true);
	}

	public void trace(String newText) {
		text.setText(text.getText() + "\n" + newText);
		Point point = new Point(0, (int)(text.getSize().getHeight()));
		scrollText.getViewport().setViewPosition(point);
	}
}
