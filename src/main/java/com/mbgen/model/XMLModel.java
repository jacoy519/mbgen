package com.mbgen.model;

import org.dom4j.Document;

public class XMLModel {
	
	private String packageName;
	
	private String fileName;
	
	private Document doc;
	
	public XMLModel(String packageName,String fileName,Document doc) {
		this.packageName=packageName;
		this.fileName=fileName;
		this.doc=doc;
	}
	
	public Document getDoc() {
		return this.doc;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public String getPackageName() {
		return this.packageName;
	}
	
	@Override
	public String toString() {
		return packageName+"."+fileName;
	}
	
}
