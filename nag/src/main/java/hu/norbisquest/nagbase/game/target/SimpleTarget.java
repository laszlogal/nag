package hu.norbisquest.nagbase.game.target;

public class SimpleTarget extends AbstractTarget {

	private String title;
	private int left;
	private int top;
	private int right;
	private int bottom;
	public SimpleTarget(String title) {
		this.title = title;
		setBounds(0, 0, 0, 0);
	}

	public SimpleTarget(String title, int left, int top, int right,
			int bottom) {
		this.title = title;
		setBounds(left, top, right, bottom);
	}

	@Override
	public boolean isHit(int x, int y) {
		return (x > getLeft() && x < getRight() && y > getTop()
				&& y < getBottom());

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	private int getRight() {
		return right;
	}

	protected void setRight(int right) {
		this.right = right;
	}

	private int getBottom() {
		return bottom;
	}

	protected void setBottom(int bottom) {
		this.bottom = bottom;
	}

	private void setBounds(int left, int top, int right, int bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

}
