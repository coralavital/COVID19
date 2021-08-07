package UI;

import java.util.ArrayList;

public class PathTaker {
	
	private ArrayList<Memento> stateList = new ArrayList<Memento>();
	
	public void addMemento(Memento m) {
		this.stateList.add(m);
	}
	
	public Memento getMemento(int index) {
		return stateList.get(index);
	}
}
