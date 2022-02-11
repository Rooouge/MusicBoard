package musicboard.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import jutils.config.Config;
import jutils.gui.ColoredPanel;
import jutils.gui.Colors;
import jutils.gui.EmptyBorder;
import jutils.log.Log;

@SuppressWarnings("serial")
public class Footer extends ColoredPanel {

	public Footer() throws URISyntaxException {
		super(
			Colors.GRAY_48,
			new BorderLayout(),
			BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.white), new EmptyBorder(5))
		);
		
//		JLabel label = new JLabel(Config.getValue("footer").trim(), SwingConstants.CENTER);
//		label.setForeground(Color.white);
//		label.setBackground(null);
//		add(label, BorderLayout.CENTER);
		
		final URI uri = new URI(Config.getValue("youtube-link"));
		
		JButton linkButton = new JButton(Config.getValue("youtube-label"));
		linkButton.setHorizontalAlignment(SwingConstants.CENTER);
		linkButton.setBorderPainted(false);
		linkButton.setContentAreaFilled(false);
		linkButton.setBackground(null);
		linkButton.setForeground(Color.decode("#3399FF"));
		linkButton.setToolTipText(uri.toString());
		linkButton.addActionListener(e -> {
			if(Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(uri);
				} catch (IOException e1) {
					Log.error("Failed to open URI '" + uri.toString() + "'");
					e1.printStackTrace();
				};
			}
		});
		
		add(linkButton, BorderLayout.CENTER);
	}

}
