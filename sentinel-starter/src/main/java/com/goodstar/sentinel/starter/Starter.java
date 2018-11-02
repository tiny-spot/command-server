package com.goodstar.sentinel.starter;

import java.io.IOException;

public class Starter {
	public static void main(String[] args) {
		try {
			System.out.println("Sentinel server is starting....");
			SentinelServer.getInstance().start(8891);
			while (!SentinelServer.getInstance().isStarted()) {
				Thread.sleep(200);
			}
			System.out.println("Sentinel server started!");
			SentinelServer.getInstance().stop();
			System.out.println(SentinelServer.getInstance().isStarted());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
