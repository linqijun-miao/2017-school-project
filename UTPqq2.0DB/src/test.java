import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import server.User;

public class test {

	public static void main(String[] args) throws FileNotFoundException {
		double id = 456123;
		Double id3 = id;
		double id2=25656;
		User u = new User(id,"jun");
		User beadd = new User(id2 , "dfsd");
		u.addFriend(beadd);
		u.addFriend(u);
		u.addFriend(beadd);
		u.addFriend(u);
		u.addFriend(beadd);
		u.addFriend(u);
		u.addFriend(beadd);
		u.addFriend(u);
		String ids = "F:\\javahomework\\UTPqq\\user\\" + id3.toString() + ".txt";
		File f = new File(ids);
		try {
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(ids);
		ObjectOutputStream oop = new ObjectOutputStream(fos);
		oop.writeObject(u);
		oop.flush();
		oop.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileInputStream fi = new FileInputStream(ids);
		try {
			ObjectInputStream oi = new ObjectInputStream(fi);
			User read = (User)oi.readObject();
			System.out.println(read.getFriends().size());
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
