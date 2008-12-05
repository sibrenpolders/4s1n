package actua;

public class Vector2D {
	protected int y;
	protected int x;

	/**
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
}
