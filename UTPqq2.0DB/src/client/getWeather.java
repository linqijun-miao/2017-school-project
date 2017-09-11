package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class getWeather {
	static String getWeatherInform(String city){
		String baiduUrl = "";
		StringBuffer strBuf;
		
		try{
			baiduUrl = "http://api.map.baidu.com/telematics/v3/weather?location="+URLEncoder.encode(city,"utf-8") + "&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ";
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		
		strBuf = new StringBuffer();
		
		try {
			URL url = new URL(baiduUrl);
			URLConnection conn = url.openConnection();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
			String line = null;
			while((line = reader.readLine()) != null){
				strBuf.append(line + " ");
				
			}
			reader.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return strBuf.toString();
	}
}
