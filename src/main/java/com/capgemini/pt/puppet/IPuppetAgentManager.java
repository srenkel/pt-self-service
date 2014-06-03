package com.capgemini.pt.puppet;

import com.capgemini.pt.entity.Server;

public interface IPuppetAgentManager {
	
	public boolean invokePuppetRun(Server server);

}
