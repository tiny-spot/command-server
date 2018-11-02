package org.sentinel.starter;

import org.reflections.Reflections;

import com.goodstar.sentinel.comon.CommandAnnation;
import com.goodstar.sentinel.core.ReflectionUtil;

public class App {
	public static void main(String[] args) {
		Reflections reflections = ReflectionUtil.getReflection("com.goodstar.sentinel");
		System.out.println(reflections.getTypesAnnotatedWith(CommandAnnation.class).size());
	}
	
}
