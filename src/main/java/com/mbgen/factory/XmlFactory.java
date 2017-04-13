package com.mbgen.factory;


import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.mbgen.context.ConfigContext;
import com.mbgen.context.TableContext;
import com.mbgen.model.Database;
import com.mbgen.model.XMLModel;
import com.mbgen.model.sql.SqlConfig;
import com.mbgen.util.MySQLUtil;
import com.mbgen.util.StringUtil;


public class XmlFactory {
	
	private ConfigContext configContext;
	
	private TableContext tableContext;
	
	public XmlFactory(ConfigContext configContext, TableContext tableContext) {
		this.configContext=configContext;
		this.tableContext=tableContext;
	}
	
	public XMLModel getXMLModel() {
		
		String packageName=tableContext.getNameSpace();
		String tableName=tableContext.getTableName();
		String doType=tableContext.getDoName();
		String daoType=tableContext.getDaoName();
		
		Document doc=DocumentHelper.createDocument();
		
		//添加docType
		doc.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN","http://mybatis.org/dtd/mybatis-3-mapper.dtd");
		
		//添加mapper元素
		Element mapper=doc.addElement("mapper");
		mapper.addAttribute("namespace", daoType);
		
		//添加resultMap元素
		Element resultMap=mapper.addElement("resultMap");
		String resultMapId=StringUtil.captureName(tableName)+"ResultMap";
		resultMap.addAttribute("id", resultMapId);
		resultMap.addAttribute("type", doType);
		
		Database dataBase=configContext.getDataBaseFromConfig();
		Map<String,String> tableColumns=MySQLUtil.mapTableColumns(dataBase, tableName);
		
		Boolean idSet=false;
		for(Map.Entry<String, String> tableColumn : tableColumns.entrySet()) {
			String elementType="result";
			
			if(!idSet) {
				idSet=true;
				elementType="id";
			}
			
			Element result=resultMap.addElement(elementType);
			result.addAttribute("column", tableColumn.getKey());
			result.addAttribute("property", StringUtil.getJavaVariableStr(tableColumn.getKey()));
			result.addAttribute("jdbcType", tableColumn.getValue());
		}
		
		
		//添加sql语句元素
		List<SqlConfig> sqlConfigs=tableContext.listSqlConfigs();
		for(SqlConfig sqlConfig:sqlConfigs) {
			
			 Element sqlElement=mapper.addElement(sqlConfig.getType());
			 sqlElement.addAttribute("id", sqlConfig.getId());
			 
			 if("select".equals(sqlConfig.getType())) {
				 sqlElement.addAttribute("resultMap", resultMapId);
			 }
			 
			 sqlElement.addText(sqlConfig.getSqlCommand());
			 
		}
		
		String mapperPackageName=packageName+".mapping";
		
		String fileName=StringUtil.getMapperFileName(tableName);		
		
		return new XMLModel(mapperPackageName,fileName,doc);
	}
}
