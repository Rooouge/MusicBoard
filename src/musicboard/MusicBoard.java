package musicboard;

import jutils.threads.Threads;
import musicboard.gui.Frame;

public class MusicBoard {

	public static void main(String[] args) {
		Threads.run(() -> {
			try {
				Frame frame = new Frame();
				Global.add("gui", frame);
				frame.setVisible(true);
			} catch(Exception e) {
				e.printStackTrace();
			}
		});
	}
}
