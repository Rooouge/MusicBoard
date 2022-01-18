package musicboard.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Board {

	@Setter
	private int counter;
	private List<State> states;
	
	
	public Board() {
		counter = 0;
		states = new ArrayList<>();
	}
	
	
	public void addState(State state) {
		states.add(state);
	}
	
	public int getStatesCount() {
		return states.size();
	}
	
	public State findState(String name) {
		for(State state : states) {
			if(state.getName().equals(name))
				return state;
		}
		
		return null;
	}
	
	public int getNewId() {
		counter++;
		return counter-1;
	}
}
