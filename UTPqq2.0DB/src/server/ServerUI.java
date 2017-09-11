package server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.Calendar;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class ServerUI extends JFrame {

	private JPanel contentPane;
	private int number = 0;
	private int renumber = 0;
	JLabel lblNewLabel;
	JLabel lblNewLabel_1;
	private static JTextArea information;
	public ServerUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 559, 479);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("在线人数"+ number);
		lblNewLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 18));
		lblNewLabel.setBounds(20, 13, 92, 60);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("注册用户数" + renumber);
		lblNewLabel_1.setFont(new Font("微软雅黑 Light", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(20, 71, 105, 60);
		contentPane.add(lblNewLabel_1);
		
		
		information = new JTextArea();
		information.setFont(new Font("微软雅黑 Light", Font.PLAIN, 16));
		information.setEditable(false);
		information.setLineWrap(true);
		information.setWrapStyleWord(true);
		
		JScrollPane scrollPane = new JScrollPane(information);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(14, 143, 513, 261);
		contentPane.add(scrollPane);
		new Thread(new Update(this )).start();
	}
	
	private class Update implements Runnable{
		ServerUI ui;
		public Update(ServerUI ui){
			this.ui = ui;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				//System.out.println(Thread.activeCount());
				number = ServerHelper.getOnline();
				renumber = ServerHelper.getUserNUM();
				ui.lblNewLabel.setText("在线人数"+ number);
				lblNewLabel_1.setText("注册用户数" + renumber);
				ui.repaint();
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void printINF(String a){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH); 
		int date = c.get(Calendar.DATE); 
		int hour = c.get(Calendar.HOUR_OF_DAY); 
		int minute = c.get(Calendar.MINUTE); 
		int second = c.get(Calendar.SECOND); 
		String time = ""+year + "/" + month + "/" + date + " " +hour + ":" +minute + ":" + second;
		information.append(time + "  : "+ a+"\r\n");//append在jtextarea 的行末尾加入新的字符串
	}
}
