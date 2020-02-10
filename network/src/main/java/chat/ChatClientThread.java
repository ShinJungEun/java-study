package chat;

import java.io.BufferedReader;
import java.io.IOException;

public class ChatClientThread extends Thread {

	private BufferedReader br;

	public ChatClientThread(BufferedReader br) {
		this.br = br;
	}

	@Override
	public void run() {
		// reader를 통해 읽은 데이터 콘솔에 출력(message 처리)
		String data = null;
		try {
			while (true) {
				data = br.readLine();

				if (data == null) {
					ChatClient.log("closed by server");
					break;
				} 
				else if ("quit".equals(data)) {
					break;
				}
//				else if("whisper")
				System.out.println(data);
			}
		} catch (IOException e) {
		} finally {
			try {
				br.close();
			} catch (IOException e) {
			}

		}
	}

}
