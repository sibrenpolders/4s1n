package actua;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class Vector2D implements Serializable{
	private static final long serialVersionUID = 8609844615654347035L;
	protected int y;
	protected int x;
	
	/**
	 * @param  
	 * @post getX() == 0 && getY() == 0
	 */
	public Vector2D() {
		setX(0);
		setY(0);
	}

	/**
	 * @post getX() == x && getY() == y
	 */
	public Vector2D(int x, int y) {
		setX(x);
		setY(y);
	}

	public Vector2D(Vector2D andereVector) {
		this.x = andereVector.x;
		this.y = andereVector.y;
	}
	
	/**
	 * @post getX() == x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @post getY() == y
	 */
	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return new String("(" + x + ", " + y + ")");
	}
	
	 private void writeObject(java.io.ObjectOutputStream out)throws IOException {
		 out.writeInt(x);
		 out.writeInt(y);
	 }
	 
	 private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		 x = in.readInt();
		 y = in.readInt();
	 }
	 
	 private void readObjectNoData() throws ObjectStreamException {
		 
	 }
}
