package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import server.MyMessage;
import server.User;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class MainUI extends JFrame {

	private JPanel contentPane;
	private Socket so;
	private User u;
	private ArrayList<User> friendList;
	private ArrayList<friendButton> friendB = new ArrayList<friendButton>();
	private static ArrayList<ChatUI> chatlist = new ArrayList<ChatUI>();
	JButton photoB;
	AddFriend frame;
	
	public MainUI(Socket so , User u, AddFriend frame) {
		this.frame = frame;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("image\\mars.jpg"));
		this.so = so;
		this.u = u;
		 
		frame.setVisible(false);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				MyMessage exitmsg = new MyMessage(u);
				exitmsg.setType(2);
				exitmsg.send(so);
				System.exit(0);
			}
		});
		
		setBounds(100, 100, 328, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 118, 322, 576);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
		contentPane.add(scrollPane);
		
		Toolkit tl = Toolkit.getDefaultToolkit();
		Dimension screenSize = tl.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		this.setLocation(screenWidth/2, screenHeight/4 - 50 );
		
		ButtonListener bl = new ButtonListener(this);
		friendList = u.getFriends();
		
		friendButton b ;
		for(int i = 0; i < friendList.size() ; i++){
			double id = friendList.get(i).getID();
			String ids = ((Double)id).toString();
			String name = friendList.get(i).getName();
			b = new friendButton(" " + name +"  (   " +ids+"   )",id );
			b.setFont(new Font("微软雅黑 Light", Font.BOLD, 14));
			ImageIcon icon1 = geticon(friendList.get(i).getPhotoType());
			icon1.setImage(icon1.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));//好友头像
			b.setIcon(icon1);
			b.setActionCommand(ids);
			b.setUI(new friendButtonUI() );
			b.addActionListener(bl);
			friendB.add(b);
		}
		JPanel jp = new JPanel();
		if(friendB == null){
			jp.setSize(new Dimension(321,1*60 + 80));
		}else{
			jp.setSize(new Dimension(321,friendB.size()*60 + 80));
		}
		
		jp.setLayout(null);
		scrollPane.add(jp);
		
		JButton addf = new JButton("+");
		addf.setFont(new Font("微软雅黑", Font.BOLD, 20));
		addf.setUI(new NormalButtonUI());
		addf.setActionCommand("add");
		addf.setBounds(0, 696, 41, 37);
		addf.addActionListener(bl);
		contentPane.add(addf);
		
		JLabel lblNewLabel = new JLabel("<html>" + u.getName() + "<br>" + "( " + u.getID() + " ) </html>");
		lblNewLabel.setFont(new Font("等线 Light", Font.PLAIN, 15));
		lblNewLabel.setBounds(129, 23, 193, 51);
		contentPane.add(lblNewLabel);
		
		photoB = new JButton("");
		photoB.setUI(new friendButtonUI());
		photoB.setIcon(geticon(u.getPhotoType()));
		photoB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChangePhotoUI frame = new ChangePhotoUI(so,u,MainUI.this);
				frame.setUndecorated(true);
				frame.setLocation(photoB.getLocationOnScreen());
				frame.setVisible(true);
			}
		});
		
		
		photoB.setBounds(8, 3, 81, 81);
		contentPane.add(photoB);
		System.out.println("friendb " + friendB.size());
		for(int i = 0; i < friendB.size() ; i++){
			friendB.get(i).setBounds(1,2+60 * i,290,60);
			jp.add(friendB.get(i));
			friendB.get(i).setVisible(true);
		}
		jp.setVisible(true);
	}
	public User getUser(){
		return u;
	}
	
	public friendButton getFriendButton(double id){
		for(int i = 0 ; i < friendB.size() ; i++){
			if(friendB.get(i).getFriendID() == id){
				return friendB.get(i);
			}
			
		}
		return null;
	}
	
	public void setBPhoto(int a){
		photoB.setIcon(geticon(a));
	}
	
	public static void removeC(User u){
		for(int q = 0; q < chatlist.size();q++){
			if(chatlist.get(q).getFriendID() == u.getID()){
				chatlist.remove(q);
			}
			
		}
	}
	
	public ImageIcon geticon(int a){//返回用户头像
		ImageIcon icon = null;
		switch (a){
		case 0:
			 icon = new ImageIcon("photo\\0.png");
			 break;
		case 1:
			 icon = new ImageIcon("photo\\1.png");
			 break;
		case 2:
			 icon = new ImageIcon("photo\\2.png");
			 break;
		case 3:
			 icon = new ImageIcon("photo\\3.png");
			 break;
		case 4:
			 icon = new ImageIcon("photo\\4.png");
			 break;	
		case 5:
			 icon = new ImageIcon("photo\\5.png");
			 break;
		case 6:
			 icon = new ImageIcon("photo\\6.png");
			 break;
		case 7:
			 icon = new ImageIcon("photo\\7.png");
			 break;
		case 8:
			 icon = new ImageIcon("photo\\8.png");
			 break;
		case 9:
			 icon = new ImageIcon("photo\\9.png");
			 break;
		case 10:
			 icon = new ImageIcon("photo\\10.png");
			 break;
		case 11:
			 icon = new ImageIcon("photo\\11.png");
			 break;
		}
		icon.setImage(icon.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
		return icon;
	}
	
	/*public User findUser(double id){
		for(int i = 0 ; i < friendList.size(); i++){
			if(friendList.get(i).getID() == id){
				return friendList.get(i);
			}
			
		}
		return null;
	}*/
	
	public boolean ischat(double id) {
		for(int i = 0 ; i < chatlist.size() ; i++){
			if(id == chatlist.get(i).getFriendID()){
				return true;
			}
		}
		return false;
	}
	
	public void chat (double id, String a ){
		for(int i = 0 ; i < chatlist.size() ; i++){
			if(id == chatlist.get(i).getFriendID()){
				chatlist.get(i).showUp(a);
			}
		}
		
	}
	
	private class ButtonListener implements ActionListener{
		MainUI ui;
		public ButtonListener(MainUI ui){
			this.ui = ui;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("add")){
				frame.setVisible(true);
				return;
			}
			
			for(int i = 0 ; i < friendList.size() ; i++){
				String temp = ((Double)friendList.get(i).getID()).toString();
				if(e.getActionCommand().equals(temp)){
					//启动聊天
					for(int q = 0; q < chatlist.size();q++){
						if(temp.equals(((Double)chatlist.get(q).getFriendID()).toString())){
							return;
						}
					}
					ChatUI cui = new ChatUI(ui.getUser(),friendList.get(i),so);
					chatlist.add(cui);
					cui.setVisible(true);
					ui.getFriendButton(friendList.get(i).getID()).haveno();
					
				}
			}
			
			
		}
		
	}
}
