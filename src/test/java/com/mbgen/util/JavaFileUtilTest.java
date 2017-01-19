package com.mbgen.util;

import java.util.ArrayList;
import java.util.List;

import com.mbgen.model.Dao;
import com.mbgen.model.Do;
import com.mbgen.model.Method;
import com.mbgen.model.Properity;

public class JavaFileUtilTest {
	
	
	public static void main(String[] args) {
		Properity properity_1=new Properity("int","number123123123");
		
		List<Properity> properities=new ArrayList<Properity>();
		
		properities.add(properity_1);
		
		Do doObj=new Do("TestClassDo","com.mbgen.model",properities);
		
		JavaFileUtil.createDoFile(doObj);
		
		Method method=new Method("int","testMethod",properities);
		
		List<Method> methods=new ArrayList<Method>();
		
		methods.add(method);
		
		Dao dao=new Dao("TestInterfaceDao","com.mbgen.model",methods);
		
		JavaFileUtil.createDaoFile(dao);
		
	}
}
