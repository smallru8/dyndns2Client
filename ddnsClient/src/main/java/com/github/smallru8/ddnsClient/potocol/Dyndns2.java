package com.github.smallru8.ddnsClient.potocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.TimerTask;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.github.smallru8.ddnsClient.App;
import com.github.smallru8.ddnsClient.Print;
import com.github.smallru8.ddnsClient.SSLX509;

public class Dyndns2 extends TimerTask{

	private String cache = "0.0.0.0";
	private SSLSocketFactory ssf;
	private String req="";
	private URL url;
	private HostnameVerifier allHostsValid;
	
	public Dyndns2() {
		System.setProperty("http.agent", "ddclient/3.8.7");
		
		allHostsValid = new HostnameVerifier()
        {
            public boolean verify(String arg0, SSLSession arg1)
            {
                return true;
            }
        };

		try {
			SSLContext sslContext=SSLContext.getInstance("SSL");
			TrustManager[] tm={new SSLX509()};
			sslContext.init(null, tm, new java.security.SecureRandom());
			ssf=sslContext.getSocketFactory();
			
			if(App.ssl)
				req = "https://";
			else
				req = "http://";
			
			req+=App.NSserver+"/nic/update?system=dyndns&hostname="+App.domain+"&myip=";
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Override
	public void run() {
		String ip = "";
		try {
			URL url_checkip;
			url_checkip = new URL(App.checkip);
			BufferedReader br = new BufferedReader(new InputStreamReader(url_checkip.openStream()));
			ip = br.readLine();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!ip.equals(cache)) {
			cache = ip;
			req+=ip;
			App.window.lbl_IP.setText(ip);
			Print.print("Myip:"+ip);
			
			String encoded = Base64.getEncoder().encodeToString((App.username + ":" + App.passwd).getBytes(StandardCharsets.UTF_8));
			
			try {
				url = new URL(req);
				InputStream is;
				if(App.ssl) {
					HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();
					conn.setDoOutput(true); 
					conn.setDoInput(true); 
					conn.setUseCaches(false);
					conn.setRequestMethod("GET");
					conn.setHostnameVerifier(allHostsValid);
					conn.setSSLSocketFactory(ssf);
					conn.setRequestProperty("Authorization", "Basic " + encoded);
					conn.connect();
					is=conn.getInputStream();
					InputStreamReader isr=new InputStreamReader(is,"utf-8"); 
					BufferedReader br=new BufferedReader(isr); 
					String line = "";
					
					while((line=br.readLine())!=null){ 
						Print.print(line);
					}
					br.close();
					isr.close();
					is.close();
					conn.disconnect();
				}else {
					HttpURLConnection conn=(HttpURLConnection)url.openConnection(); 
					conn.setDoOutput(true); 
					conn.setDoInput(true); 
					conn.setRequestMethod("GET"); 
					conn.setRequestProperty("Authorization", "Basic " + encoded);
					conn.connect(); 
					is=conn.getInputStream();
					InputStreamReader isr=new InputStreamReader(is,"utf-8"); 
					BufferedReader br=new BufferedReader(isr); 
					String line = "";
					
					while((line=br.readLine())!=null){ 
						Print.print(line);
					}
					br.close();
					isr.close();
					is.close();
					conn.disconnect();
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}else {
			Print.print("IP nochg");
		}
	}
	
}
