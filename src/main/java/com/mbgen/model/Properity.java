package com.mbgen.model;

public class Properity {
	
	private String name;
	
	private String type;
	
	public Properity(String type,String name) {
		this.name=name;
		this.type=type;
	}

	public String getType() {
		return type;
	}


	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return type+" "+name;
	}
}
