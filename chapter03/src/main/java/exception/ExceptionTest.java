package exception;

public class ExceptionTest {

	public static void main(String[] args) {
		int a = 10;
		int b = 11 - a;

		System.out.println("some code0");

		try {
			System.out.println("some code1");
			// 예외가 발생하면 바로 빠져나와서 예외처리
			int result = (1 + 2 + 3) / b;
			System.out.println("some code2");
		} catch(ArithmeticException ex) {
			// 1. 사과
			System.out.println("미안합니다.");
			// 2. 로그(파일, DB)
			System.out.println(ex);
			// 3. 정상종료
			return;
		} finally {			// option. 예외와는 관계 없이 무조건 실행이 됨
			System.out.println("some code4");
		}
		System.out.println("some code5");

		//		System.out.println(result);

	}

}
