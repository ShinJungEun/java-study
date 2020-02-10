package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;

public class RequestHandler extends Thread {
	private static final String DOCUMENT_ROOT = "./webapp";
	private Socket socket;

	public RequestHandler( Socket socket ) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = ( InetSocketAddress )socket.getRemoteSocketAddress();
			consoleLog( "connected from " + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() );

			// get IOStream
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			OutputStream outputStream = socket.getOutputStream();

			String request = null;

			while(true) {
				String line = br.readLine();

				// 브라우저가 연결을 끊음
				if(line == null) { 		
					break;								
				}
				// Request(요청)의 헤더만 읽음
				if("".equals(line)) {
					break;
				}

				// header의 첫번째 라인만 읽음
				if(request == null) {
					request = line;
					break;
				}
			}

			consoleLog(request);

			String[] tokens = request.split(" ");
			if("GET".equals(tokens[0])) {		// Get방식	cf) Post방식도 있음
				// token[1] : uri,  token[2] : protocol
				responseStaticResource(outputStream, tokens[1], tokens[2]);
			}
			else {		// [POST, DELETY, PUT], HEAD, CONNECT => method들
				// *****과제 (POST방식 지원X)
				// 응답 예시
				// HTTP/1.1 400 Bad Request \r\n
				// Content-Type:text/html; charset=utf-8\r\n
				// \r\n
				// HTML 에러 문서(./webapp/error/400.html)

				 response400Error(outputStream, tokens[2]);

			}
			// 예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.
			//			outputStream.write( "HTTP/1.1 200 OK\r\n".getBytes( "UTF-8" ) );
			//			outputStream.write( "Content-Type:text/html; charset=utf-8\r\n".getBytes( "UTF-8" ) );
			//			outputStream.write( "\r\n".getBytes() );
			//			outputStream.write( "<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할 준비가 된 것입니다.</h1>".getBytes( "UTF-8" ) );

		} catch( Exception ex ) {
			consoleLog( "error:" + ex );
		} finally {
			// clean-up
			try{
				if( socket != null && socket.isClosed() == false ) {
					socket.close();
				}

			} catch( IOException ex ) {
				consoleLog( "error:" + ex );
			}
		}			
	}

	public void consoleLog( String message ) {
		System.out.println( "[RequestHandler#" + getId() + "] " + message );
	}

	private void responseStaticResource(
			OutputStream outputStream, String uri,
			String protocol) throws IOException {
		if("/".equals(uri)) {				// ex) GET / HTTP/1.1
			uri = "/index.html";
		}

		File file = new File(DOCUMENT_ROOT + uri);
		if(!file.exists()) {
			// 응답 예시
			// HTTP/1.1 404 Not Found\r\n
			// Content-Type:text/html; charset=utf-8\r\n
			// \r\n
			// HTML 에러 문서(./webapp/error/404.html)

			response404Error(outputStream, protocol);	// ****함수 만들기 과제
			return;
		}

		// nio(new io API)
		byte[] body = Files.readAllBytes(file.toPath());
		String contentType = Files.probeContentType(file.toPath());

		// 응답
		outputStream.write( (protocol + " 200 OK\r\n").getBytes( "UTF-8" ) );
		outputStream.write( ("Content-Type:" + contentType + "; charset=utf-8\r\n").getBytes( "UTF-8" ) );
		outputStream.write( "\r\n".getBytes() );
		outputStream.write( body );

	}
	
	public void response400Error(OutputStream outputStream, 
			String protocol) throws IOException{
		outputStream.write( ( protocol + " 400 Bad Request \r\n").getBytes());
		outputStream.write( "Content-Type: text/html\r\n".getBytes());
		outputStream.write( "\r\n".getBytes() );
		
		File file = new File(DOCUMENT_ROOT + "/error/400.html");
		byte[] body = Files.readAllBytes(file.toPath());
		outputStream.write(body);
	}
	
	public void response404Error(OutputStream outputStream,
			String protocol) throws IOException {
		outputStream.write( ( protocol + " 404 File Not Found \r\n").getBytes());
		outputStream.write( "Content-Type: text/html\r\n".getBytes());
		outputStream.write( "\r\n".getBytes() );
		
		File file = new File(DOCUMENT_ROOT + "/error/404.html");
		byte[] body = Files.readAllBytes(file.toPath());
		outputStream.write(body);
	}

}
