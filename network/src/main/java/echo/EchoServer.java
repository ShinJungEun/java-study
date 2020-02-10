package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {

	private static int PORT = 8000; 

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {		// server socket에 대한 IOEXception
			// 1. 서버소켓 생성
			serverSocket = new ServerSocket();


			// 2. 바인딩: Socket Address(IP Address + Port) Binding
			serverSocket.bind(new InetSocketAddress("127.0.0.1", PORT));
			Log("Server Starts...[port:" + PORT + "]");

			while(true) {
				// 3. accept
				Socket socket = serverSocket.accept();	  // blocking.(sleep상태.멈춰있음)

				new EchoServerReceiveThread(socket).start();
			}} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(serverSocket != null && !serverSocket.isClosed()) {
						serverSocket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	public static void Log(String log) {
		System.out.println("[server#" + Thread.currentThread().getId() + "]"+ log);
	}

}
