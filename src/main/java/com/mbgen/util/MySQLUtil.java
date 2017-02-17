package com.mbgen.util;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mbgen.model.Database;
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
	
	public static Map<String,String> mapTableColumns(Database database,String table) {
		Map<String,String> result=new LinkedHashMap<String,String>();
		Connection connection=getConnection(database);
		DatabaseMetaData dbMetaData;
		try {
			dbMetaData = connection.getMetaData();
			ResultSet colRet = dbMetaData.getColumns(null, "%", table, "%");
			while (colRet.next()) {
				String columnName = colRet.getString("COLUMN_NAME");  
				String columnType = colRet.getString("TYPE_NAME");
				String ibatisType=StringUtil.getIbatisTypeFrormJdbcType(columnType);
				result.put(columnName, ibatisType);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static String getJavaTypeFromMySqlType(String mySqlType) {
		
		if ("INTEGER".equals(mySqlType) || "INT".equals(mySqlType) ) {
			return "int";
		}
		
		if("VARCHAR".equals(mySqlType)) {
			return "java.lang.String";
		}
		return mySqlType;
	}
	
}
