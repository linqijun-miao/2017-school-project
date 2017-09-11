package server;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
	private double id;
	private String name;
	private ArrayList<User> friendList = new ArrayList<User>();
	private String password;
	private int photoType = 0 ;
	private boolean sex;
	
	
	
	public User(double id,String name){
		this.id = id;
		this.name = name;
	}
	
	
	public void setPhoto(int a){
		this.photoType = a;
	}
	public int getPhotoType(){
		return photoType;
	}
	public void setSex(boolean temp){
		sex = temp;
	}
	
	public boolean getSex(){
		return sex;
	}
	
	public double getID(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public void addFriend(User u){
		friendList.add(u);
		
	}
	
	public ArrayList<User> getFriends(){
		return friendList;
	}
	
	public String getPassWord(){
		return password;
	}
	
	public void setPassWord(String password){
		this.password = password;
	} 
}
