package musicboard.data;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class State {

	private String name;
	private Color color;
	private List<Item> items;
	
	public State(String name, String colorString) {
		this.name = name;
		this.color = Color.decode(colorString);
		this.items = new ArrayList<>();
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public boolean removeItem(Item item) {
		return items.remove(item);
	}
	
	public int getItemsCount() {
		return items.size();
	}
}
