package com.goodstar.sentinel.plugin;

import com.goodstar.sentinel.comon.CommandAnnation;
import com.goodstar.sentinel.comon.CommandHandler;

@CommandAnnation("plugin")
public class PluginCommandHandler implements CommandHandler {

	@Override
	public String handler(String command) {
		return "back plugin!";
	}

}
