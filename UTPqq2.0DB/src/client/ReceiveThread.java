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
						//��addfriend����ʾϣ���ӵ��˵���Ϣ
						User temp = mgs.getUser();
						String a = "ID:      "+temp.getID() +"       name:      " + temp.getName();
						ConfirmUI frame = new ConfirmUI(a,so,u);
						frame.setVisible(true);
					}
					if(mgs.getString().equals("not found")){
						//��addfriend����ʾ�û�δ�ҵ�
						af.show("δ�ҵ�ƥ���û�");
					}
					if(mgs.getString().equals("success")){
						//��������ͳɹ�
						af.show("�����ͳɹ�����ȴ��Է��ظ�");
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
