package client;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class friendButton extends JButton{
	private boolean haveNew = false;
	private int count = 0;
	
	private static Color blueishBorderOver = new Color(8, 247, 243); 
	
	private static Color blueishBorderSelected = new Color(49, 106, 197); 
	private double fid;
	
	public friendButton(String a,double fid){
		super(a);
		this.fid = fid;
		new Thread(new paintThread(this)).start();
	}
	
	public void haveNewm(){
		
			haveNew = true;
			
	}
	public void haveno(){
		haveNew = false;
	}
	
	public void paintComponent(Graphics g){
		
		if(!haveNew){
			super.paintComponent(g);
		}
		if(haveNew){
			super.paintComponent(g);
			
			if(count == 0){
				
				g.setColor(blueishBorderSelected); 
				g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1); 
				count ++;
			}	else{
				
				g.setColor(blueishBorderOver);
				g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1); 
				count = 0;
			}
		
			
		}
		
	
	}
	
	public double getFriendID(){
		return fid;
	}
	
	private class paintThread implements Runnable{
		friendButton fb;
		public paintThread(friendButton fb){
			this.fb = fb;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				fb.repaint();
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
