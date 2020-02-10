package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChatServerThread extends Thread {

	private String nickname;
	private Socket socket;
	private BufferedReader br = null;
	private PrintWriter pw = null;
	private static Map<String, PrintWriter> map = Collections.synchronizedMap(new HashMap<String, PrintWriter>());

	public ChatServerThread(Socket socket) {
		this.socket = socket;
	}

//	public ChatServerThread(Socket socket, HashMap<String, PrintWriter> map) {
//		this.socket = socket;
//		this.map = map;
//	}

	@Override
	public void run() {
		try {

			// 2. 스트림 얻기
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

			String request = null;
			// 3. 요청 처리
			while (true) {
				request = br.readLine();
				if (request == null) {
					ChatServer.log("클라이언트로부터 연결 끊김");
					break;
				}

				// 4. 프로토콜 분석
				String[] tokens = request.split(":");

				if ("join".equals(tokens[0])) {
					doJoin(tokens[1], pw);
				} else if ("message".equals(tokens[0])) {
					doMessage(tokens[1]);
				} else if ("quit".equals(tokens[0])) {
					doQuit(pw);
				} else if ("whisper".equals(tokens[0])) {
					// tokens[1] : nickname, tockens[2] : message
					doWhisper(tokens[1], tokens[2]);
				} else {
					ChatServer.log("에러: 알 수 없는 요청(" + tokens[0] + ")");
				}

			}
		} catch (IOException e) {
		} finally {
			if (socket != null && !socket.isClosed())
				try {
					socket.close();
				} catch (IOException e) {
				}
			if (br != null)
				try {
					br.close();
					System.exit(0);
				} catch (IOException e) {
				}
			if (pw != null)
				pw.close();
		}

	}

	private void doJoin(String nickName, PrintWriter pw) {
		this.nickname = nickName;
		String data = nickName + "님이 참여하였습니다.";
		broadcast(data);
		addWriter(nickName, pw);

		// ack
		pw.println("join:ok");
		pw.println("귓속말을 보내시려면 whisper:닉네임:메시지 를 입력하세요.");
		pw.println("퇴장하시려면 quit을 입력하세요.");
		pw.flush();
	}

	private void addWriter(String nickName, PrintWriter pw) {
		synchronized (map) {
			map.put(nickName, pw);
		}
	}

	private void doQuit(PrintWriter pw) {
		System.out.println("접속 종료");
		String data = nickname + "님이 퇴장하셨습니다.";

		broadcast(data);

		removeWriter(pw);

	}

	private void removeWriter(PrintWriter pw) {
		synchronized (map) {
			map.remove(nickname);
		}
	}

	private void doMessage(String string) {
		broadcast(nickname + ":" + string);
	}

	private void broadcast(String data) {
		synchronized (map) {
			for (Map.Entry<String, PrintWriter> entry : map.entrySet()) {
				PrintWriter pw = entry.getValue();

				pw.println(data);
				pw.flush();

			}

		}
	}

	private void doWhisper(String nickName, String message) {
		synchronized (map) {
			PrintWriter receivePw = map.get(nickName);
			pw.println(nickName + "에게:" + message);
			receivePw.println(nickname + "로부터 온 메세지: " + message);
			pw.flush();
			receivePw.flush();
		}
	}

}
