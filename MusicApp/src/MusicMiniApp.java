import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MusicMiniApp {

	public static void main(String[] args) {
		MusicMiniApp musicApp = new MusicMiniApp();
		musicApp.play(80, 20);
	}

	public void play(int instrument, int note) {
		Sequencer sequencer = null;
		try {
			sequencer = MidiSystem.getSequencer();

			sequencer.open();
			Sequence sequence = new Sequence(Sequence.PPQ, 4);
			Track track = sequence.createTrack();

			for (int i = 5; i < 61; i += 4) {
				track.add(makeEvent(144, 1, i, 100, i));
				track.add(makeEvent(128, 1, i, 100, i + 2));
			}

			sequencer.setSequence(sequence);
			sequencer.setTempoInBPM(300);
			sequencer.start();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}

	}

	public static MidiEvent makeEvent(int command, int channel, int note, int howHard, int whichBeat) {

		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(command, channel, note, howHard);
			event = new MidiEvent(a, whichBeat);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return event;
	}

}

