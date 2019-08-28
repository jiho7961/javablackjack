package exam.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class servergame {
	
	public static void main(String[] args) {
		// ���� ���ӵǾ� �ִ� Ŭ���̾�Ʈ ����
		ArrayList<ServerChatter> chatters = new ArrayList<ServerChatter>();
		
		// �������� ��ü ����
		ServerSocket serverSocket = null;
		Socket socket = null;

		// ���ӵ� ���� ��ȣ
		int no = 0;
		ServerChatter chatter = null;
		try{
			// �������� ����
			serverSocket = new ServerSocket(9002);
			while(true){
				System.out.println("***********Ŭ���̾�Ʈ ���� �����*************");
				socket = serverSocket.accept();
				
				// ä�� ��ü ����
				chatter = new ServerChatter(socket, chatters, no );
				chatter.login();  // ��ȭ�� �Է� ó��
				
				// ä�� ��ü�� ArrayList�� �����Ѵ�.
				chatters.add(chatter);
				no++;
				
				// ���ӵ� ������ ���� 1��1 ä���� ��Ű�� ����
				if( no % 2 == 0 ){
					chatters.get(no-2).start();
					chatters.get(no-1).start();
				}
			}
		}catch(IOException e){
			System.out.println(e.getMessage());
		}finally{
			try{
				serverSocket.close();
			}catch(IOException e){				
			}
		}
	}
}

// ������ �̿��Ͽ� Ŭ���̾�Ʈ 1���� ���� ����Ǿ� �ְ�
// ArrayList<> �� chatters�� �ҼӵǾ��ִ� �Ǵٸ� ���ϰ� ����Ÿ�� �ְ�޴� ������ Ŭ����
class ServerChatter extends Thread{
	// Ŭ���̾�Ʈ�� ���� ����Ǿ� �ִ� ����
	Socket socket;	
	BufferedReader br;	// �������κ����� ���� �Է� ��Ʈ��
	PrintWriter pw;		// �������κ����� ���� ��� ��Ʈ��
	
	// ���� ������ ���ӵ� ��ü Ŭ���̾�Ʈ ������ ����Ǿ� �ִ�.
	// �̵��� 1���� Ŭ���̾�Ʈ�� ä���� �Ѵ�(1��1ä��)
	ArrayList<ServerChatter> chatters;

	int no;		// ���ӵ� ���� --> ���� 1�� 1 ä�� ����� ���ϱ� ���� �ڽ��� ���� ����
	String id;	// ���̵�(��Ī)--> ��ȭ�޼����� ������ id(��ȭ��) ==> �α���ó���� ���� ����
	
	public ServerChatter(Socket socket, ArrayList<ServerChatter> chatters, 
			int no){
		this.socket = socket;
		this.chatters = chatters;
		this.no = no;
		
		// �������κ��� ���� ����� ��Ʈ�� ���
		try{
			br = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream());
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	// ��ȭ���� �Է¹޴� ó�� --> Ȯ��Ǿ����� ����Ÿ���̽��� id/pass�� �˻��Ͽ�
	//							  �α��� ������� Ȯ���� �� �ִ�.
	public void login(){
		try{
			id = br.readLine();
		}catch(IOException e){
			System.out.println(e.getMessage());
			System.out.println("login()ó������ ���� �߻�.....");
		}
	}
	public void run(){
		// ����ڰ� ä���� ����ϴ��� �ڱ��ڽ�, ����� ¦���� ���� �޼����� �����ָ� �ȴ�.
		//						  0 ¦���̸� 1��ŭ ū ���  -----> 1
		//                        1 Ȧ���̸� 1��ŭ ���� ��� -----> 0
		int pairNo = ( no % 2 == 0) ? no + 1 : no - 1;
		// ���� Ŭ���̾�Ʈ�� 1��1 ä���� Ŭ���̾�Ʈ ���ϱ�
		ServerChatter pair = chatters.get(pairNo);
		
		// �ΰ��� Ŭ���̾�Ʈ�� ���ÿ� ä���� ������ �� �ֵ��� �ϱ����ؼ�
		this.sendMessage("start");
		
		try{
			String message = "";String order="";String eorder="";
			System.out.println(id +" Ŭ���̾�Ʈ�� �޼����� ��ٸ��ϴ�.");
			while(!message.equals("bye")){
            	//message = "";
            	
            	message = br.readLine();
            	if(message!=null) {
            	System.out.println("���� �޼��� ==>" + id + ":" + message);
				//����� ������ ©��
            	if(message.equals("sendtoname")) {order="";eorder="@"+id;}
            	if(message.equals("a")) {order="a";eorder="ea";}
            	if(message.equals("d")) {order="d";eorder="ed";}
            	if(message.equals("shot")) {order="shot";eorder="eshot";}
            	if(message.equals("win")) {order="";eorder="losed";}
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
				// �ڽŰ� ���� ����� Ŭ���̾�Ʈ���� �޼����� �ٽ� �����Ѵ�.
            	//this.sendMessage(id + ": " + message);
            	this.sendMessage(order);
				// 1��1ä���� �ϵ��� ����� Ŭ���̾�Ʈ���� �޼����� �����Ѵ�.
            	pair.sendMessage(eorder);
            	System.out.println(id +" Ŭ���̾�Ʈ�� �޼����� ��ٸ��ϴ�.");
            	}
            	else {message="";}
            }
		}catch(IOException e){
			System.out.println(e.getMessage());
			System.out.println("�޼����� �����Ͽ� �۽��� ���� �߻�....");
		}finally{
			close();
			System.out.println("������ �ݰ� ������ ����....");
		}				
	}
	
	void sendMessage(String message){
		try{
			pw.println(message);
			pw.flush();
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.out.println("sendMessage()���� ���� �߻�....");
		}
	}
	
	public void close(){
		try{
			br.close();
			pw.close();
			socket.close();
		}catch(Exception e){
			System.out.println("close()..���� ���� �߻�!");
		}
	}
}