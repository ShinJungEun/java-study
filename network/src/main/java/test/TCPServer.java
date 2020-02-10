package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {		// server socket에 대한 IOEXception
			// 1. 서버소켓 생성
			serverSocket = new ServerSocket();

			// 1-1. Time-Wait 시간에 소켓에 포트번호 할당을 가능하게 하기 위해서
			serverSocket.setReuseAddress(true);



			// 2. 바인딩: Socket Address(IP Address + Port) Binding
			serverSocket.bind(new InetSocketAddress("127.0.0.1", 6000));


			// 3. accept
			Socket socket = serverSocket.accept();	  // blocking.(sleep상태.멈춰있음)
			InetSocketAddress remoteInetSocketAddress 
			=(InetSocketAddress)socket.getRemoteSocketAddress();

			InetAddress remoteInetAddress = remoteInetSocketAddress.getAddress();
			String remoteHostAddress = remoteInetAddress.getHostAddress();
			int remotePort = remoteInetSocketAddress.getPort();

			System.out.println("[server]connectied by client[" 
					+ remoteHostAddress + ":" + remotePort +  "]" );

			//			System.out.println("remoteInetAddress :" + remoteInetAddress);
			//			System.out.println("remoteHostAddress :" + remoteHostAddress);


			try {		// socket에 대한 IOException
				// 4. IOStream 받아오기
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				while(true) {
					// 5. data 읽기
					byte[] buffer = new byte[256];

					int readByteCount = is.read(buffer);	// blocking
					if(readByteCount == -1) {		// -1이 아니라면, data를 읽어온 것임.
						// client에서 정상종료
						System.out.println("[server]close by client");
						break;
					}

					// encoding ( 3byte씩 잘라 string으로 )
					String data = new String(buffer, 0, readByteCount, "UTF-8");
					System.out.println("received:" + data);


					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// 6. 데이터 쓰기
					os.write(data.getBytes("UTF-8"));
				}

			} catch (SocketException e) {
				System.out.println("[server] suddenclosed by client");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(socket != null && !socket.isClosed())
					socket.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(serverSocket != null && !serverSocket.isBound())
					serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
