package actua;

import java.io.IOException;
import java.io.Serializable;

public class Vector3D extends Vector2D implements Serializable {
	private static final long serialVersionUID = 530961272307619597L;
	private int z;

	/**
	 * @post getX() == 0 && getY() == 0 && getZ() == 0
	 */
	public Vector3D() {
		this(0, 0, 0);
	}

	/**
	 * @post getX() == x && getY() == y && getZ() == z
	 */
	public Vector3D(int x, int y, int z) {
		super(x, y);
		setZ(z);
	}

	// GETTERS en SETTERS

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

	// FILE I/O

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		x = in.readInt();
		y = in.readInt();
		z = in.readInt();
	}
}