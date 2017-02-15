package com.mbgen.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mbgen.model.Dao;
import com.mbgen.model.Database;
import com.mbgen.model.Do;
import com.mbgen.model.Method;
import com.mbgen.model.Properity;
import com.mbgen.util.JavaFileUtil;
import com.mbgen.util.MySQLUtil;
import com.mbgen.util.StringUtil;
import com.mbgen.util.XmlFileUtil;

public class FileFactory {
	
	private DaoFactory daoFactory;
	
	private DoFactory doFactory;
	
	private XmlFactory xmlFactory;
	
	public FileFactory(String configFile,String tableFile) {
		this.daoFactory=new DaoFactory(configFile,tableFile);
		this.doFactory=new DoFactory(configFile,tableFile);
		this.xmlFactory=new XmlFactory(configFile,tableFile);
	}
	
	public void createFile(){
		Do doModel=doFactory.getDo();
		JavaFileUtil.createDoFile(doModel);
		
		Dao daoModel=daoFactory.getDao();
		JavaFileUtil.createDaoFile(daoModel);
		
		Document doc=xmlFactory.getXML();
		XmlFileUtil.createXmlFile(doc, "test");
	}
	
	
	
}
