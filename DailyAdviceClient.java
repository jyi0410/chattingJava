// 이 프로그램에서는 Socket과 BufferedReader를 만들고(중간에 다른 스트림도 필요함) 서버 애플리이션(4242번 포트에서 돌아가고
// 있는 애플리케이션)으로부터 한 행을 읽어옵니다.

package chat;

import java.io.*;
import java.net.*;  
//socket 클래스가 java.net에 들어 있다.

public class DailyAdviceClient {
	
	public void go(){
		try{
			//잘못될 수 있는 부분이 많기 때문에 try/catch를 써야 합니다.
			
			Socket s = new Socket("127.0.0.1", 4242);
			//이 코드가 실행되는 것과 같은 호스트("localhost")의 4242번 포트에서 실행중인 애플리케이션에 대한 Socket 연결을 만다.
			
			InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
			//InputStreamReader는 저수준 바이트 스트림과 고수준 문자 스트림을 이어주는 '다리'
			// 저수준 바이트 스트림 : Socket에서 오는 것과 같은 스트림
			// 고수준 문자 스트림 : BufferedReader와 같이 우리가 직접 사용할 맨 위에 있는 스트림
			
			BufferedReader reader = new BufferedReader(streamReader);
			//Socket으로부터의 입력 스트림에 대한 InputStreamReader에 BufferedReader를 연쇄시킨다.
			
			String advice = reader.readLine();
			//이 readLine()은 파일에 연쇄된 BufferedReader를 쓸 대 사용한 readLine()과 완전히 똑같이 쓰면 된다.
			//즉 BufferedReader의 메소드를 호출할 때 그 객체에서는 문자들이 어디에서 오는지에 대해서 전혀 신경 쓰지 않음
			
			System.out.println("Today you should : " + advice);
			
			
			reader.close();
			//이렇게 하면 모든 스트림이 닫힌다.
		} catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		DailyAdviceClient client = new DailyAdviceClient();
		client.go();
	}

}


