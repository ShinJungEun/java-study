package chapter03;

public class Student extends Person {
	public Student() {
		// 자식 생성자에서 부모 생성자를 명시적으로 호출(super)하지 않으면,
		// 자동으로 부모의 기본 생성자를 호출하게 된다.
		super();	// 부모 호출(무조건 부모가 먼저 실행됨). 해당 코드를 넣지 않아도 자동으로 생성됨
		System.out.println("Student() called");
	}
}
