package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import server.MyMessage;
import server.User;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class WantYouUI extends JFrame {

	private JPanel contentPane;
	private double tid ;
	private User u;
	private Socket so;
	
	public WantYouUI(double tid,User u,Socket so) {
		this.tid = tid;
		this.u = u;
		this.so = so;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Toolkit tl = Toolkit.getDefaultToolkit();
		Dimension screenSize = tl.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		this.setLocation(screenWidth / 4 + 200, screenHeight / 4);
		
		
		String temp = "ID:   " + tid + "   想加你为好友";
		JLabel lblNewLabel =  new JLabel(temp);
		lblNewLabel.setFont(new Font("微软雅黑 Light", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(89, 76, 271, 48);
		contentPane.add(lblNewLabel);
		ButtonListener bl = new ButtonListener(this,tid,u,so);
		
		JButton btnNewButton = new JButton("\u597D\u54D2");
		btnNewButton.setFont(new Font("微软雅黑 Light", Font.PLAIN, 16));
		btnNewButton.setBounds(49, 175, 113, 36);
		contentPane.add(btnNewButton);
		btnNewButton.setActionCommand("确认");
		btnNewButton.addActionListener(bl);
		
		JButton btnNewButton_1 = new JButton("\u4E11\u62D2");
		btnNewButton_1.setFont(new Font("微软雅黑 Light", Font.PLAIN, 15));
		btnNewButton_1.setBounds(275, 175, 113, 36);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.setActionCommand("取消");
		btnNewButton_1.addActionListener(bl);
		
	}
	private class ButtonListener implements ActionListener{
		WantYouUI ui;
		double tid;
		User u;
		Socket so;
		
		public ButtonListener(WantYouUI ui,double tid,User u,Socket so){
			this.ui = ui;
			this.tid = tid;
			this.u = u;
			this.so = so;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("确认")){
				MyMessage ok = new MyMessage(u);
				ok.wirteString("ok");
				ok.setType(5);
				ok.setFriend(tid);
				ok.send(so);
				
				ui.dispose();
			}
			if(e.getActionCommand().equals("取消")){
				ui.dispose();
			}
		}
		
	}
}
