package com.github.smallru8.ddnsClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config {
	
	Properties pro = new Properties();
	
	public Config() {
		if(!new File("conf.d").exists())
			new File("conf.d").mkdir();
		if(!new File("conf.d/DD.conf").exists()) {
			try {
				FileWriter fw = new FileWriter("conf.d/DD.conf");
				fw.write("poto=dyndns2\n" + 
						"server=dyndns.com\n" + 
						"ssl=yes\n" + 
						"username=id\n" + 
						"passwd=pass\n" + 
						"domain=domain.dyndns.com\n" + 
						"gui=yes\n" + 
						"checkip=https://checkip.amazonaws.com/\n" + 
						"time=60\n");
				fw.flush();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			InputStream  is = new FileInputStream("conf.d/DD.conf");
			pro.load(is);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getConf() {
		App.protocol = pro.getProperty("poto");
		App.NSserver= pro.getProperty("server");
		App.ssl = pro.getProperty("ssl").equals("yes");
		App.username = pro.getProperty("username");
		App.passwd = pro.getProperty("passwd");
		App.domain = pro.getProperty("domain");
		App.gui = pro.getProperty("gui").equals("yes");
		App.checkip = pro.getProperty("checkip");
		App.time = Integer.parseInt(pro.getProperty("time"));
	}
	
	public void saveConf() {
		try {
			OutputStream os = new FileOutputStream("conf.d/DD.conf");
			pro.store(os, "");
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
