package com.goodstar.sentinel.core;

import java.util.HashMap;
import java.util.Map;

import com.goodstar.sentinel.comon.CommandHandler;

public abstract class CommandRepository {

	private static Map<String, CommandHandler> CommandMaps = new HashMap<>();

	public static void registerCommandHander(String command, CommandHandler commandHandler) {
		CommandMaps.put(command, commandHandler);
	}

	public static CommandHandler get(String command) {
		return CommandMaps.get(command);
	}

}
