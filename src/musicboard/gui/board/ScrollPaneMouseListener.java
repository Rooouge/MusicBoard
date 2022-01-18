package musicboard.gui.board;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import jutils.log.Log;
import jutils.random.Utils;
import lombok.AllArgsConstructor;
import musicboard.Global;
import musicboard.data.Board;
import musicboard.data.Item;
import musicboard.data.State;
import musicboard.data.xml.XML;

@AllArgsConstructor
public class ScrollPaneMouseListener implements MouseListener {

	private StatePanel parent;
	
	
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
		if(SwingUtilities.isRightMouseButton(evt)) {
			JPopupMenu popup = new JPopupMenu("Move to");
			
			String parentName = parent.getState().getName();
			List<String> stateNamesList = new ArrayList<>();
			
			for(State state : Global.get("board", Board.class).getStates()) {
				String name = state.getName();
				
				if(!name.equals(parentName))
					stateNamesList.add(name);
			}
			
			for(String name : stateNamesList) {
				JMenuItem menuItem = new JMenuItem(name);
				
				menuItem.addActionListener(e -> {
					State state = parent.getState();
					State newState = getNewState(menuItem.getText());
					List<Item> items = new ArrayList<>();
					
					for(ItemPanel panel : parent.getItemPanels()) {
						if(panel.getCheckBox().isSelected())
							items.add(panel.getItem());
					}
					
					for(Item item : items) {
						if(state.removeItem(item))
							newState.addItem(item);
						else
							Log.error("Failed to move from \"" + state.getName() + "\" to \"" + newState.getName() + "\"");
					}
					
					try {
						XML.write();
						Global.refreshGui();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					Utils.beep();
				});
				
				popup.add(menuItem);
			}
			
			
			popup.show(parent, evt.getX(), evt.getY());
		}
	}
	
	
	private State getNewState(String name) {
		List<String> stateNames = Arrays.asList(Global.get("states", String.class).split(";"));
		
		for(String stateName : stateNames) {
			if(stateName.equals(name))
				return Global.get("board", Board.class).findState(name);
		}
		
		return null;		
	}
}
