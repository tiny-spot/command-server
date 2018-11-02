package com.goodstar.sentinel.starter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import com.goodstar.sentinel.comon.CommandHandler;
import com.goodstar.sentinel.core.CommandRepository;

public class SentinelServer {

	private static SentinelServer INSTANCE = new SentinelServer();
	
	private ExecutorService starterThread = Executors.newSingleThreadExecutor();
	private ExecutorService businessThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
	private AtomicBoolean Init = new AtomicBoolean(false);
	private volatile boolean started = false;
	private ServerSocket serverSocket;

	static {
		try {
			CommandRepository.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SentinelServer() {}

	public static SentinelServer getInstance() {
		return INSTANCE;
	}

	public void start(int port) throws IOException {
		if (!Init.compareAndSet(false, true)) {
			return;
		}
		serverSocket = new ServerSocket(port);
		starterThread.execute(new Runnable() {
			@Override
			public void run() {
				started = true;
				while (started) {
					try {
						Socket socket = serverSocket.accept();
						businessThreadPool.submit(new SentinelTask(socket));
					} catch (IOException e) {
					}
				}
			}
		});
	}

	public boolean isStarted() {
		return started;
	}

	public void stop() {
		started = false;
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
			}
		}
		starterThread.shutdown();
	}

	private class SentinelTask implements Runnable {

		private Socket socket;

		public SentinelTask(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				inputStream = socket.getInputStream();
				outputStream = socket.getOutputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
				String cmdLine = reader.readLine();
				cmdLine = cmdLine.replaceAll("GET /", "").split(" ")[0];
				String[] arr = cmdLine.split("\\?");
				CommandHandler commandHandler = CommandRepository.get(arr[0]);
				String result = "cmd not support!";
				if (commandHandler != null) {
					result = commandHandler.handler(arr[1]);
				}
				writer.write(result);
				writer.flush();
			} catch (IOException e) {
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
					}
				}
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

}
