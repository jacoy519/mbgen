package com.mbgen.context;

import java.util.HashMap;
import java.util.Map;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.mbgen.model.Database;
import com.mbgen.util.XmlFileUtil;

public class ConfigContext {
	
	private String configFilePath;
	
	public ConfigContext(String configFilePath) {
		this.configFilePath=configFilePath;
	}
	
	public Database getDataBaseFromConfig() {
		Document config=XmlFileUtil.getXmlDocument(configFilePath);
		Element configElement=config.getDocumentElement();
		System.out.println("load database config begin");
		NodeList dataBaseList=configElement.getElementsByTagName("database");
		Map<String,String> databaseProperityMap=new HashMap<String,String>();
		for(int i=0;i<dataBaseList.getLength();i++) {
			Element dataBaseNode=(Element)dataBaseList.item(i);
			NodeList properityNodeList=dataBaseNode.getElementsByTagName("property");
			for(int j=0;j<properityNodeList.getLength();j++) {
				Element properityElement=(Element)properityNodeList.item(j);
				databaseProperityMap.put(properityElement.getAttribute("name"), properityElement.getAttribute("value"));
			}
		}
		Database database=new Database(databaseProperityMap.get("url"),databaseProperityMap.get("driver"),databaseProperityMap.get("username"),databaseProperityMap.get("password"));
		System.out.println(database.toString());
		return database;
	}
	
}
