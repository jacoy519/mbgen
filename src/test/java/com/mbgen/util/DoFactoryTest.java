package com.mbgen.util;

import com.mbgen.factory.DoFactory;
import com.mbgen.model.Database;
import com.mbgen.model.Do;

public class DoFactoryTest {
	
	public static void main(String[] args) {
		String driver = "com.mysql.jdbc.Driver"; 
		String url="jdbc:mysql://localhost:3306/blog?useUnicode=true&characterEncoding=utf8";
		String username="root";
		String password="medivh519";
		String tableName="article";
		
		Database datebase=new Database(url,driver,username,password);
		
		DoFactory doFactory=new DoFactory("com.mbgen.model");
		Do doModel=doFactory.getDoFromMySQL(datebase, tableName);
		JavaFileUtil.createDoFile(doModel);
		
	}
}
