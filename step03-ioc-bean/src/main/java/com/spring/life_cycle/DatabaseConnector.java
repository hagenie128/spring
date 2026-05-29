package com.spring.life_cycle;

//종속되지 않음
public class DatabaseConnector {
	private String host;
	private int port;
	private boolean connected = false;
	
	public DatabaseConnector() {
		System.out.println("[DatabaseConnector] 생성자 호출 - 인스턴스화");
	}
	
	public void connect() {
		connected = true;
		System.out.println("[DatabaseConnector] connect 호출 - init-method" );
	}
	
	public void disconnect() {
		connected = false;
		System.out.println("[DatabaseConnector] disconnect 호출 - destory-method" );
	}
	
	public String query(String sql) {
		if(!connected) {
			throw new IllegalStateException("DB에 연결되지 않았습니다.");
		}
		return "[DatabaseConnector] 쿼리 실행 : " + sql;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	
}