package musicboard.gui.board;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import jutils.gui.EmptyBorder;
import lombok.Getter;
import musicboard.Global;
import musicboard.data.Board;
import musicboard.data.State;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {

	private Board board;
	@Getter
	private List<StatePanel> statePanels;
	
	
	public BoardPanel() {		
		board = Global.get("board", Board.class);
		statePanels = new ArrayList<>();
		List<State> states = board.getStates();
		
		int row = 1;
		int column = states.size();
		
		if(states.size() % 3 == 0) {
			row = states.size() / 3;
			column = 3;
		}
		
		this.setLayout(new GridLayout(row, column, 5, 5));
		this.setBorder(new EmptyBorder(15));
		
		for(State state : states) {
			StatePanel statePanel = new StatePanel(state);
			add(statePanel);
			statePanels.add(statePanel);
		}
		
		Global.add("board-panel", this);
	}
	
	
}
