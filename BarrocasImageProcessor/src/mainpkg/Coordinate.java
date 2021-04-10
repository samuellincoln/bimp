package mainpkg;
public class Coordinate {
	private int x;
	private int y;
	public Coordinate (int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isInvalid () {
		return this.x == -1 && this.y == -1;
	}
	public String coordToString () {
		return "(" + x + ", " + y + ")";
	}
}
