package com.mbgen.util;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mbgen.model.Database;
import com.mbgen.model.Do;
import com.mbgen.model.Properity;
import com.mysql.jdbc.Connection;


public class MySQLUtil {
	
	public static Connection getConnection(Database database) {
		String driver=database.getDriver();
		String url=database.getUrl();
		String username=database.getUsername();
		String password=database.getPassword();
		
		Connection conn=null;
		
		try {
			Class.forName(driver);
			conn=(Connection) DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static String getJavaTypeFromMySqlType(String mySqlType) {
		
		if ("INT".equals(mySqlType)) {
			return "int";
		}
		
		if("VARCHAR".equals(mySqlType)) {
			return "java.lang.String";
		}
		return null;
	}
	
}
