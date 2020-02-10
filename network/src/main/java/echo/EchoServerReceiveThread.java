package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {

	private Socket socket;
	
	public EchoServerReceiveThread(Socket socket) {
		this.socket = socket;
		
	}
	
	@Override
	public void run() {
		InetSocketAddress remoteInetSocketAddress 
		=(InetSocketAddress)socket.getRemoteSocketAddress();

		String remoteHostAddress = remoteInetSocketAddress.getAddress().getHostAddress();
		int remotePort = remoteInetSocketAddress.getPort();

		EchoServer.Log("connected by client[" + remoteHostAddress + ":" + remotePort +  "]");
		try {
			//4. IOStream 생성(받아오기)
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"), true);
			
			while(true) {
				//5. 데이터 읽기
				String data = br.readLine();
				if(data == null) {
					EchoServer.Log("closed by client");
					break;
				}
				EchoServer.Log("received:" + data);
				
				//6. 데이터 쓰기
				pw.println(data);
			}
		} catch(SocketException e) {
			EchoServer.Log("suddenly closed by client");
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}


