package musicboard.gui;

import java.awt.Dimension;

import jutils.config.Config;
import jutils.config.Images;
import jutils.gui.JUFrame;

@SuppressWarnings("serial")
public class Frame extends JUFrame {

	public Frame() throws Exception {
		super(Config.getValue("title"), Images.getImageIcon("icon").getImage());
		this.setMinimumSize(new Dimension(300, 200));
		
		refreshContent();
	}
	
	
	public void refreshContent() throws Exception {
		setContent(new Content());
		setToCenter();
	}
}
