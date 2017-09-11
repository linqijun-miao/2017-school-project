package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import server.MyMessage;
import server.User;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;
import java.awt.Toolkit;

public class ChatUI extends JFrame {

	private JPanel contentPane;
	private User u;
	private User friend;
	private Socket so;
	private JTextArea chatPane;
	
	public ChatUI(User u,User friend,Socket so) {
		setResizable(false);
		this.u = u;
		this.friend = friend;
		this.so = so;
		
		Toolkit tl = Toolkit.getDefaultToolkit();
		Dimension screenSize = tl.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		
		String top = "这是你和" + friend.getName() +"     (   " + friend.getID() + "   ) 的聊天";
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				MainUI.removeC(friend);
			}
		});
		setBounds(100, 100, 602, 508);
		this.setLocation(screenWidth / 4 + 200, screenHeight / 4);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel(top);
		lblNewLabel.setFont(new Font("微软雅黑 Light", Font.PLAIN, 18));
		lblNewLabel.setBounds(14, 13, 556, 39);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u8F93\u5165");
		lblNewLabel_1.setFont(new Font("微软雅黑 Light", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(25, 318, 72, 18);
		contentPane.add(lblNewLabel_1);
		
		chatPane = new JTextArea();
		chatPane.setFont(new Font("微软雅黑 Light", Font.PLAIN, 18));
		chatPane.setEditable(false);
		chatPane.setLineWrap(true);
		chatPane.setWrapStyleWord(true);
		
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("微软雅黑 Light", Font.PLAIN, 18));
		textArea.setBounds(14, 341, 458, 107);
		contentPane.add(textArea);
		textArea.setLineWrap(true); 
		textArea.setWrapStyleWord(true);
		
		ButtonListener bl = new ButtonListener(u,friend,so,textArea,chatPane);
		
		JButton btnNewButton = new JButton("\u53D1\u9001");
		btnNewButton.setFont(new Font("微软雅黑 Light", Font.PLAIN, 18));
		btnNewButton.setBounds(490, 360, 80, 59);
		btnNewButton.setUI(new friendButtonUI());
		btnNewButton.setActionCommand("发送");
		btnNewButton.addActionListener(bl);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane(chatPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(14, 48, 556, 268);
		contentPane.add(scrollPane);
		String a;
		do{
			a = ReceiveThread.tempMessage(friend.getID());
			if(a != null){
				showUp(a);
			}
		}while(a == "");
	}
	
	public double getFriendID(){
		return friend.getID();
	}
	
	public void showUp(String a){
		chatPane.append("      " + friend.getName() + "  ( " + friend.getID()+ " ) :" +  a + "\r\n");
	}
	
	private class ButtonListener implements ActionListener{
		private User u;
		private User friend;
		private Socket so; 
		private JTextArea textArea;
		private JTextArea chatPane;
		public ButtonListener(User u,User friend,Socket so,JTextArea textArea,JTextArea chatPane){
			this.u = u;
			this.friend = friend;
			this.so = so;
			this.textArea = textArea;
			this.chatPane = chatPane;
			
		}
		@Override
		//用TextField的 append 方法,每输出一行加个\r\n例如:String output="第一行" TextField1.append(output+"\r\n"); 
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("发送")){
				String send = textArea.getText();
				MyMessage wsend = new MyMessage(u);
				wsend.setType(3);
				wsend.setFriend(friend.getID());
				wsend.wirteString(send);
				
				chatPane.append("      我：   " +  send + "\r\n");
				wsend.send(so);
				textArea.setText("");
			}
		}
		
	}
	
}
