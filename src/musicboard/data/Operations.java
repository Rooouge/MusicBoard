package musicboard.data;

import java.util.Date;

import javax.swing.JTextField;

import lombok.experimental.UtilityClass;
import musicboard.Global;
import musicboard.gui.board.BoardPanel;
import musicboard.gui.board.ItemPanel;
import musicboard.gui.board.StatePanel;

@UtilityClass
public class Operations {

	public void add(JTextField nameField, Object selected) {
		Board board = Global.get("board", Board.class);
		State state = board.findState("" + selected);
		
		int id = board.getNewId();
		String value = nameField.getText().trim();
		
		state.addItem(new Item(id, value, new Date()));
	}
	
	public void delete() {
		BoardPanel boardPanel = Global.get("board-panel", BoardPanel.class);
		
		for(StatePanel statePanel : boardPanel.getStatePanels()) {
			State state = statePanel.getState();
			
			for(ItemPanel itemPanel : statePanel.getItemPanels()) {
				if(itemPanel.getCheckBox().isSelected()) {
					state.removeItem(itemPanel.getItem());
				}
			}
		}
	}
	
	public void edit(Item item, String newValue, Date newCreationDate) {
		item.setValue(newValue);
		item.setCreationDate(newCreationDate);
	}
}
