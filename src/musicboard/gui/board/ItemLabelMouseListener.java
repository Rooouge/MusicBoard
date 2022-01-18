package musicboard.gui.board;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import jutils.config.Images;
import jutils.random.Utils;
import jutils.strings.Strings;
import lombok.AllArgsConstructor;
import musicboard.Global;
import musicboard.data.Item;
import musicboard.data.xml.XML;

@AllArgsConstructor
public class ItemLabelMouseListener implements MouseListener {

	private ItemPanel itemPanel;
	
	
	@Override
	public void mouseClicked(MouseEvent evt) {
		// Empty
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
		// Empty
	}

	@Override
	public void mouseExited(MouseEvent evt) {
		// Empty
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		// Empty
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		if(SwingUtilities.isMiddleMouseButton(evt)) {
			Item item = itemPanel.getItem();
			
			String response = (String) JOptionPane.showInputDialog(
					itemPanel, 
					"Change the value of the selected item", 
					"Edit", 
					JOptionPane.PLAIN_MESSAGE, 
					Images.getImageIcon("edit"), 
					null, 
					item.getValue()
			);
			
			if(!Strings.isVoid(response)) {
				item.setValue(response.trim());
				
				try {
					XML.write();
					Global.refreshGui();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				Utils.beep();
			}
		}
	}

}
