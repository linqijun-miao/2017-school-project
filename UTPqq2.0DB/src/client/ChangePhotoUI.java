package client;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import server.MyMessage;
import server.User;

import javax.swing.JScrollPane;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;

public class ChangePhotoUI extends JFrame {
	
	
	private ArrayList<ImageIcon> iconlist = new ArrayList<ImageIcon>();
	private ArrayList<JButton> buttonList = new ArrayList<JButton>();
	private int chosen = 0;
	private Socket so;
	private User u;
	private MainUI ui;
	CButton temp = null;
	public ChangePhotoUI(Socket so,User u,MainUI ui) {
		this.so = so;
		this.u = u;
		this.ui = ui;
		
		iconlist.add(changeSize(new ImageIcon("photo\\0.png")));
		iconlist.add(changeSize(new ImageIcon("photo\\1.png")));
		iconlist.add(changeSize(new ImageIcon("photo\\2.png")));
		iconlist.add(changeSize(new ImageIcon("photo\\3.png")));
		iconlist.add(changeSize(new ImageIcon("photo\\4.png")));
		iconlist.add(changeSize(new ImageIcon("photo\\5.png")));
		iconlist.add(changeSize(new ImageIcon("photo\\6.png")));
		iconlist.add(changeSize(new ImageIcon("photo\\7.png")));
		iconlist.add(changeSize(new ImageIcon("photo\\8.png")));
		iconlist.add(changeSize(new ImageIcon("photo\\9.png")));
		iconlist.add(changeSize(new ImageIcon("photo\\10.png")));
		iconlist.add(changeSize(new ImageIcon("photo\\11.png")));
		this.getContentPane();
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(27, 13, 430, 288);
		scrollPane.setLayout(null);
		
		for(int i = 0 ; i < iconlist.size() ; i++){//设置图片选择按钮
			CButton tempb = new CButton();
			tempb.setIcon(iconlist.get(i));//7
			tempb.photoType = i;
			tempb.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					chosen = tempb.photoType;
					if(temp != null){
						temp.setChoose();
						temp = tempb;
					}else{
						temp = tempb;
					}
					tempb.setChoose();
					
				}
				
			});
			
			buttonList.add(tempb);
		}
			int count = 0;
			int height = 0;
			
		for(int i = 0 ; i < buttonList.size() ; i++){
			
			scrollPane.add(buttonList.get(i));
			if(count == 7){
				height += 65;
				count = 0;
			}
			buttonList.get(i).setBounds(1+count*60, height, 60, 60);
			count++;
		}
		
		getContentPane().add(scrollPane);
		
		JButton confirm = new JButton("confirm");
		confirm.setBounds(55, 314, 122, 36);
		getContentPane().add(confirm);
		
		confirm.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				u.setPhoto(chosen);
				MyMessage changeP = new MyMessage(u);
				changeP.setType(6);
				changeP.send(so);
				ui.setBPhoto(chosen);
				dispose();
				
			}
			
		});
		
		
		
		JButton back = new JButton("back");
		back.setBounds(305, 314, 122, 32);
		getContentPane().add(back);
		back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
			
		});
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 497, 398);
		
		new Thread(new freshP(this)).start();
	}
	
	public ImageIcon changeSize(ImageIcon a){
		a.setImage(a.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		return a ;
	}
	
	private class CButton extends JButton{
		boolean beChosen = false;
		int photoType;
		public void paintComponent(Graphics g){
			if(!beChosen){
				super.paintComponent(g);
			}else{
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)g;
				Color c = g2.getColor();
				g2.setStroke(new BasicStroke(5.0f));
				g2.setColor(Color.GREEN);//打勾
				g2.drawLine(5, 5, 15, 20);
				g2.drawLine(15, 20, 35, 5);
				
				g2.setColor(c);
				
				}
		}
		
		public void setChoose(){
			if(beChosen){
				beChosen = false;
			}else{
				beChosen = true;
			}
		}
	}
	
	private class freshP implements Runnable{
		ChangePhotoUI ui;
		freshP(ChangePhotoUI ui){
			this.ui = ui;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				ui.repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
