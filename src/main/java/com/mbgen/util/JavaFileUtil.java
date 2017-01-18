package com.mbgen.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.mbgen.content.FileStaticContent;
import com.mbgen.model.Dao;
import com.mbgen.model.Do;
import com.mbgen.model.Properity;

public class JavaFileUtil {

	public static void createDoFile(Do doObj) {
		System.out.println("create Do: "+doObj.toString());
		
		File file=createJavaFile(doObj.getClassName(),doObj.getPackageName());
		
		try {
			FileWriter fw = new FileWriter(file);
			
			fw.write("package "+doObj.getPackageName()+";"+FileStaticContent.RT_2);
			fw.write("public class "+doObj.getClassName()+"Do"+FileStaticContent.BLANK_1+"{"+FileStaticContent.RT_2);
			
			for(Properity properity : doObj.getProperities()) {
				fw.write(FileStaticContent.BLANK_4+"private "+properity.getType()+" "+properity.getName()+";"+FileStaticContent.RT_2);
			}
			
			for(Properity properity : doObj.getProperities()) {
				
				String type=properity.getType();
				String name=properity.getName();
				String UpperCaseName=captureName(name);
				
				//生成get方法
				fw.write(FileStaticContent.BLANK_4+"public "+type+" get"+UpperCaseName+"("+type+" "+name+") {"+FileStaticContent.RT_1);
				fw.write(FileStaticContent.BLANK_8+"return this."+name+";"+FileStaticContent.RT_1);
				fw.write(FileStaticContent.BLANK_4+"}"+FileStaticContent.RT_2);
				
				//生成set方法
				fw.write(FileStaticContent.BLANK_4+"public "+"void"+" set"+UpperCaseName+"("+type+" "+name+") {"+FileStaticContent.RT_1);
				fw.write(FileStaticContent.BLANK_8+"this."+name+"="+name+";"+FileStaticContent.RT_1);
				fw.write(FileStaticContent.BLANK_4+"}"+FileStaticContent.RT_2);
			}
			
			fw.write("}");

			fw.flush();
			fw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createDaoFile(Dao dao) {
		
		System.out.println("create Dao: "+dao.toString());
		File file=createJavaFile(dao.getClassName(),dao.getPackageName());
		try {
			FileWriter fw = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static File createJavaFile(String className,String packageName) {
		StringBuffer sb=new StringBuffer();
		sb.append( System.getProperty("user.dir"));
		sb.append("/src/main/java/");
		
		String[] packageNames=packageName.split("\\.");
		for(String str :packageNames) {
			sb.append(str+"/");
		}
		sb.append(className+"Do.java");
		return new File(sb.toString());
		
	}
	
	private static String captureName(String name){
		name=name.substring(0,1).toUpperCase()+name.substring(1);
		return name;
	}
}
