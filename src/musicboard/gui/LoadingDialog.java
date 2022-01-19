package musicboard.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRootPane;

@SuppressWarnings("serial")
public class LoadingDialog extends JDialog {

	public LoadingDialog() {
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setLayout(new BorderLayout());
		
		add(new LoadingPanel(), BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
	}
	
	
	private class LoadingPanel extends JPanel {
		
		public LoadingPanel() {
			setPreferredSize(new Dimension(300, 200));
		}
		
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			int w = getWidth();
			int h = getHeight();
			Graphics2D g2d = (Graphics2D) g;
			
			Color color1 = Color.decode("#348F50");
			Color color2 = Color.decode("#56B4D3");
			GradientPaint gp = new GradientPaint(w/4f, 0, color1, 3*w/4f, h, color2);
			
			g2d.setPaint(gp);
			g2d.fillRect(0, 0, w, h);
			
			String str = "Loading...";
			g.setFont(g.getFont().deriveFont(16f).deriveFont(Font.BOLD));
			FontMetrics fm = g2d.getFontMetrics();
			Rectangle2D r = fm.getStringBounds(str, g2d);
			
			int x = (this.getWidth() - (int) r.getWidth()) / 2;
			int y = 4*(this.getHeight() - (int) r.getHeight()) / 5 + fm.getAscent();
			System.out.println("X: " + x + ", Y: " + y);
			
			g.setColor(Color.white);
			g.drawString(str, x, y);			
		}
	}
}
