package actua.types;
public class Vector3D extends Vector2D {
	private int z;

	public Vector3D() {
		super.x = 0;
		super.y = 0;
		z = 0;
	}

	public Vector3D(int x, int y, int z) {
		super.x = x;
		super.y = y;
		this.z = z;
	}

	public int getZ () {
		return z;
	}

	public void setZ (int z) {
		this.z = z;
	}

	public void set(int x, int y, int z) {
		super.set(x, y);
		this.z = z;
	}

}