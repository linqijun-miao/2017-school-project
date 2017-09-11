package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerHelper implements Runnable{
	
	
	private static ArrayList<User> userList = new ArrayList<User>();
	private static ArrayList<User> onlineUserList = new ArrayList<User>();
	private static ArrayList<Socket> onlineso = new ArrayList<Socket>();
	private static ArrayList<MyMessage> tempMessage = new ArrayList<MyMessage>();
	private static int size;
	private Socket so  ;
	private int count = 1;
	private User threadOwner;
	private User addf;
	private boolean ifrun = true;
	
	Connection conn ;
	Statement myStmt;
	
	public ServerHelper(Socket so){
		
		
	//***********
		String driver ="com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/miniqq?characterEncoding=utf8&useSSL=true";
		String dbuser = "root";
		String dbpassword = "root";
		
		
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,dbuser,dbpassword);
			myStmt = conn.createStatement();//sql语句的预处理对象
			ResultSet result = myStmt.executeQuery("select count(*) totalCount from user");
			if(result.next())
				size = result.getInt("totalCount");//计算总用户数
			
			
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	//************	
		
		
		this.so = so;
	}
	
	public void run(){
		while(ifrun){
			
			try {
				MyMessage mgs = MyMessage.receive(so);
				if(mgs == null){
					continue;
				}
				ServerUI.printINF("收到"+mgs.getUser().getID()+"发出的type为" + mgs.getType()+" 的信息");
				if(mgs.getType() == 0){//注册
					boolean temp = register(mgs.getUser());
					if(temp){
						MyMessage m = new MyMessage();
						
						m.wirteString("success");
						ServerUI.printINF("注册成功");
						DataOutputStream dos = new DataOutputStream(so.getOutputStream());
						ObjectOutputStream oops = new ObjectOutputStream(dos);
						oops.writeObject(m);
						oops.flush();
						//oops.close();
						
					}else{
						
						MyMessage m = new MyMessage();
						m.wirteString("fail");
						System.out.println("fail");
						DataOutputStream dos = new DataOutputStream(so.getOutputStream());
						ObjectOutputStream oops = new ObjectOutputStream(dos);
						oops.writeObject(m);
						oops.flush();//清空缓存
						//oops.close();
					}
					
				}
				if(mgs.getType() == 1){//登陆
					login(mgs.getUser());
					
				}
				
				if(mgs.getType() == 4){//注册退出
					so.close();
					ifrun = false;
					
				}
				
				if(mgs.getType() == 2){//用户退出
					User temp = mgs.getUser();
					for(int i = 0 ; i < onlineUserList.size() ; i++){
						if(temp.getID() == onlineUserList.get(i).getID()){
							onlineUserList.remove(i);
							onlineso.remove(i);
							//user data更新
							ServerUI.printINF("ID" + temp.getID() + " 退出");
							so.close();
							ifrun = false;
						}
					}
				}
				if(mgs.getType() == 5){ //加好友
			
					boolean whetherFind = false;
					
					if(mgs.getString().equals("ok")){//同意添加
						double fid = mgs.getFriend();
						PreparedStatement ps;
						try {
							ps = conn.prepareStatement("INSERT INTO friendrelationship VALUES( ?, ?)");
							ps.setDouble(1, threadOwner.getID());
							ps.setDouble(2, fid);
							ps.executeUpdate();
							ps = conn.prepareStatement("INSERT INTO friendrelationship VALUES( ?, ?)");
							ps.setDouble(1, fid);
							ps.setDouble(2, threadOwner.getID());
							ps.executeUpdate();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						ServerUI.printINF("ID" +threadOwner.getID() + " ID " + fid + " 互加好友");
								
						break;	
						}
						
					
					
					System.out.println(count);
					if(count == 1){
						String sid = mgs.getString();
						double id = Double.parseDouble(sid);
						ResultSet result;
						try {
							result = myStmt.executeQuery("select id , name from user where id = "+id);
							while(result.next()){	
							String name = result.getString(2);
							User temp = new User(id,name);
							MyMessage ask = new MyMessage(temp);
							ask.setType(5); //防止客户端同时接受多个message出现混乱
							ask.wirteString("ask");
							ask.send(so);
							whetherFind = true;
							addf = temp;
							count++;
							System.out.println("haoyou" );
							break;
						}	
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						
								
								
					
							if(!whetherFind){
								MyMessage sorry = new MyMessage(mgs.getUser());
								sorry.setType(5);
								sorry.wirteString("not found");
								sorry.send(so);
							}
							continue;
					}
					if(count == 2){
						System.out.println("好友请求发送11111");
						if(mgs.getString().equals("yes")){
							
									MyMessage ask = new MyMessage(mgs.getUser());
									ask.setType(5);
									ask.setFriend(addf.getID());
									ask.wirteString("wAdd");
									transMessage(ask);
									
									MyMessage success = new MyMessage(mgs.getUser());
									success.setType(5);
									success.wirteString("success");
									success.send(so);
								System.out.println("好友请求发送");
								addf = null;
						}
						count = 1;
					}
					
					
				}
				
				if(mgs.getType() == 3){ //聊天
					transMessage(mgs);
				}
				
				if(mgs.getType() == 6){ //更改头像
					PreparedStatement ps = conn.prepareStatement("update user set phototype = ? where id = ?");
					int photo = mgs.getUser().getPhotoType();
					ps.setInt(1, photo);
					ps.setDouble(2, mgs.getUser().getID());
					ps.executeUpdate();
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			killSelf();
		}
	}
	
	private boolean register(User u) {
		double id = u.getID();
		try {
			
			ResultSet result ;
			result = myStmt.executeQuery("select count(*) from user where id =" + id);
			while(result.next()){
				if(result.getInt(1) > 0){
					ServerUI.printINF("注册 id 重复");
					return false;
				}
			}
		
			
			String  password = u.getPassWord();
			String name = u.getName();
			
			PreparedStatement ps = conn.prepareStatement("INSERT INTO user VALUES( ?, ? , ?,?,?)");
			ps.setDouble(1, id);
			ps.setString(2, password);
			ps.setString(3, name);
			ps.setInt(4, 1);
			ps.setInt(5, 0);
			ps.executeUpdate();
			result = myStmt.executeQuery("select count(*) totalCount from user");
			if(result.next())
				size = result.getInt("totalCount");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ServerUI.printINF(e.toString());
			return false;
		}
	}
	
	private boolean login(User u){//需要修改防止出现二次登陆
		
		
		//*************************
		double id = u.getID();
		String password = u.getPassWord();
		try {
			ResultSet result = myStmt.executeQuery("select id , password from user where id = "+id);
			if(result.next()){
				if(result.getString(2).equals(password)){
					result = myStmt.executeQuery("select id , name , phototype from user where id = "+id);
					User temp1 = null;
					while(result.next()){
						temp1 = new User(id,result.getString(2));
						temp1.setPhoto(result.getInt(3));
					}
					for(int i = 0 ; i < onlineUserList.size(); i++){
						if(onlineUserList.get(i).getID() == id){
							onlineUserList.remove(i);
							onlineso.remove(i);
							continue;
						}
					}
					onlineUserList.add(temp1);
					onlineso.add(so);//记录下端口
					threadOwner = temp1;
					addAllFriend(temp1.getID());
					MyMessage m = new MyMessage(temp1);
					m.wirteString("success");
					m.send(so);
					ServerUI.printINF("ID " + temp1.getID() +" 登陆");
					
					//将不在线时的消息发送
					for(int q = 0 ; q < tempMessage.size() ; q++){
						double id2 = tempMessage.get(q).getFriend();
						if(id == id2){
							transMessage(tempMessage.get(q));
							tempMessage.remove(q);
						}
					}
					ServerUI.printINF("ID " + temp1.getID() +" 已将未发送信息发送");
					return true;//成功
				}else{
					MyMessage m = new MyMessage();
					m.wirteString("fail , password error");
					m.send(so);
				}
			}else{
				MyMessage m = new MyMessage();//未找到
				m.wirteString("fail , id not found");
				m.send(so);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		return false;
	}	
		
		//*****************************
		
		
		
	private boolean transMessage(MyMessage m){
		if(isOnline(m.getFriend())){
			Socket to ;
			for(int i = 0 ; i < onlineUserList.size(); i++){
				User temp = onlineUserList.get(i);
				if(temp.getID() == m.getFriend()){
					to = onlineso.get(i);
					MyMessage a = new MyMessage();
					a.wirteString(m.getString());
					a.setType(m.getType());
					a.setFriend(m.getUser().getID());
					a.send(to);
					return true;
				}
			}
		}else{
			tempMessage.add(m);
		}
		return false;
		
	}
	private boolean isOnline(double u){
		
		
		for(int i = 0 ; i < onlineUserList.size(); i++){
			User temp = onlineUserList.get(i);
			if(temp.getID() == u){
				return true;
			}
		}
		return false;
	}
	
	public static int getOnline(){
		return onlineUserList.size();
	}
	
	public static int getUserNUM(){
		return size;
	}
	
	public ArrayList<User> returnFList(ArrayList<Double> friendID){
		 ArrayList<User> FList = new ArrayList<User>();
		for(int i = 0 ; i < friendID.size() ; i++){
			 for(int k = 0 ; k < userList.size() ; k++){
				 if(friendID.get(i) == userList.get(k).getID() )
					 FList.add(userList.get(k));
			 }
		}
		return FList;
	}
	
	public void addAllFriend(double id){//返回所有的好友
		try {
			ResultSet result = myStmt.executeQuery("select Aid , Bid from friendrelationship where Aid =" +id);
			ArrayList<Double> idl = new ArrayList<Double>();
			while(result.next()){
			
				double fid = result.getDouble(2);
				idl.add(fid);
				
			}
			
			if(idl != null){
				for(int i = 0; i < idl.size() ; i++){
					ResultSet result2 = myStmt.executeQuery("select id , name , phototype from user where id = "+idl.get(i));
					while(result2.next()){
						String name = result2.getString(2);
						int type = result2.getInt(3);
						User temp = new User(idl.get(i),name);
						temp.setPhoto(type);
						threadOwner.addFriend(temp);
					}
				}	
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void killSelf(){
		for(int i = 0 ; i < onlineso.size() ; i++){
			if(so == onlineso.get(i)){
				return;
			}
		}
		ifrun = false;
		
	}
}

