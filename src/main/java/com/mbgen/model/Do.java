package com.mbgen.model;

import java.util.List;

public class Do {
	
	private String className;
	
	private String packageName;
	
	private List<Properity> properities;
	
	public Do(String className,String packageName,List<Properity> properities) {
		this.className=className;
		this.packageName=packageName;
		this.properities=properities;
	}
	
	public String getClassName(){
		return this.className;
	}
	
	public String getPackageName(){
		return this.packageName;
	}
	
	public List<Properity> getProperities(){
		return this.properities;
	}
	
	@Override
	public String toString(){
		return packageName+"."+className;
	}
}
