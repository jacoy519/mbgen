package com.mbgen.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


import org.dom4j.io.XMLWriter;
import com.mbgen.content.FileStaticContent;
import com.mbgen.model.Dao;
import com.mbgen.model.Do;
import com.mbgen.model.Method;
import com.mbgen.model.Properity;
import com.mbgen.model.XMLModel;

public class FileUtil {
	
	public static void createXmlFile(XMLModel xmlModel) {
		
		File xmlFile=createXmlFile(xmlModel.getFileName(),xmlModel.getPackageName());
		try {
			XMLWriter output=new XMLWriter(new FileWriter(xmlFile));
			output.write(xmlModel.getDoc());
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
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
				String UpperCaseName=StringUtil.captureName(name);
				
				//生成get方法
				fw.write(FileStaticContent.BLANK_4+"public "+type+" get"+UpperCaseName+"() {"+FileStaticContent.RT_1);
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
		String filePath=getFilePath(packageName);
		String fullFileName=filePath+className+".java";
		return new File(fullFileName);
	}
	
	private static File createXmlFile(String className,String packageName) {
		String filePath=getFilePath(packageName);
		String fullFileName=filePath+className+".xml";
		return new File(fullFileName);
	}
	
	
	private static String getFilePath(String packageName) {
		StringBuffer sb=new StringBuffer();
		//sb.append( System.getProperty("user.dir"));
		File directory = new File(".."); 
		String path="";
		try {
			path = directory.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append( path);
		sb.append("/src/main/java/");
		
		String[] packageNames=packageName.split("\\.");
		for(String str :packageNames) {
			sb.append(str+"/");
		}
		return sb.toString();
		
	}
}
