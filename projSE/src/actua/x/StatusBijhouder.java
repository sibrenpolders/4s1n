package actua.x;


import java.util.ArrayList;

import actua.types.Memento;


public class StatusBijhouder {
	public ArrayList<Memento> redo;
	public ArrayList<Memento> undo;

	public StatusBijhouder() {
		
	}

	public void push_undo (Memento status) {
		
	}

	public Memento pop_redo () {
		return null;
	}

	public Memento pop_undo(){
		return null;
	}

	public void push_redo (Memento status) {
		
	}

}

