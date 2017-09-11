package server;

import java.net.ServerSocket;
import java.net.Socket;


public class Qserver {
	public static void main(String[] args){
		//网络服务
		ServerUI frame = new ServerUI();
		frame.setVisible(true);
		try{
			ServerSocket s1 = new ServerSocket(6870);
			while(true){
				ServerUI.printINF("等待加入");
				Socket s = s1.accept();
				ServerUI.printINF("尝试加入");
				new Thread(new ServerHelper(s)).start();//每一个连接启动一个线程。
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}