package com.mbgen.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.mbgen.model.sql.SqlConfig;
import com.mbgen.util.StringUtil;
import com.mbgen.util.XmlFileUtil;

public class TableContext {
	
	private String tableFilePath;
	
	public TableContext(String tableFilePath) {
		this.tableFilePath=tableFilePath;
	}
	
	public String getNameSpace() {
		Document table=XmlFileUtil.getXmlDocument(tableFilePath);
		Element tableElement=table.getDocumentElement();
		String namespace=tableElement.getAttribute("namespace");
		return namespace;
	}
	
	public String getDoSimpleName() {
		String tableName=getTableName();
		return StringUtil.getJavaClassDoName(tableName);
	}
	
	public String getDoPackageName() {
		String namespace=getNameSpace();
		return namespace+".pojo";
	}
	
	public String getDoName() {
		return getDoPackageName()+"."+getDoSimpleName();
	}
	
	
	public String getDaoSimpleName() {
		String tableName=getTableName();
		return StringUtil.getJavaClassDaoName(tableName);
	}
	
	public String getDaoPackageName() {
		String namespace=getNameSpace();
		return namespace+".dao";
	}
	
	
	public String getDaoName() {
		return getDaoPackageName()+"."+getDaoSimpleName();
	}
	
	
	public String getTableName() {
		Document table=XmlFileUtil.getXmlDocument(tableFilePath);
		Element tableElement=table.getDocumentElement();
		String tableName=tableElement.getAttribute("name");
		return tableName;
	}
	
	public List<SqlConfig> listSqlConfigs() {
		//创建ElementSql语句
		String[] methodTags={"select","delete","update","insert"};
		List<SqlConfig> sqlConfigs=new ArrayList<SqlConfig>();
		
		Document table=XmlFileUtil.getXmlDocument(tableFilePath);
		Element tableElement=table.getDocumentElement();
		for(String methodTag: methodTags) {
			NodeList methodNodeList=tableElement.getElementsByTagName(methodTag);
			for(int i=0;i<methodNodeList.getLength();i++) {
				
				Element methodElement=(Element) methodNodeList.item(i);
				
				String type=methodTag;
				String id=methodElement.getAttribute("id");
				String resultType=methodElement.getAttribute("result");
				
				Element sqlElement=(Element)methodElement.getElementsByTagName("sql").item(0);
				String sqlCommand=sqlElement.getTextContent();
				
				Map<String,String> inputs=new HashMap<String,String>();
				NodeList inputNodeList=methodElement.getElementsByTagName("input");
				for(int j=0;j<inputNodeList.getLength();j++) {
					Element input=(Element)inputNodeList.item(j);
					String inputName=input.getAttribute("name");
					String inputType=getDoName();
					
					if(input.hasAttribute("jdbcType")) {
						inputType=input.getAttribute("jdbcType");
						
					}
					
					inputs.put(inputName, inputType);
				}
				
				SqlConfig sqlConfg=new SqlConfig(type,id,resultType,inputs,sqlCommand);
				sqlConfigs.add(sqlConfg);
			}
		}
		return sqlConfigs;
	}
	

}
