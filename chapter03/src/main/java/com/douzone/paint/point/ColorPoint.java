package com.douzone.paint.point;

public class ColorPoint extends Point {
	private String color;
	
	public ColorPoint(int x, int y, String color) {
		super(x, y);	// 부모 생성자 호출 명시함.
		setX(x);		// this.setX(x) 이지만 this 생략 가능
		setY(y);
		this.color = color;		
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public void show() {
		System.out.println("점[x=" + getX() + ",y=" + getY() + ",color=" + color + "]을 그렸습니다.");		
		
//		부모코드
//		super.show();		
	}
	
	
}
