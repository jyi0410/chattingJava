package chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class VerySimpleChatServer {

	ArrayList clientOutputStreams;
	
	public class ClientHandler implements Runnable{
		BufferedReader reader;
		Socket sock;
		
		public ClientHandler(Socket clientSocket){
			try{
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
				
			} catch(Exception ex){
				ex.printStackTrace();
			}
		} //생성자 끝
		
		public void run(){
			String message;
			try{
				while((message = reader.readLine()) != null){
					System.out.println("read" + message);
					tellEveryone(message);
				} //while 순환문 
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}//run 메소드 
	} //내부 클래스 끝 
	
	public static void main(String[] args){
		new VerySimpleChatServer().go();
	}
	
	public void go(){
		clientOutputStreams = new ArrayList();
		System.out.println("start");
		try{
			ServerSocket serverSock = new ServerSocket(4242);
			System.out.println("start1");	
			while(true){
				System.out.println("start2");
				Socket clientSocket = serverSock.accept();
				System.out.println("start!!!");
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);
				
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
				System.out.println("got a connection");
			}
		} catch(Exception ex){
			System.out.println("start3");
			ex.printStackTrace();
		}
	} //go 메소드 끝 
	
	public void tellEveryone(String message){
		
		Iterator it = clientOutputStreams.iterator();
		while(it.hasNext()){
			try{
				PrintWriter writer = (PrintWriter) it.next();
				writer.println(message);
				writer.flush();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		} //while 순환문 
	}//tellEveryone 메소드 
} //클래스 끝 
