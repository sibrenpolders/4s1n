package actua;

public class Vector3D extends Vector2D {
	private int z;

	/**
	 * @post getX() == 0 && getY() == 0 && getZ() == 0
	 */
	public Vector3D() {
		super();
		setZ(0);
	}

	/**
	 * @post getX() == x && getY() == y && getZ() == z
	 */
	public Vector3D(int x, int y, int z) {
		super(x, y);
		setZ(z);
	}

	public int getZ() {
		return z;
	}

	/**
	 * @post getZ() == z
	 */
	public void setZ(int z) {
		this.z = z;
	}

	public void setXYZ(int x, int y, int z) {
		setXY(x, y);
		setZ(z);
	}
}