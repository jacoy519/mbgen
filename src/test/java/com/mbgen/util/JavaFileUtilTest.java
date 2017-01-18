package com.mbgen.util;

import java.util.ArrayList;
import java.util.List;

import com.mbgen.model.Do;
import com.mbgen.model.Properity;

public class JavaFileUtilTest {
	
	public static void main(String[] args) {
		Properity properity_1=new Properity("int","number123123123");
		
		List<Properity> properities=new ArrayList<Properity>();
		
		properities.add(properity_1);
		
		Do doObj=new Do("TestClass","com.mbgen.model",properities);
		
		JavaFileUtil.createDoFile(doObj);
	}
}
