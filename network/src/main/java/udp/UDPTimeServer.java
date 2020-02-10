package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {

	private static final int PORT = 7000;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			// 1. socket 생성
			socket = new DatagramSocket(PORT);

			while(true) {
				// 2. data 수신
				// bute buffer와 byte size
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket);		// blocking

				// encoding
				byte[] data = receivePacket.getData(); 
				String str = new String(data);
				str = str.trim();

				if("\"\"".equals(str)) {
					SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss a" );
					String dateData = format.format( new Date() );
					System.out.println("datedata:" + dateData);
					byte[] sendData = dateData.getBytes("UTF-8");
					
					DatagramPacket sendPacket 
					= new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());

					socket.send(sendPacket);
					
					continue;
				}

				int length = receivePacket.getLength();
				String message = new String(data, 0, length, "UTF-8");

				System.out.println("[server] received:" + message);

				
				// 3. data 송신 
				byte[] sendData = message.getBytes("UTF-8");
				DatagramPacket sendPacket 
				= new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());

				socket.send(sendPacket);

			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(socket != null && !socket.isClosed()) {
				socket.close();
			}
		}
	}



}
