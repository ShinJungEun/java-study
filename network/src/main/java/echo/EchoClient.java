package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 8000;

	public static void main(String[] args) {
		Scanner scanner = null;
		Socket socket = null;

		try {
			// 1. Scanner 생성(표준입력, 키보드연결)
			scanner = new Scanner(System.in);

			// 2. Socket 생성
			socket = new Socket();

			// 3. 서버연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			Log("connected");

			// 4. IOStream 생성(받아오기)
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			while(true) {
				// 5. 키보드 입력 받기
				System.out.print(">>");
				String line = scanner.nextLine();

				if("quit".equals(line))
					break;

				// 6. data 쓰기
				pw.println(line);

				// 7. data 읽기
				String data = br.readLine();
				if(data == null) {
					Log("closed by server");
					break;
				}

				System.out.println("<<" + data);
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(scanner != null)
					scanner.close();

				if(socket != null && !socket.isClosed())
					socket.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void Log(String log) {
		System.out.println("[client]" + log);
	}


}
