package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient {

	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 9999;

	public static void main(String[] args) {
		Scanner scanner = null;
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;

		try {
			// 1. Scanner 생성(표준입력, 키보드연결)
			scanner = new Scanner(System.in);

			// 2. Socket 생성
			socket = new Socket();

			// 3. 연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));

			// 4. reader writer 생성
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

			// 5. join protocol
			System.out.print("닉네임>>");
			String nickname = scanner.nextLine();
			pw.println("join:" + nickname);
			pw.flush();

			// 6. ChatClientReceiveThread 시작
			new ChatClientThread(br).start();

			// 7. 키보드 입력 처리
			while (true) {
				System.out.print(">>");
				String input = scanner.nextLine();

				if ("quit".equals(input)) {
					// 8. quit protocol 처리
					pw.println("quit");
					break;
					// 귓속말 protocol
				} else if (input.contains("whisper")) {
					pw.println(input);
					// quit protocol
				} else {
					// 9. message 처리
					pw.println("message:" + input);
				}
			}

		} catch (IOException e) {
		} finally {
			try {
				if (scanner != null)
					scanner.close();
				if (br != null)
					br.close();
				if (pw != null)
					pw.close();
				if (socket != null && !socket.isClosed())
					socket.close();

			} catch (IOException e) {
			}
		}
	}

	public static void log(String log) {
		System.out.println("[client]" + log);
	}

}

