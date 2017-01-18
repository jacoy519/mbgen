package com.mbgen.util;

public class StringUtil {
	
	public static String captureName(String name){
		name=name.substring(0,1).toUpperCase()+name.substring(1);
		return name;
	}
}
