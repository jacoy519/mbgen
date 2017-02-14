package com.mbgen.factory;

import com.mbgen.model.Dao;

public class DaoFactory {
	
	private String packageName;
	
	public DaoFactory(String packageName) {
		this.packageName=packageName;
	}
	
	public String getPackageName() {
		return this.packageName;
	}
	
	public Dao getDaoModelFromXmlFile(String xmlFile) {
		return null;
	}
}
