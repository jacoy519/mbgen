package com.mbgen.model;

import java.util.List;

public class Dao {
	
	private List<Method> methods;
	
	private String className;
	
	private String packageName;
	
	public Dao(String className,String packageName,List<Method> methods) {
		this.className=className;
		this.packageName=packageName;
		this.methods=methods;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	public String getPackageName() {
		return this.packageName;
	}
	
	public List<Method> getMethods() {
		return this.methods;
	}
	
	@Override
	public String toString() {
		return packageName+"."+className;
	}
}
