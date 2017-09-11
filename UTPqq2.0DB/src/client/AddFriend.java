package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import server.MyMessage;
import server.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

public class AddFriend extends JFrame {

	private JPanel contentPane;
	private Socket so;
	private JTextField textField;
	private User u;
	
	public AddFriend(Socket so, User u) {
		this.u = u;
		setTitle("\u6DFB\u52A0\u597D\u53CB");
		this.so = so;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 427, 369);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Toolkit tl = Toolkit.getDefaultToolkit();
		Dimension screenSize = tl.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		this.setLocation(screenWidth / 4 + 200, screenHeight / 4);
		
		JLabel lblNewLabel = new JLabel("\u5BF9\u8C61ID");
		lblNewLabel.setFont(new Font("Î¢ÈíÑÅºÚ Light", Font.PLAIN, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(41, 113, 95, 34);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(199, 113, 184, 34);
		contentPane.add(textField);
		textField.setColumns(10);
		
		buttonListener bl = new buttonListener(this);
		
		JButton btnNewButton = new JButton("\u67E5\u627E");
		btnNewButton.setFont(new Font("Î¢ÈíÑÅºÚ Light", Font.PLAIN, 17));
		btnNewButton.setBounds(139, 221, 119, 40);
		btnNewButton.setUI(new friendButtonUI());
		btnNewButton.setActionCommand("addf");
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(bl);
	}
	
	public void show(String s){
		JOptionPane.showMessageDialog(contentPane,s);
	}
	
	private void searchAndAdd(double id){
		MyMessage addmgs = new MyMessage(u);
		addmgs.setType(5);
		addmgs.wirteString(((Double)id).toString());
		addmgs.send(so);
		
	}
	
	private class buttonListener implements ActionListener{
		AddFriend ad;
		public buttonListener(AddFriend ad){
			this.ad = ad;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("addf")){
				if(textField.getText().equals("")){
					JOptionPane.showMessageDialog(contentPane,"²»ÄÜÎª¿Õ");
					return;
				}
				String temp = textField.getText();
				double a = Double.parseDouble(temp);
				ad.searchAndAdd(a);
				
				
			}
		}
		
	}
}
