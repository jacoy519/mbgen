package com.mbgen.model.sql;

import java.util.Map;

public class SqlConfig {
	
	private String type;
	
	private String id;
	
	private String resultType;
	
	private Map<String,String> inputs;
	
	private String sqlCommand;
	
	public SqlConfig(String type,String id,String resultType,Map<String,String> inputs,String sqlCommand) {
		this.type=type;
		this.id=id;
		this.resultType=resultType;
		this.inputs=inputs;
		this.sqlCommand=sqlCommand;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getResultType() {
		return this.resultType;
	}
	
	public Map<String,String> getInputs() {
		return this.inputs;
	}
	
	public String getSqlCommand() {
		return this.sqlCommand;
	}
}
