package actua;


public class Camera {
	private Vector3D minVector;
	private Vector3D huidigeVector;
	private Vector3D maxVector;

	public Camera() {

	}

	public Camera(Vector3D minVector, Vector3D maxVector) {
		this.minVector = minVector;
		this.maxVector = maxVector;
	}

	public boolean bewegingGeldig(Vector3D bewegingsVector) {
		int xm = minVector.getX(), ym = minVector.getY(), zm = minVector.getZ();
		int xM = maxVector.getX(), yM = maxVector.getY(), zM = maxVector.getZ();
		int x = bewegingsVector.getX(), y = bewegingsVector.getY(), z = bewegingsVector
				.getZ();

		return (x <= xM && x >= xm) && (y <= yM && y >= ym)
				&& (z <= zM && z >= zm);
	}

	public void veranderStandpunt(Vector3D bewegingsVector) {
		huidigeVector = bewegingsVector;
	}

	protected void maakVeranderingen(Vector3D beweging) {

	}

	public void herstelOverzicht() {
		huidigeVector.set(0, 0, 0);
	}

	public Vector3D getMinVector() {
		return minVector;
	}

	public void setMinVector(Vector3D minVector) {
		this.minVector = minVector;
	}

	public Vector3D getHuidigeVector() {
		return huidigeVector;
	}

	public void setHuidigeVector(Vector3D huidigeVector) {
		this.huidigeVector = huidigeVector;
	}

	public Vector3D getMaxVector() {
		return maxVector;
	}

	public void setMaxVector(Vector3D maxVector) {
		this.maxVector = maxVector;
	}

}