package com.mbgen.factory;

import com.mbgen.context.ConfigContext;
import com.mbgen.context.TableContext;
import com.mbgen.model.Dao;
import com.mbgen.model.Do;
import com.mbgen.model.XMLModel;
import com.mbgen.util.FileUtil;

public class FileFactory {
	
	private DaoFactory daoFactory;
	
	private DoFactory doFactory;
	
	private XmlFactory xmlFactory;
	
	public FileFactory(ConfigContext configContext,TableContext tableContext) {
		this.daoFactory=new DaoFactory(tableContext);
		this.doFactory=new DoFactory(configContext,tableContext);
		this.xmlFactory=new XmlFactory(configContext,tableContext);
	}
	
	public void createFile(){
		Do doModel=doFactory.getDo();
		FileUtil.createDoFile(doModel);
		
		Dao daoModel=daoFactory.getDao();
		FileUtil.createDaoFile(daoModel);
		
		XMLModel xmlModel=xmlFactory.getXMLModel();
		FileUtil.createXmlFile(xmlModel);
	}
	
	
	
}
