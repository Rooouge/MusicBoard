package musicboard.data;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import lombok.experimental.UtilityClass;
import musicboard.Global;
import musicboard.gui.board.BoardPanel;
import musicboard.gui.board.ItemPanel;
import musicboard.gui.board.StatePanel;

@UtilityClass
public class Operations {

	public void add(JTextField nameField, JComboBox<String> stateBox) {
		Board board = Global.get("board", Board.class);
		State state = board.findState("" + stateBox.getSelectedItem());
		
		int id = board.getNewId();
		String value = nameField.getText().trim();
		
		state.addItem(new Item(id, value));
	}
	
	public void delete() {
		BoardPanel boardPanel = Global.get("board-panel", BoardPanel.class);
		
		for(StatePanel statePanel : boardPanel.getStatePanels()) {
			State state = statePanel.getState();
			
			for(ItemPanel itemPanel : statePanel.getItemPanels()) {
				System.out.println(itemPanel.getItem().getValue() + " - " + itemPanel.getCheckBox().isSelected());
				if(itemPanel.getCheckBox().isSelected()) {
					state.removeItem(itemPanel.getItem());
				}
			}
		}
	}
}
