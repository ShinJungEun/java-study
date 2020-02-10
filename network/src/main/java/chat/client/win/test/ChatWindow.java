package chat.client.win.test;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatWindow {

	//	private String nickname;
	private Socket socket;

	private Frame frame;
	private Panel pannel;
	private Button buttonSend;
	private TextField textField;
	private TextArea textArea;

	public ChatWindow(String nickname, Socket socket) {
		frame = new Frame(nickname);
		pannel = new Panel();
		buttonSend = new Button("Send");
		textField = new TextField();
		textArea = new TextArea(30, 80);

		//		this.nickname = nickname;
		this.socket = socket;
	}

	public void show() {
		// Button
		buttonSend.setBackground(Color.GRAY);
		buttonSend.setForeground(Color.WHITE);
		buttonSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				sendMessage();
			}
		});

		// Textfield
		textField.setColumns(80);
		textField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				char keyCode = e.getKeyChar();
				// enter 누르면 메세지 전송되도록
				if (keyCode == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}
		});

		// Pannel
		pannel.setBackground(Color.LIGHT_GRAY);
		pannel.add(textField);
		pannel.add(buttonSend);
		frame.add(BorderLayout.SOUTH, pannel);

		// TextArea
		textArea.setEditable(false);
		frame.add(BorderLayout.CENTER, textArea);

		// Frame
		frame.addWindowListener(new WindowAdapter() {
			ChatServerThread serverThread = null;

			// window open
			@Override
			public void windowOpened(WindowEvent e) {
				serverThread = new ChatServerThread();
				serverThread.start();
			}

			public void windowClosing(WindowEvent e) {
				PrintWriter pw = null;
				try {
					// serverThread 종료
					serverThread.join(100);

					pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

					// quit 보내고 처리 확인하기
					pw.println("quit");
					pw.flush();


				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
				} finally {
					try {
						// 소켓을 닫기
						if (socket != null && !socket.isClosed())
							socket.close();
					} catch (IOException e1) {
					}
					// 위 과정이 모두 끝나면 지금 이 thread를 종료하기
					System.exit(0);
				}

			}
		});

		frame.setVisible(true);
		frame.pack();

		while (true) {
			// breaking 효과
		}

	}

	// message send
	private void sendMessage() {
		String message = textField.getText();
		textField.setText("");
		textField.requestFocus();

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			pw.println("message:" + message);
			pw.flush();

		} catch (IOException e) {
		}
	}

	public static void log(String log) {
		System.out.println("[server#" + Thread.currentThread().getId() + "]" + log);
	}


	public class ChatServerThread extends Thread {

		@Override
		public void run() {
			BufferedReader br = null;
			String data = null;

			try {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
				while (true) {
					data = br.readLine();
					if (data == null) {
						log("연결 끊김");
						return;
					}
					else {
						textArea.append(data);
						textArea.append("\n");
					}
				}
			} catch (IOException e) {
			} finally {
				if (socket != null && !socket.isClosed())
					try {
						socket.close();
					} catch (IOException e) {
					}
			}
		}
	}

}
