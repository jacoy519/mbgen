package com.mbgen.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mbgen.model.Dao;
import com.mbgen.model.Do;
import com.mbgen.model.Method;
import com.mbgen.model.Properity;
import com.mbgen.util.JavaFileUtil;
import com.mbgen.util.MySQLUtil;
import com.mbgen.util.StringUtil;

public class DaoFactory {
	
	private String configFile;
	
	private String tableFile;
	
	public DaoFactory(String configFile,String tableFile) {
		this.configFile=configFile;
		this.tableFile=tableFile;
	}
	
	public Dao getDao() {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Dao daoModel=null;
		try {
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			Document config=db.parse(configFile);
			Document table=db.parse(tableFile);
			
			Element configElement=config.getDocumentElement();
			String packageName=configElement.getAttribute("namespace");
			String daoPackageName=packageName+".dao";
			System.out.println("read dao packageName:"+daoPackageName);
			
			//根据特定文件开始产生dao
			System.out.println("load dao");
			
			Element tableElement=table.getDocumentElement();
			String tableName=tableElement.getAttribute("name");
			
			
			String daoName=StringUtil.captureName(tableName)+"Dao";
			String doName=packageName+".pojo"+"."+StringUtil.captureName(tableName)+"Do";
			
			List<Method> methods=new ArrayList<Method>();
			
			NodeList methodNodeList=tableElement.getElementsByTagName("select");
			for(int i=0;i<methodNodeList.getLength();i++) {
				Element methodElement=(Element) methodNodeList.item(i);
				methods.add(getDaoMethod(doName,methodElement));
			}
			
			methodNodeList=tableElement.getElementsByTagName("update");
			for(int i=0;i<methodNodeList.getLength();i++) {
				Element methodElement=(Element) methodNodeList.item(i);
				methods.add(getDaoMethod(doName,methodElement));
			}
			
			methodNodeList=tableElement.getElementsByTagName("insert");
			for(int i=0;i<methodNodeList.getLength();i++) {
				Element methodElement=(Element) methodNodeList.item(i);
				methods.add(getDaoMethod(doName,methodElement));
			}
			
			methodNodeList=tableElement.getElementsByTagName("delete");
			for(int i=0;i<methodNodeList.getLength();i++) {
				Element methodElement=(Element) methodNodeList.item(i);
				methods.add(getDaoMethod(doName,methodElement));
			}
			
			daoModel=new Dao(daoName,daoPackageName,methods);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return daoModel;
			
	}
	
	private Method getDaoMethod(String doName,Element element) {
		String returnType="void";
		String resultAttr=element.getAttribute("result");
		if("single".equals(resultAttr)) {
			returnType=doName;
		}
		if("multiply".equals(resultAttr)) {
			returnType="java.util.List<"+doName+">";
		}
		
		String methodName=element.getAttribute("id");
		
		List<Properity> properities=listMethodInput(element,doName);
		
		return new Method(returnType,methodName,properities);
	}
	
	private List<Properity> listMethodInput(Element element,String doName) {
		List<Properity> properities=new ArrayList<Properity>();
		NodeList inputs=element.getElementsByTagName("input");
		for(int i=0;i<inputs.getLength();i++) {
			Element input=(Element)inputs.item(i);
			String name=input.getAttribute("name");
			String type=doName;
			
			if(input.hasAttribute("jdbcType")) {
				String jdbcType=input.getAttribute("jdbcType");
				type=MySQLUtil.getJavaTypeFromMySqlType(jdbcType);
			}
			
			properities.add(new Properity(type,name));
		}
		return properities;
	}
	
	public String getConfigFile() {
		return this.configFile;
	}
	
	public String getTableFile() {
		return this.tableFile;
	}

}
