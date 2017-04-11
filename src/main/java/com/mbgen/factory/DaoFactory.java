package com.mbgen.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mbgen.context.TableContext;
import com.mbgen.model.Dao;
import com.mbgen.model.Method;
import com.mbgen.model.Properity;
import com.mbgen.model.sql.SqlConfig;
import com.mbgen.util.MySQLUtil;

public class DaoFactory {
	
	private TableContext tableContext;
	
	public DaoFactory(TableContext tableContext) {
		this.tableContext=tableContext;
	}
	
	public Dao getDao() {

		String daoPackageName=tableContext.getDaoPackageName();
		String daoSimpleName=tableContext.getDaoSimpleName();
			
		List<Method> methods=new ArrayList<Method>();
		List<SqlConfig> sqlConfigs=tableContext.listSqlConfigs();
		for(SqlConfig sqlConfig:sqlConfigs) {
			methods.add(getDaoMethod(sqlConfig));
		}
		return new Dao(daoSimpleName,daoPackageName,methods);
			
	}
	
	private Method getDaoMethod(SqlConfig sqlConfig) {
		String returnType="int";
		String resultAttr=sqlConfig.getResultType();
		if("single".equals(resultAttr)) {
			returnType=tableContext.getDoName();
		}
		if("multiply".equals(resultAttr)) {
			returnType="java.util.List<"+tableContext.getDoName()+">";
		}
		
		String methodName=sqlConfig.getId();
		
		List<Properity> properities=listMethodInput(sqlConfig.getInputs());
		
		return new Method(returnType,methodName,properities);
	}
	
	private List<Properity> listMethodInput(Map<String,String> inputs) {
		List<Properity> properities=new ArrayList<Properity>();
		for(Map.Entry<String, String> input:inputs.entrySet()) {
			String inputName=input.getKey();
			String inputType=MySQLUtil.getJavaTypeFromMySqlType(input.getValue());
			properities.add(new Properity(inputType,inputName));
		}
		return properities;
	}
	
	public TableContext getTableFile() {
		return this.tableContext;
	}

}
