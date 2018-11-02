package com.goodstar.sentinel.core;

import com.goodstar.sentinel.comon.CommandAnnation;
import com.goodstar.sentinel.comon.CommandHandler;

@CommandAnnation("default")
public class DefaultCommandHandler implements CommandHandler {

	@Override
	public String handler(String command) {
		return "back default!";
	}

}
