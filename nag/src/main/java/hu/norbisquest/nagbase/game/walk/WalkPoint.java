package hu.norbisquest.nagbase.game.walk;
public class WalkPoint {
	private double x;
	private double y;
	private double scale;
	private String label;
	public WalkPoint(double x, double y, double scale) {
		this.x = x;
		this.y = y;
		this.label = "";
		this.scale = scale;
	}

	public WalkPoint duplicate() {
		return new WalkPoint(x, y, scale);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * @return the scale
	 */
	public double getScale() {
		return scale;
	}
	/**
	 * @param scale the scale to set
	 */
	public void setScale(double scale) {
		this.scale = scale;
	}
	
	public boolean equals(WalkPoint wp) {
		return (wp != null && wp.getX() == getX() && wp.getY() == getY());
	}

	public boolean same(WalkPoint wp, int t) {
		return (wp != null && Math.abs(wp.getX() - getX()) < t && Math.abs(wp.getY() - getY()) < t);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y +", s: " + scale +")";
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}