package server;

import java.net.ServerSocket;
import java.net.Socket;


public class Qserver {
	public static void main(String[] args){
		//�������
		ServerUI frame = new ServerUI();
		frame.setVisible(true);
		try{
			ServerSocket s1 = new ServerSocket(6870);
			while(true){
				ServerUI.printINF("�ȴ�����");
				Socket s = s1.accept();
				ServerUI.printINF("���Լ���");
				new Thread(new ServerHelper(s)).start();//ÿһ����������һ���̡߳�
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}