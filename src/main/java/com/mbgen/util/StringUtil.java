package com.mbgen.util;

public class StringUtil {
	
	public static String captureName(String name){
		name=name.substring(0,1).toUpperCase()+name.substring(1);
		return name;
	}
	
	public static String getJavaVariableStr(String input) {
		String[] names=input.split("_");
        
        StringBuffer strb=new StringBuffer();
        for(int i=0;i<names.length;i++) {
        	if(i!=0) {
        		names[i]=StringUtil.captureName(names[i]);
        	}
        	strb.append(names[i]);
        }
        return strb.toString();
	}
	
	public static String getIbatisTypeFrormJdbcType(String jdbcType) {
		
		if("INT".equals(jdbcType)) {
			return "INTEGER";
		}
		
		return jdbcType;
	}
}
