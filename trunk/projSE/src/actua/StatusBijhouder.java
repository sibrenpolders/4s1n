package actua;

import java.util.ArrayDeque;

public class StatusBijhouder {
	private static int MAX_SIZE = 100;
	
	private ArrayDeque<Memento> redo;
	private ArrayDeque<Memento> undo;
	
	public StatusBijhouder() {
		undo = new ArrayDeque<Memento>();
		redo = new ArrayDeque<Memento>();		
	}

	public void push_undo (Memento status) {
		push(undo, status);
	}

	public Memento pop_undo(){
		Memento ret = pop(undo);
		if( ret != null )
			push_redo(ret);
		return ret;
	}
	
	public void push_redo (Memento status) {
		push(redo, status);
	}
	
	public Memento pop_redo () {
		Memento ret = pop(redo);
		if( ret != null )
			push_undo(ret);
		return ret;
	}

	private Memento pop(ArrayDeque<Memento> stack) {
		if (0 == stack.size()) {
			return null;
		}
		
		Memento ret = stack.getFirst();
		stack.removeFirst(); // stack nabootsen, als er een Object wordt teruggeven is dit meteen ook verwijderd
		return ret;
	}
	
	private void push(ArrayDeque<Memento> stack, Memento status) {
		if (MAX_SIZE == stack.size()) { // is de maximale stack grootte overschreden?
			stack.removeLast();
		}
		
		stack.addFirst(status);
	}

	// deze functies worden enkel in de unit tests gebruikt
	public int getUndoSize() {
		return undo.size();
	}
	
	// deze functies worden enkel in de unit tests gebruikt
	public int getRedoSize() {
		return redo.size();		
	}
}

