package com.goodstar.sentinel.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.goodstar.sentinel.comon.CommandAnnation;
import com.goodstar.sentinel.comon.CommandHandler;

public final class CommandRepository {

	private static Map<String, CommandHandler> CommandMaps = new HashMap<>();

	public static void registerCommandHander(String command, CommandHandler commandHandler) {
		CommandMaps.put(command, commandHandler);
	}

	public static CommandHandler get(String command) {
		return CommandMaps.get(command);
	}
	
	public static void init() throws Exception {
		Reflections reflections = ReflectionUtil.getReflection("com.goodstar.sentinel");
		Set<Class<?>> clazs = reflections.getTypesAnnotatedWith(CommandAnnation.class);
		if (clazs != null && !clazs.isEmpty()) {
			for (Class<?> claz : clazs) {
				CommandMaps.put(claz.getAnnotation(CommandAnnation.class).value(), (CommandHandler) claz.newInstance());
			}
		}
	}

}
