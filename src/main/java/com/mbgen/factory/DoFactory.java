package com.mbgen.factory;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mbgen.model.Database;
import com.mbgen.model.Do;
import com.mbgen.model.Properity;
import com.mbgen.util.MySQLUtil;
import com.mbgen.util.StringUtil;
import com.mysql.jdbc.Connection;

public class DoFactory{
	
	private String packageName;
	
	public DoFactory(String packageName) {
		this.packageName=packageName;
	}
	
	public String getPackageName() {
		return this.packageName;
	}
	
	public Do getDoModel(String doName,List<Properity> properities){
		return new Do(doName,packageName,properities);
	}
	
	public Do getDoFromMySQL(Database database,String table) {
		Connection connection=MySQLUtil.getConnection(database);
		List<Properity> properities=new ArrayList<Properity>();
		String doName=StringUtil.captureName(table)+"Do";
		try {
			DatabaseMetaData dbMetaData=connection.getMetaData();
			ResultSet colRet = dbMetaData.getColumns(null, "%", table, "%");
			while (colRet.next()) {
                String columnName = colRet.getString("COLUMN_NAME");  
                String columnType = colRet.getString("TYPE_NAME");  
                int datasize = colRet.getInt("COLUMN_SIZE");  
                int digits = colRet.getInt("DECIMAL_DIGITS");  
                int nullable = colRet.getInt("NULLABLE");  
                System.out.println(columnName + " " + columnType + " "+datasize + " " + digits + " " + nullable);
                
                String[] names=columnName.split("_");
                
                StringBuffer strb=new StringBuffer();
                for(int i=0;i<names.length;i++) {
                	if(i!=0) {
                		names[i]=StringUtil.captureName(names[i]);
                	}
                	strb.append(names[i]);
                }
                String properityName=strb.toString();
                String properityType=MySQLUtil.getJavaTypeFromMySqlType(columnType);
                
                Properity properity=new Properity(properityType,properityName);
                properities.add(properity);      
            }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Do(doName,packageName,properities);
		
	}
}
