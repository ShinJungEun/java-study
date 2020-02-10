package chapter04;

public class Rect {
	private int width;
	private int height;
	
	public Rect(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public String toString() {
		return "Rect [width=" + width + ", height=" + height + "]";
	}
	
}
