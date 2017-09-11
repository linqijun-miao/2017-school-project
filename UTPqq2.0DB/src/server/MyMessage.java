package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class MyMessage implements Serializable{
	private int type;// 0Ϊע��    �� 1Ϊ��½ �� 2Ϊ�˳�   �� 3 Ϊ���� , 4ע���˳� , 5�Ӻ��� , 6 ����ͷ��
	
	
	private double friendId;
	private String s;
	private User u = new User(0,"0");
	
	
	public MyMessage(User u){
		this.u = u;
		
	}
	
	public MyMessage() {
		// TODO Auto-generated constructor stub
	}

	public void wirteString(String temp){
		s = temp;
	}
	
	public void setType(int a ){
		type = a;
	}
	
	public static MyMessage receive(Socket s) throws ClassNotFoundException {
		try {
			 DataInputStream ds = new DataInputStream(s.getInputStream());
			 ObjectInputStream oi = new ObjectInputStream(ds);
			MyMessage temp = (MyMessage)oi.readObject();
			
			
			return temp;
			
		} catch(EOFException a){
			
			return null;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public void send(Socket so){
		try {
			DataOutputStream dop = new DataOutputStream(so.getOutputStream());
			ObjectOutputStream oops = new ObjectOutputStream(dop);
			oops.writeObject(this);
			oops.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getType(){
		return type;
	}
	
	public String getString(){
		return s;
	}
	
	public User getUser(){
		return u;
	}
	
	public double getFriend(){
		return friendId;
		
	}
	
	public void setFriend(double id){
		friendId = id;
	}
	
}
