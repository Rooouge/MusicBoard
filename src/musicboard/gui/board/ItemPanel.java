package musicboard.gui.board;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lombok.Getter;
import musicboard.data.Item;

@SuppressWarnings("serial")
@Getter
public class ItemPanel extends JPanel {

	private Item item;
	private JCheckBox checkBox;
	
	
	public ItemPanel(Item item, Color background) {
		this.item = item;
		
		this.setBackground(background);
		this.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		
		this.add(checkBox());
		this.add(label());
	}
	
	
	private JCheckBox checkBox() {
		checkBox = new JCheckBox();
		checkBox.setBackground(null);
		
		return checkBox;
	}
	
	private JLabel label() {
		JLabel label = new JLabel(item.getValue());
		label.setBackground(null);
		label.addMouseListener(new ItemLabelMouseListener(this));
		
		return label;
	}
}
