package musicboard.gui.board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import jutils.config.Config;
import jutils.gui.EmptyBorder;
import jutils.gui.TransparentPanel;
import lombok.Getter;
import musicboard.data.Item;
import musicboard.data.State;

@SuppressWarnings("serial")
@Getter
public class StatePanel extends JPanel {
	
	private State state;
	private List<ItemPanel> itemPanels;
	
	
	public StatePanel(State state) {
		this.state = state;
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED), new EmptyBorder(5)));
		setBackground(state.getColor());
		setLayout(new BorderLayout(10, 10));
		
		setPreferredSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().width / 4.8), Integer.parseInt(Config.getValue("state-panel-height"))));
		
		this.add(namePanel(), BorderLayout.NORTH);
		this.add(itemsPanel(), BorderLayout.CENTER);
	}
	
	
	private JPanel namePanel() {
		JLabel nameLabel = new JLabel(state.getName());
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBackground(null);
		nameLabel.setFont(nameLabel.getFont().deriveFont(16f));
		
		TransparentPanel panel = new TransparentPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black), new EmptyBorder(5)));
		panel.add(nameLabel, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JScrollPane itemsPanel() {
		itemPanels = new ArrayList<>();
		int minRows = Integer.parseInt(Config.getValue("minimum-rows"));
		int items = state.getItemsCount();
		
		TransparentPanel itemsPanel = new TransparentPanel(new GridLayout(items < minRows ? minRows : items, 1));
		Color stateBg = state.getColor();
		Color brighter = new Color(
				(stateBg.getRed() + 32) > 255 ? 255 : (stateBg.getRed() + 32), 
				(stateBg.getGreen() + 32) > 255 ? 255 : (stateBg.getGreen() + 32), 
				(stateBg.getBlue() + 32) > 255 ? 255 : (stateBg.getBlue() + 32) 
				);
		
		for(Item item : state.getItems()) {
			ItemPanel itemPanel = new ItemPanel(item, brighter);
			
			itemsPanel.add(itemPanel);
			itemPanels.add(itemPanel);
		}
		
		JScrollPane pane = new JScrollPane(itemsPanel);
		pane.setBounds(0, 0, this.getPreferredSize().width, this.getPreferredSize().height);
		pane.setBackground(brighter);
		pane.getViewport().setBackground(brighter);
		pane.addMouseListener(new ScrollPaneMouseListener(this));
		pane.getHorizontalScrollBar().setUnitIncrement(Integer.parseInt(Config.getValue("state-panel-scroll-unit-h")));
		pane.getVerticalScrollBar().setUnitIncrement(Integer.parseInt(Config.getValue("state-panel-scroll-unit-v")));
		
		return pane;
	}
	
}
