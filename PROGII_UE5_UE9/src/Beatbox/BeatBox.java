package Beatbox;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
//import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class BeatBox {
	Sequencer player;
	Sequence sequence;
	Track track;
	private float BPM;

	// Namen der Intsrumente, die im Array instrumente bei dem zugeh�rigen
	// Index
	// zu finden sind.
	String[] instrumentNamen = { "Bassdrum", "Hi-Hat, geschlossen",
			"Hi-Hat, offen", "Snaredrum", "Crashbecken", "Haendeklatschen",
			"Hohes Tom-Tom", "Hohe Bongo", "Maraca", "China", "Tiefe Conga",
			"Kuhglocke", "Vibraslap", "Tieferes Tomtom", "Hohes Agogo",
			"Hohe Konga, offen" };

	// Integer-Wert der Instrumente, die der zugeh�rigen Zeile im Beat-Pattern
	// zugeordnet sind.
	// Zeile am Index 1 im Beat-Pattern gehoert z.B. zu Instrument 42
	int[] instrumente = { 35, 42, 46, 38, 49, 39, 50, 60, 70, 52, 64, 56, 58,
			47, 67, 63 };

	public BeatBox() {
		try {
			BPM = 100;
			player = MidiSystem.getSequencer();
			player.open();
			sequence = new Sequence(Sequence.PPQ, 4);
			track = sequence.createTrack();
			player.setTempoInBPM(BPM);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Wird von au�en aufgerufen, um die BeatBox zu stoppen.
	 */
	public void stop() {
		player.stop();
	}

	/**
	 * Wird von au�en aufgerufen, um die BeatBox zu starten. In der aktuellen
	 * Version wird ein vordefinierter Ton gespielt.
	 */
	public void spielen(int[][] allPattern) {
		// Falls zwei mal hinter einander spielen aufgerufen wird, muss der alte
		// Track geloescht
		// und ein neuer erstellt werden.
		sequence.deleteTrack(track);
		track = sequence.createTrack();
		// Hier wird der Track mit Toenen gefuellt
		for (int instr = 0; instr < 16; instr++) {
			trackFuellen(instr, allPattern[instr]);
		}
		try {
			player.setSequence(sequence);
			// Wie oft soll der Ton nach dem ersten Abspielen wiederholt werden?
			player.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			player.start();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Wird aufgerufen, um den zugeh�rigen Track mit MidiEvents zu fuellen.
	 */
	public void trackFuellen(int instrument, int[] beat) {
		for (int time = 0; time < beat.length; time++) {
			if (beat[time] == 1) {
				int timee = time + 1;
				if (timee == 1) {
					track.add(midiEventErzeugen(ShortMessage.NOTE_ON, 9,
							instrumente[instrument], 120, timee));
				} else {
					if (timee == 9) {
						track.add(midiEventErzeugen(ShortMessage.NOTE_ON, 9,
								instrumente[instrument], 110, timee));
					} else {
						track.add(midiEventErzeugen(ShortMessage.NOTE_ON, 9,
								instrumente[instrument], 90, timee));
					}
				}
			}
		}
		// nur als bugfix
		track.add(midiEventErzeugen(ShortMessage.NOTE_ON, 9, 35, 0, beat.length));
	}

	/**
	 * Wird aufgerufen um ein MidiEvent zu erzeugen.
	 * 
	 * @param anweisung
	 *            Midi-Ereignis (Anweisung)
	 * @param kanal
	 *            auf welchen Kanal bezieht sich die Anweisung
	 * @param ton
	 *            Ton der gespielt werden soll
	 * @param staerke
	 *            Anschlagstaerke fuer den Ton
	 * @param schlag
	 *            bei welchem Schlag (Zeit) soll das Event ausgefuehrt werden
	 * @return das erzeugte MidiEvent oder null, wenn keines erzeugt werden
	 *         konnte
	 */
	private MidiEvent midiEventErzeugen(int anweisung, int kanal, int ton,
			int staerke, int schlag) {
		ShortMessage message = new ShortMessage();
		MidiEvent event = null;
		try {
			message.setMessage(anweisung, kanal, ton, staerke);
			event = new MidiEvent(message, schlag);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		return event;
	}

	public float tempoErhoehen() {
		if (BPM < 300) {
			BPM += 5;
			player.setTempoFactor(BPM / 100);
		}
		return BPM;
	}

	public float tempoVerringern() {
		if (BPM > 0) {
			BPM -= 5;
			player.setTempoFactor(BPM / 100);
		}
		return BPM;
	}

	public float getBPM() {
		return BPM;
	}
}
