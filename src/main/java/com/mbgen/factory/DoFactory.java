package com.mbgen.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.mbgen.context.ConfigContext;
import com.mbgen.context.TableContext;
import com.mbgen.model.Database;
import com.mbgen.model.Do;
import com.mbgen.model.Properity;
import com.mbgen.util.MySQLUtil;
import com.mbgen.util.StringUtil;

public class DoFactory{
	
	private ConfigContext configContext;
	
	private TableContext tableContext;
	
	public DoFactory(ConfigContext configContext,TableContext tableContext) {
		this.configContext=configContext;
		this.tableContext=tableContext;
	}
	
	
	public Do getDo() {
		
		String doPackageName=tableContext.getDoPackageName();
		String doSimpleName=tableContext.getDoSimpleName();
		Database database=configContext.getDataBaseFromConfig();
		String tableName=tableContext.getTableName();
		
		List<Properity> properities=new ArrayList<Properity>();
		Map<String,String> tableColumns=MySQLUtil.mapTableColumns(database, tableName);
		for(Map.Entry<String, String> comlum:tableColumns.entrySet()) {
			String tableVar=comlum.getKey();
			String jdbcType=comlum.getValue();
			String javaVar=StringUtil.getJavaVariableStr(tableVar);
			String javaType=MySQLUtil.getJavaTypeFromMySqlType(jdbcType);
			Properity properity=new Properity(javaType,javaVar);
			properities.add(properity);  
		}
		
		return new Do(doSimpleName,doPackageName,properities);
		
	}
	
}
