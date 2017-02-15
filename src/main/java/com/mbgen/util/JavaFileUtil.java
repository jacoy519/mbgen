package com.mbgen.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mbgen.content.FileStaticContent;
import com.mbgen.factory.DaoFactory;
import com.mbgen.factory.DoFactory;
import com.mbgen.model.Dao;
import com.mbgen.model.Database;
import com.mbgen.model.Do;
import com.mbgen.model.Method;
import com.mbgen.model.Properity;

public class JavaFileUtil {
	
	public static void createDoFile(Do doObj) {
		System.out.println("create Do: "+doObj.toString());
		
		File file=createJavaFile(doObj.getClassName(),doObj.getPackageName());
		
		try {
			FileWriter fw = new FileWriter(file);
			
			fw.write("package "+doObj.getPackageName()+";"+FileStaticContent.RT_2);
			fw.write("public class "+doObj.getClassName()+FileStaticContent.BLANK_1+"{"+FileStaticContent.RT_2);
			
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
			
			fw.write("package "+dao.getPackageName()+";"+FileStaticContent.RT_2);
			
			fw.write("import org.apache.ibatis.annotations.Param;"+FileStaticContent.RT_2);
			
			fw.write("public interface "+dao.getClassName()+FileStaticContent.BLANK_1+"{"+FileStaticContent.RT_2);
			
			for(Method method : dao.getMethods()) {
				fw.write(FileStaticContent.BLANK_4+"public "+method.getReturnType()+" "+method.getMethodName()+"(");
				List<Properity> properities=method.getProperities();
				for(int i=0;properities!=null && i<properities.size();i++) {
					Properity properity=properities.get(i);
					fw.write("@Param(\""+properity.getName()+"\") ");
					fw.write(properity.toString());
					if(i!=(properities.size()-1)) {
						fw.write(",");
					}
				}
				fw.write(");"+FileStaticContent.RT_2);
			}
			
			fw.write("}");

			fw.flush();
			fw.close();
			
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
		sb.append(className+".java");
		return new File(sb.toString());
		
	}
	
	private static String captureName(String name){
		name=name.substring(0,1).toUpperCase()+name.substring(1);
		return name;
	}
}
