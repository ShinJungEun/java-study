package chapter03;

public class StaticMethod {
	int n;
	static int m;
	
	void f1() {
		System.out.println(n);
	}
	
	void f2() {
		// instance method에서는 static 변수 접근 가능
		System.out.println(StaticMethod.m);	
		
		// 같은 클래스의 클래스변수 접근에서는 클래스이름 생략이 가능
		System.out.println(m);
	}
	
	void f3() {
		f2();
	}
	
	void f4() {
		StaticMethod.s1();
		
		// 같은 클래스의 클래스변수 접근에서는 클래스이름 생략이 가능
		s1();
	}
	
	static void s1() {
		// 오류 : static method에서 인스턴스 변수 접근 X
		// System.out.println(n);		
	}
	
	static void s2() {
		System.out.println(StaticMethod.m);	
		
		// 같은 클래스의 클래스변수 접근에서는 클래스이름 생략이 가능
		System.out.println(m);
	}
	
	static void s3() {
		// 오류 : static method에서 인스턴스 method 접근은 안됨
		// f1();
	}
	
	static void s4() {
		StaticMethod.s1();
		
		// 같은 클래스의 클래스변수 접근에서는 클래스이름 생략이 가능
		s1();
	}
	

	
}
