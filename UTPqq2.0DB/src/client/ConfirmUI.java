package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import server.MyMessage;
import server.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class ConfirmUI extends JFrame {

	private JPanel contentPane;
	private String name;
	private Socket so;
	private User u;
	
	public ConfirmUI(String name, Socket so,User u) {
		this.u = u;
		this.name = name;
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
		
		JLabel lblNewLabel = new JLabel(name);
		lblNewLabel.setFont(new Font("Î¢ÈíÑÅºÚ Light", Font.PLAIN, 26));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(28, 63, 390, 65);
		contentPane.add(lblNewLabel);
		
		
		buttonListener bl = new buttonListener(so,this);
		JButton btnNewButton = new JButton("confirm");
		btnNewButton.setActionCommand("confirm");
		btnNewButton.setFont(new Font("Î¢ÈíÑÅºÚ Light", Font.PLAIN, 17));
		btnNewButton.setBounds(142, 173, 151, 46);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(bl);
	}
	
	private class buttonListener implements ActionListener{
		Socket so;
		ConfirmUI ui;
		
		public buttonListener(Socket so,ConfirmUI ui){
			this.so = so;
			this.ui = ui;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("confirm")){
				MyMessage yes = new MyMessage(ui.u);
				yes.wirteString("yes");
				yes.setType(5);
				yes.send(so);
				ui.setVisible(false);
			}
		}
		
	}
}
