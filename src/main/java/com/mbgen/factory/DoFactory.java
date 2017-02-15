package com.mbgen.factory;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.mbgen.model.Database;
import com.mbgen.model.Do;
import com.mbgen.model.Properity;
import com.mbgen.util.MySQLUtil;
import com.mbgen.util.StringUtil;
import com.mysql.jdbc.Connection;

public class DoFactory{
	
	private String configFile;
	
	private String tableFile;
	
	public DoFactory(String configFile,String tableFile) {
		this.configFile=configFile;
		this.tableFile=tableFile;
	}
	
	public String getConfigFile() {
		return this.configFile;
	}
	
	public String getTableFile() {
		return this.tableFile;
	}
	
	public Do getDo() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Do doModel=null;
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document config=db.parse(configFile);
			Document table=db.parse(tableFile);
			
			
			Element configElement=config.getDocumentElement();
			String packageName=configElement.getAttribute("namespace");
			String doPackageName=packageName+".pojo";
			System.out.println("read Do packageName:"+doPackageName);
			
			
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
			System.out.println("load database config end");
			
			//根据特定的文件开始生产do文件
			System.out.println("load do");
			Element tableElement=table.getDocumentElement();
			String tableName=tableElement.getAttribute("name");
			doModel=getDoFromMySQL(database, tableName,packageName);
			
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
		return doModel;
		
	}
	
	private Do getDoFromMySQL(Database database,String table,String packageName) {
		Connection connection=MySQLUtil.getConnection(database);
		List<Properity> properities=new ArrayList<Properity>();
		String doName=StringUtil.captureName(table)+"Do";
		try {
			DatabaseMetaData dbMetaData=connection.getMetaData();
			ResultSet colRet = dbMetaData.getColumns(null, "%", table, "%");
			while (colRet.next()) {
                String columnName = colRet.getString("COLUMN_NAME");  
                String columnType = colRet.getString("TYPE_NAME");  
                int datasize = colRet.getInt("COLUMN_SIZE");  
                int digits = colRet.getInt("DECIMAL_DIGITS");  
                int nullable = colRet.getInt("NULLABLE");  
                System.out.println(columnName + " " + columnType + " "+datasize + " " + digits + " " + nullable);
                
                String[] names=columnName.split("_");
                
                StringBuffer strb=new StringBuffer();
                for(int i=0;i<names.length;i++) {
                	if(i!=0) {
                		names[i]=StringUtil.captureName(names[i]);
                	}
                	strb.append(names[i]);
                }
                String properityName=strb.toString();
                String properityType=MySQLUtil.getJavaTypeFromMySqlType(columnType);
                
                Properity properity=new Properity(properityType,properityName);
                properities.add(properity);      
            }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return new Do(doName,packageName,properities);
		
	}
}
