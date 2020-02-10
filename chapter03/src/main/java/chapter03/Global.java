package chapter03;

public class Global {
	// 다른 class에서 사용하려면 꼭 static을 붙이자(객체로 사용)
	public static int globalVar = 100;
	public int var = 200;
	
	public static void globalFunc() {
		System.out.println("Hello World");
	}
}
