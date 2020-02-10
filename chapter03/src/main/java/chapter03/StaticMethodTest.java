package chapter03;

public class StaticMethodTest {

	public static void main(String[] args) {
		int a = Math.abs(-1);		// abs는 static method
		int b = Math.max(10, 20);

		System.out.println(a + ":" + b);
	}

}
