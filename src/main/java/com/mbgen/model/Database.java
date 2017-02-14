package com.mbgen.model;

public class Database {
	
	private String url;
	
	private String driver;
	
	private String username;
	
	private String password;
	
	public Database(String url,String driver,String username,String password) {
		this.url=url;
		this.driver=driver;
		this.username=username;
		this.password=password;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public String getDriver() {
		return this.driver;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
}
