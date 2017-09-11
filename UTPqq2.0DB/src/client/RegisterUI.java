package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import server.MyMessage;
import server.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Toolkit;

public class RegisterUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	private String name = null;
	private double id;
	private String password = null;
	private boolean sex;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private Socket so;
	
	public RegisterUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("image\\mars.jpg"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 475, 460);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Toolkit tl = Toolkit.getDefaultToolkit();
		Dimension screenSize = tl.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		this.setLocation(screenWidth / 4 + 250, screenHeight / 4 -70);
		
		JLabel lblNewLabel = new JLabel("Register");
		lblNewLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 32));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(161, 13, 131, 47);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 26));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(31, 84, 94, 54);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Name");
		lblNewLabel_2.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 26));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(31, 151, 94, 47);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Password");
		lblNewLabel_3.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 22));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(31, 211, 94, 47);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Sex");
		lblNewLabel_4.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 26));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(31, 282, 94, 37);
		contentPane.add(lblNewLabel_4);
		
		textField = new JTextField();
		textField.setBounds(202, 95, 164, 37);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(202, 161, 164, 37);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(202, 221, 164, 37);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		rdbtnNewRadioButton = new JRadioButton("male"); //true
		rdbtnNewRadioButton.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 14));
		rdbtnNewRadioButton.setBounds(202, 282, 61, 37);
		contentPane.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("female");
		rdbtnNewRadioButton_1.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 14));
		rdbtnNewRadioButton_1.setBounds(290, 282, 84, 37);
		contentPane.add(rdbtnNewRadioButton_1);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnNewRadioButton);
		bg.add(rdbtnNewRadioButton_1);
		
		ButtonListener bl = new ButtonListener(this);
		JButton btnConfirm = new JButton("confirm");
		btnConfirm.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		btnConfirm.setBounds(76, 332, 113, 43);
		btnConfirm.addActionListener(bl);
		contentPane.add(btnConfirm);
		
		JButton btnNewButton = new JButton("cancel");
		btnNewButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		btnNewButton.setBounds(261, 332, 113, 43);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(bl);
		
		JLabel lblNewLabel_5 = new JLabel("number only");
		lblNewLabel_5.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
		lblNewLabel_5.setBounds(202, 132, 94, 18);
		contentPane.add(lblNewLabel_5);
	}
	
	private class ButtonListener implements ActionListener{
		RegisterUI ui;
		public ButtonListener(RegisterUI ui){
			this.ui = ui;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("confirm")){
				
				name = textField_1.getText();
				password = textField_2.getText();
				if(textField_1.getText().equals("") || textField_2.getText().equals("") || textField.getText().equals("") ){
					JOptionPane.showMessageDialog(contentPane,"不能为空");
					return;
				}
				id = Double.parseDouble(textField.getText());
				if(rdbtnNewRadioButton.isSelected()){
					sex = true;
				}else{
					sex = false;
				}
				
				User newUser = new User(id,name);//建立新用户
				newUser.setPassWord(password);
				newUser.setSex(sex);
				
				MyMessage msg = new MyMessage(newUser);
				msg.setType(0);
				try {
					so = new Socket("120.24.90.211",6870);
					msg.send(so);
					
					DataInputStream dis = new DataInputStream(so.getInputStream());
					ObjectInputStream ois = new ObjectInputStream(dis);
					MyMessage res = (MyMessage)ois.readObject();
					
					if(res.getString().equals("success")){
						JOptionPane.showMessageDialog(contentPane,"success");
						ui.dispose();
					}else{
						JOptionPane.showMessageDialog(contentPane,"fail");
						ui.dispose();
					}
					
					MyMessage stop = new MyMessage();
					stop.setType(4);
					stop.send(so);
					
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(e.getActionCommand().equals("cancel")){
				ui.dispose();
			}
		}
		
	}
	
	
}
