package com.github.smallru8.ddnsClient;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.Timer;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;

public class App 
{
	
	public static Timer timer;
	public static Config cfg;
	public static MainWindow window;
	
	public static boolean guiReady = false;
	
	public static String protocol = "dyndns2";
	public static String NSserver = "dyndns.com";
	public static boolean ssl = false;
	public static String username = "name";
	public static String passwd = "passwd";
	public static String domain = "domain.dyndns.com";
	public static boolean gui = true;
	public static String checkip = "https://checkip.amazonaws.com/";
	public static int time = 60;//60s
	
	/**
	 * @param args
	 * -poto dyndns2
	 * -s tky1.jp.skunion.org
	 * -ssl
	 * -u smallru8
	 * -passwd vdfopasdbnviulsrnt2ascad6a2
	 * -d url.dd.skunion.org
	 * -nogui
	 * -cip http://checkip.amazonaws.com/
	 * -t 60
	 * @throws IOException 
	 */
    public static void main( String[] args ) throws IOException
    {
    	try {
		    UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
    	
    	cfg = new Config();
    	cfg.getConf();
    	int MAX = args.length;
    	for(int i=0;i<MAX;i++) {
    		if(args[i].equals("-poto")&&i+1<MAX) {
    			protocol = args[++i];
    		}else if(args[i].equals("-s")&&i+1<MAX) {
    			NSserver = args[++i];
    		}else if(args[i].equals("-ssl")) {
    			ssl = true;
    		}else if(args[i].equals("-u")&&i+1<MAX) {
    			username = args[++i];
    		}else if(args[i].equals("-passwd")&&i+1<MAX) {
    			passwd = args[++i];
    		}else if(args[i].equals("-d")&&i+1<MAX) {
    			domain = args[++i];
    		}else if(args[i].equals("-nogui")) {
    			gui = false;
    		}else if(args[i].equals("-cip")&&i+1<MAX) {
    			checkip = args[++i];
    		}else if(args[i].equals("-t")&&i+1<MAX) {
    			time = Integer.parseInt(args[++i]);
    		}
    	}
    	
    	timer = new Timer();
    	
    	DoJob.runClient();
    	
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    	
    	System.out.println("Running, press any key to stop...");
    	System.in.read();
    }
}
