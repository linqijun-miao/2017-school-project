package client;

import java.io.EOFException;
import java.net.Socket;
import java.util.ArrayList;

import server.MyMessage;
import server.User;

public class ReceiveThread implements Runnable{
	private AddFriend af;
	private MainUI mu;
	private Socket so;
	private User u;
	private static ArrayList<MyMessage> temp = new ArrayList<MyMessage>();
	public ReceiveThread(MainUI mu,AddFriend af ,Socket so){
		this.mu = mu;
		this.af = af;
		this.so = so;
		u = mu.getUser();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				MyMessage mgs = MyMessage.receive(so);
				if(mgs == null){
					continue;
				}
				if(mgs.getType() == 5){
					if(mgs.getString().equals("ask")){
						//在addfriend中显示希望加的人的信息
						User temp = mgs.getUser();
						String a = "ID:      "+temp.getID() +"       name:      " + temp.getName();
						ConfirmUI frame = new ConfirmUI(a,so,u);
						frame.setVisible(true);
					}
					if(mgs.getString().equals("not found")){
						//在addfriend中显示用户未找到
						af.show("未找到匹配用户");
					}
					if(mgs.getString().equals("success")){
						//添加请求发送成功
						af.show("请求发送成功，请等待对方回复");
					}
					if(mgs.getString().equals("wAdd")){
						Double tid = mgs.getFriend(); 
						WantYouUI wyui = new WantYouUI(tid,u,so);
						wyui.setVisible(true);
					}
				}
				
				if(mgs.getType() == 3){
					String beSent = mgs.getString();
					double fid = mgs.getFriend();
					if(mu.ischat(fid)){
						mu.chat(fid, beSent);
					}else{
						mu.getFriendButton(fid).haveNewm();
						temp.add(mgs);
					}
				}
			
			
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
	}
	public static String tempMessage(double id){
		for(int i = 0 ; i < temp.size() ; i++){
			if(temp.get(i).getFriend() == id ){
				MyMessage a = temp.get(i);
				temp.remove(i);
				return a.getString();
			}
		}
		return null;
	}
	
}
