package com.github.smallru8.ddnsClient;

import com.github.smallru8.ddnsClient.potocol.Dyndns2;

public class DoJob {
	public static void runClient() {
		if(App.protocol.equals("dyndns2"))
    		App.timer.schedule(new Dyndns2(),1000,App.time*1000);
	}
}
