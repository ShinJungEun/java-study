package mypackage;

public class Goods {

	public static int countofGoods;

	public Goods() {
		Goods.countofGoods++;
		// countofGoods++; 도 가능하지만, 지양함.
	}

	public String name;			// 모든 접근 가능(접근 제한이 없음)
	protected int price;		// 같은 패키지 + 자식 접근 가능			
	int countSold;				// default. 같은 패키지		
	private int countStock;		// 한 클래스 내에서만 접근 가능


}
