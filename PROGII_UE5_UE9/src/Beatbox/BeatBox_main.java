package Beatbox;

import javax.swing.JOptionPane;


public class BeatBox_main {
	
	public static void main(String[] args) {
		String name = JOptionPane.showInputDialog("Bitte geben Sie einen Namen ein:");
		String ip = JOptionPane.showInputDialog("Bitte geben Sie die IP des Servers ein:");
		new BeatBoxView(new BeatBox(), name, ip);
	}
}