package musicboard;

import javax.swing.JOptionPane;

import jutils.threads.Threads;
import musicboard.data.xml.XML;
import musicboard.gui.Frame;
import musicboard.gui.LoadingDialog;

public class MusicBoard {

	public static void main(String[] args) {
		Threads.run(() -> {
			try {
				LoadingDialog dialog = new LoadingDialog();
				
				dialog.setVisible(true);
				XML.init();
				dialog.setVisible(false);
				
				Frame frame = new Frame();
				Global.add("gui", frame);
				frame.setVisible(true);
			} catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
