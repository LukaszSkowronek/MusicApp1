import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MusicMiniApp {
	static JFrame frame = new JFrame("My first music video");
	static MyDrawPanel myDrawPanel;

	public static void main(String[] args) {
		MusicMiniApp musicApp = new MusicMiniApp();
		musicApp.go();

	}

	public void setUpGui() {
		myDrawPanel = new MyDrawPanel();
		frame.setContentPane(myDrawPanel);
		frame.setBounds(30, 30, 300, 300);
		frame.setVisible(true);
	}

	public void go() {
		setUpGui();

		
		try {
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			int[] eventIWant = { 127 };
			sequencer.addControllerEventListener(myDrawPanel, eventIWant);
			Sequence sequence = new Sequence(Sequence.PPQ, 4);
			Track track = sequence.createTrack();
			int r=0;
			for (int i = 5; i < 61; i += 4) {
				track.add(makeEvent(144, 1, r, 100, i));

				track.add(makeEvent(176, 1, 127, 0, i));

				track.add(makeEvent(128, 1, r, 100, i + 2));
			}

			sequencer.setSequence(sequence);
			sequencer.setTempoInBPM(120);
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

	class MyDrawPanel extends JPanel implements ControllerEventListener {
		boolean msg = false;

		@Override
		public void controlChange(ShortMessage event) {
			msg = true;
			repaint();
		}

		public void paintComponent(Graphics g) {
			if (msg) {
				Graphics2D g2 = (Graphics2D) g;

				int red = (int) (Math.random() * 250);
				int blue = (int) (Math.random() * 250);
				int green = (int) (Math.random() * 250);

				g2.setColor(new Color(red, blue, green));

				int height = (int) ((Math.random() * 120) + 10);
				int width = (int) ((Math.random() * 120) + 10);
				int x = (int) ((Math.random() * 40) + 10);
				int y = (int) ((Math.random() * 40) + 10);
				g2.fillRect(x, y, width, height);
				msg = false;
			}
		}
	}

}
