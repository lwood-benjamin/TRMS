package com.revature.project_one.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.revature.project_one.util.CassUtil;
import com.revature.project_one.util.S3Util;

public class DatabaseTest {
	private static CqlSession session = CassUtil.getInstance().getSession();
	private static S3Util instance = S3Util.getInstance();

	public static void main(String[] args) {
		//dbtest();
		//ssstest();
		//sssTestGet();
	}
	
	
	private static void dbtest() {
		String query = "Select * from users;";

		ResultSet rs = session.execute(query);
		rs.forEach(data -> {
			System.out.println("Query Results: "+data.toString());
		});
	}
	
	private static void ssstest(){
		//instance.uploadToBucket("", null);
		//instance.listBuckets();
        byte[] b = new byte[10];
        new Random().nextBytes(b);
		//instance.uploadToBucket("test2", b);
        try {
			System.out.print("Test output: ");
        	InputStream s = instance.getObject("test");
        	System.out.println("$"+s);
		} catch (Exception e) {
			e.printStackTrace();

			System.out.println("Exception block");
		} finally {
			System.out.println("Finally block");
		}
	}
	
	private static void sssTestGet() {
		instance.listBuckets();
	}

	
}
