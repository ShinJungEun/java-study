package chat.client.win.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClientApp {

	private static String SERVER_IP = "0.0.0.0";
	private static int SERVER_PORT = 9999;

	public static void main(String[] args) {
		String nickname = null;
		Scanner scanner = new Scanner(System.in);

		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;

		try {
			// 1. socket 생성
			socket = new Socket();

			// 2. connect to server
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));

			// 3. ioStream생성 (보조)
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

			// 4. join protocol 요청 및 처리
			while (true) {

				System.out.println("대화명을 입력하세요.");
				System.out.print(">>> ");
				nickname = scanner.nextLine();

				if (nickname.isEmpty() == false) {
					break;
				}

				System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");

			}
			pw.println("join:" + nickname);
			pw.flush();

			// 만약에 join:ok를 받았을 때에 window를 열도록 한다.
			String response = br.readLine();
			if (response.equalsIgnoreCase("join:ok")) {
				// 5. join protocol이 성공 응답을 받으면 실행
				new ChatWindow(nickname, socket).show();
			} else {
				System.out.println("join 수신 실패...");
			}

		} catch (IOException e) {
		} finally {
			if (socket != null && !socket.isClosed()) {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
		scanner.close();

	}

}
