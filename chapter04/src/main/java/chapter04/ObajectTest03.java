package chapter04;

public class ObajectTest03 {

	public static void main(String[] args) {
		String s1 = new String("ABC");
		String s2 = new String("ABC");
		
		// 동일성 비교
		System.out.println(s1 == s2);
		
		// 동질성 비교(Override됨)
		System.out.println(s1.equals(s2));
		
		// 내용 기반(Override됨)
		System.out.println(s1.hashCode() + ":" + s2.hashCode());
		
		// 주소 기반
		System.out.println(System.identityHashCode(s1) + ":" + System.identityHashCode(s2));
	
		System.out.println("==========================");
		
		// literal => 상수는 재사용(효율성) : hash를 이용하여 리터럴 상수를 생성할 때 중복검사
		String s3 = "ABC";
		String s4 = "ABC";
		
		System.out.println(s3 == s4);
		System.out.println(s3.equals(s4));
		System.out.println(s3.hashCode() + ":" + s4.hashCode());
		System.out.println(System.identityHashCode(s3) + ":" + System.identityHashCode(s4));

	}

}
