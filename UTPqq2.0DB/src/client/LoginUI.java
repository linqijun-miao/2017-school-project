package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import server.MyMessage;
import server.ServerHelper;
import server.User;

import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;

public class LoginUI extends JFrame {

	private JPanel contentPane;
	private double id;
	private String passWord;
	private JTextField textField;
	private JTextField textField_1;
	private Socket so;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI frame = new LoginUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginUI() {
		setTitle("LTC");
		setIconImage(Toolkit.getDefaultToolkit().getImage("image\\timg.jpg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 565, 418);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Toolkit tl = Toolkit.getDefaultToolkit();
		Dimension screenSize = tl.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		this.setLocation(screenWidth / 4 + 200, screenHeight / 4);
		
		ButtonListener bl = new ButtonListener(this);
		
		JButton btnNewButton = new JButton("Register");
		btnNewButton.setUI(new NormalButtonUI());
		btnNewButton.addActionListener(bl);
		btnNewButton.setFont(new Font("Î¢ÈíÑÅºÚ Light", Font.PLAIN, 18));
		btnNewButton.setBounds(334, 290, 129, 42);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Login");
		btnNewButton_1.setUI(new NormalButtonUI());
		
		btnNewButton_1.addActionListener(bl);
		
		btnNewButton_1.setFont(new Font("Î¢ÈíÑÅºÚ Light", Font.PLAIN, 18));
		btnNewButton_1.setBounds(100, 290, 129, 42);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("id");
		lblNewLabel.setForeground(new Color(75, 0, 130));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 25));
		lblNewLabel.setBounds(148, 118, 72, 49);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("password");
		lblNewLabel_1.setForeground(new Color(75, 0, 130));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 25));
		lblNewLabel_1.setBounds(100, 195, 125, 42);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBackground(new Color(255, 255, 255));
		textField.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 21));
		textField.setBounds(233, 118, 230, 43);
		textField.setOpaque(false);//
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 21));
		textField_1.setOpaque(false);
		textField_1.setBounds(233, 195, 230, 42);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		ImageIcon load = new ImageIcon("image\\load.jpg");
		load.setImage(load.getImage().getScaledInstance(547, 371, Image.SCALE_DEFAULT));
		lblNewLabel_2.setIcon(load);
		lblNewLabel_2.setBounds(0, 0, 547, 371);
		contentPane.add(lblNewLabel_2);
	}
	private class ButtonListener implements ActionListener{
		LoginUI ui;
		public ButtonListener(LoginUI ui){
			this.ui = ui;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("Register")){
				RegisterUI frame = new RegisterUI();
				frame.setVisible(true);
			}
			if(e.getActionCommand().equals("Login")){
				if(textField.getText().equals("") || textField_1.getText().equals("")){
					JOptionPane.showMessageDialog(contentPane,"²»ÄÜÎª¿Õ");
					return;
				}
				id = Double.parseDouble(textField.getText());
				passWord = textField_1.getText();
				User login = new User(id,"0");
				login.setPassWord(passWord); 
				MyMessage lomsg = new MyMessage(login);
				lomsg.setType(1);
				try {
					so = new Socket("120.24.90.211",6870);//"120.24.90.211"
					lomsg.send(so);
					
					DataInputStream dis = new DataInputStream(so.getInputStream());
					ObjectInputStream ois = new ObjectInputStream(dis);
					MyMessage res = (MyMessage)ois.readObject();
					
					if(res.getString().equals("success")){
						AddFriend frame = new AddFriend(so,res.getUser());
						MainUI mu = new MainUI(so , res.getUser(),frame);//Æô¶¯´°¿Ú
						ui.dispose();
						frame.setVisible(false);
						new Thread(new ReceiveThread(mu,frame,so)).start();
						mu.setVisible(true);
					}else{
						JOptionPane.showMessageDialog(contentPane,res.getString());
					}
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
		}
		
	}
}
