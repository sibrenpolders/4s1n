package GUI;

import java.io.IOException;
import java.io.Serializable;

import Core.Vector3D;

public class Camera implements Serializable {
	private static final long serialVersionUID = -8032130706888345396L;
	private Vector3D minVector;
	private Vector3D huidigeVector;
	private Vector3D maxVector;

	public Camera() {
		huidigeVector = new Vector3D();
		maxVector = new Vector3D();
		minVector = new Vector3D();
	}

	// GETTERS en SETTERS

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

	// CAMERA BEWEGEN

	public boolean isBewegingGeldig(Vector3D bewegingsVector) {
		int xm = minVector.getX(), ym = minVector.getY(), zm = minVector.getZ();
		int xM = maxVector.getX(), yM = maxVector.getY(), zM = maxVector.getZ();
		int x = bewegingsVector.getX(), y = bewegingsVector.getY(), z = bewegingsVector
				.getZ();

		return (x <= xM && x >= xm) && (y <= yM && y >= ym)
				&& (z <= zM && z >= zm);
	}

	public boolean veranderStandpunt(Vector3D bewegingsVector) {
		if (isBewegingGeldig(bewegingsVector)) {
			huidigeVector = bewegingsVector;
			return true;
		}
		return false;
	}

	public void herstelOverzicht() {
		huidigeVector.setXYZ(0, 0, 0);
	}

	//
	// File I/O-functies
	//

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(minVector);
		out.writeObject(huidigeVector);
		out.writeObject(maxVector);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		minVector = (Vector3D) in.readObject();
		huidigeVector = (Vector3D) in.readObject();
		maxVector = (Vector3D) in.readObject();
	}
}