package com.mbgen.factory;

public class FileFactoryTest {
	
	public static void main(String[] args) {
		FileFactory fileFactory=new FileFactory("Config.xml","Article.xml");
		fileFactory.createFile();
	}
}
