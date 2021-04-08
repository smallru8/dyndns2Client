package com.github.smallru8.ddnsClient;

import java.util.Date;

public class Print {
	
	private static String buffer = "";
	
	public static void print(String str) {
		Date date = new Date();
		String pstr = "[" + date.toString() + "] " + str;
		
		if(App.gui&&!App.guiReady) {
			buffer+=pstr+"\n";
		}
		
		if(App.gui&&App.guiReady) {
			if(buffer!="") {
				App.window.textArea.append(buffer);
				buffer = "";
			}
			App.window.textArea.append(pstr+"\n");
		}
		System.out.println(pstr);
	}
	
}
