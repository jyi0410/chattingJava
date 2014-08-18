//메시지를 입력하고 Send를 누르면 서버로 메시지를 보내기
//서버로부터 아무 메시지도 받지 않을 것!! 스크롤 텍스트 영역 만들지 않음 

package chat;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatCleintA {

	JTextField outgoing;
	PrintWriter writer;
	Socket sock;
	
	public void go(){
		//GUI를 만들고 send 버튼에 대한 리스너를 등록한다.
		//setUpNetWorking 메소드를 호출한다.
		
		JFrame frame = new JFrame("서버에게 보낸다 메시지 !!"); //윗줄에 나타나는 부
		JPanel mainPanel = new JPanel();
		outgoing = new JTextField(20); //초기 input창 글자수 
		JButton sendButton = new JButton("Send"); //Send라는 버튼을 만든다.
		sendButton.addActionListener(new SendButtonListener()); 
		mainPanel.add(outgoing);
		mainPanel.add(sendButton); //메인 패널에 인풋창과 send버튼을 추가할것이다.
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		
		setUpNetWorking();
		
		frame.setSize(400,100); //창크기 
		frame.setVisible(true);
	}//go 메소드 끝
	
	
	
	private void setUpNetWorking(){
		//socket을 만들고 PrintWriter를 만든다.
		// 그 PrintWriter를 writer인스턴스 변수에 대입함.
		
		try{
			sock = new Socket("127.0.0.1", 5000);
			//한 시스템에서 서버와 클라이언트를 모두 테스트할 수 있도록 하기 위해 localhost를 사용!
			writer = new PrintWriter(sock.getOutputStream());
			//Socket과 PrintWriter를 만드는 부분! go()메소드에서 애플리케이션 GUI를 화면에 표시하기 직전에 이 메소드 호출 !
			
			System.out.println("networking established");
			
		} catch(IOException ex){
			ex.printStackTrace();
		}
	}//setUpNetWorking 
	
	public class SendButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			// 텍스트 필드로부터 텍스트를 알아낸 다음
			//writer(PrintWriter 객체)를 써서 서버로 보낸다.
			try{
				writer.println(outgoing.getText());
				writer.flush();
				
			} catch(Exception ex){
				ex.printStackTrace();
			}
			outgoing.setText("");
			outgoing.requestFocus();
		}
	} //SendButtonListener 내부 클래스 
	
	public static void main(String[] args){
		new SimpleChatCleintA().go();
	}
} //외부 클래스 
