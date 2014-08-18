//ServerSocket을 만들고 클라이언트 요청이 들어올 때까지 기다림
//클라이언트 요청이 들어오면(즉, 어떤 클라이언트에서 이 애플리케이션에 연결하기 위한 Socket 객체를 새로 만들면)
//서버에서는 새 socket객체를 만들어서 그 클라이언트로 연결한다. 서버에서는 (socket의 출력 스트림을 이용하여)
//PrintWriter를 만들고 클라이언트로 메시지를 보낸다.

package chat;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyAdviceServer {
	
	String[] adviceList = {"조금씩 드세요","꼭 맞는 청바지를 입어보세요.별로 뚱뚱해 보이지 않을 거에요.",
			"딱 한마디만 하겠습니다: 좋지 않아요.","오늘 하루만 솔직해집시다. 윗사람한테 용감하게 의견을 말해보세요",
			"그 머리 스타일은 좀 안 어울리는 것 같은데요."};
	//이 배열에 들어있는 조언이 클라이언트로 전달됩니다.
	
	public void go(){
	
		try{
			ServerSocket serverSock = new ServerSocket(4242);
			
			System.out.println(getTime() + "서버가 준비되었습니다.");
			// ServerSocket을 통해 이 서버 애플리케이션은 이 코드가 실행되고 있는 시스템의 4242번 포트로 들어오는 클라이언트 요청을 감시한다.
			
			while(true){
				System.out.println(getTime() + "연결요청을 기다립니다.");
				Socket sock = serverSock.accept();
				sock.setSoTimeout(2000);
				//accept()메소드는 요청이 들어 올때까지 그냥 기다린다. 그리고 클라이언트 요청이 들어오면 클라이언트와 통신을 위해 (현재 쓰이고 있찌 않은 포트에 대한) Socket을 리턴함.
				System.out.println(getTime() +sock.getInetAddress() + "로부터 연결요청이 들어왔습니다.");
				
				PrintWriter writer = new PrintWriter(sock.getOutputStream());
				String advice = getAdvice();
				writer.println(advice);
				writer.close();
				//이제 클라이언트에 대한 Socket연결을 써서 PrintWriter를 만들고 클라이언트에 String 조언메시지를 보냄(println() 메소드 사용)
				//그리고 나면 클라이언트와의 작업이 끝난 것이므로 Socket을 닫는다.
				
				System.out.println(advice);
			}
			
		} catch(IOException ex){
			System.out.println("ssss");
			ex.printStackTrace();
		}
	} //go 메소드 
	
	private String getAdvice(){
		int random = (int) (Math.random() * adviceList.length);
		return adviceList[random];
	}
	
	public String getTime(){
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	}	
	public static void main(String[] args){
		DailyAdviceServer server = new DailyAdviceServer();
		server.go();
	}

}
