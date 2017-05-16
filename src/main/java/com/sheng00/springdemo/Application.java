package com.sheng00.springdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.sheng00.springdemo.models.Article;
//import com.sheng00.springdemo.repositories.ArticleRepository;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("run");
        
        String className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://10.35.63.10:1433;DatabaseName=CIM";
		String user="sa";
		String password="p@ssw0rd";
		try {
			Class.forName(className);
			Connection conn= DriverManager.getConnection(url,user,password);
			Statement stmt = (Statement)conn.createStatement();   
            ResultSet rs = stmt.executeQuery("SELECT * from PublicMap");   
            ResultSetMetaData rsmt = rs.getMetaData();
            int count = rsmt.getColumnCount();
  
            while (rs.next())   
            {   
                 for (int i = 1; i <= count; i++) {
					System.out.print("[" + rsmt.getColumnName(i) + "]:" + rs.getObject(i));
					if(i<count)
						System.out.print(",");
				}
                 System.out.println("");
            }   
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    

}
