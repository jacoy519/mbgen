package com.mbgen.model;

import java.util.List;

import com.mbgen.util.StringUtil;

public class Method {
	
	private String returnType;
	
	private String methodName;
	
	private List<Properity> properities;
	
	public Method(String methodName,String returnType,List<Properity> properities) {
		this.returnType=returnType;
		this.methodName=methodName;
		this.properities=properities;
	}
	
	public String getReturnType() {
		return this.returnType;
	}
	
	public String getMethodName() {
		return this.methodName;
	}
	
	public List<Properity> getProperities() {
		return this.properities;
	}
	
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append(returnType);
		sb.append(" ");
		sb.append(StringUtil.captureName(methodName));
		sb.append("(");
		for(int i=0;i<properities.size();i++) {
			Properity properity=properities.get(i);
			sb.append(properity.toString());
			if(i!=(properities.size()-1)) {
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}
}
