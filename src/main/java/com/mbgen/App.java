package com.mbgen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mbgen.context.ConfigContext;
import com.mbgen.context.TableContext;
import com.mbgen.factory.FileFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	while(true) {
        	System.out.println("input mapper name (input out to exit)");
        	BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        	try {
    			String str = bf.readLine();
    			if("out".equals(str)) {
    				break;
    			}
    			TableContext tableContext=new TableContext( System.getProperty("user.dir")+"/mapper/"+str+".xml");
    			ConfigContext configContext=new ConfigContext( System.getProperty("user.dir")+"/config/Config.xml");
    			FileFactory fileFactory=new FileFactory(configContext,tableContext);
    			fileFactory.createFile();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}

    	
		
		
    }
}
