package com.mbgen.factory;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XmlFactory {
	
	private String configFile;
	
	private String tableFile;
	
	public XmlFactory(String configFile,String tableFile) {
		this.configFile=configFile;
		this.tableFile=tableFile;
	}
	
	public Document getXML() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document doc=null;
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document config=db.parse(configFile);
			Document table=db.parse(tableFile);
			
			Element configElement=config.getDocumentElement();
			String packageName=configElement.getAttribute("namespace");
			
			Element tableElement=table.getDocumentElement();
			String tableName=tableElement.getAttribute("name");
			
			String doName=packageName+".pojo."+tableName+"Do";
			String daoName=packageName+".dao"+tableName+"Dao";
			
			//开始写Xml
			doc=db.newDocument();
			
			//创建mapper元素
			Element mapper=doc.createElement("mapper");
			mapper.setAttribute("namespace", daoName);
			doc.appendChild(mapper);
			
			//创建ElementSql语句
			String[] methodTags={"select","delete","update","insert"};
			for(String methodTag: methodTags) {
				NodeList methodNodeList=tableElement.getElementsByTagName(methodTag);
				for(int i=0;i<methodNodeList.getLength();i++) {
					Element methodMapperEle=doc.createElement(methodTag);
					
					Element methodElement=(Element) methodNodeList.item(i);
					String id=methodElement.getAttribute("id");
					methodMapperEle.setAttribute("id",id);
					
					Element sqlElement=(Element)methodElement.getElementsByTagName("sql").item(0);
					String sql=sqlElement.getTextContent();
					methodMapperEle.setNodeValue(sql);
					
					if("select".equals(methodTag)) {
						methodMapperEle.setAttribute("resultType",doName);
					}
					
					mapper.appendChild(methodMapperEle);
				}
			}
		
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
		
		return doc;
	}
}
