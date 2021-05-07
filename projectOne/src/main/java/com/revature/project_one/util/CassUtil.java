package com.revature.project_one.util;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CassUtil {

	private static final Logger log = LogManager.getLogger(CassUtil.class);
	private static CassUtil instance = null;
	private CqlSession session = null;
	
	private CassUtil() {
		log.trace("Establishing connection with Cassandra");
		DriverConfigLoader loader = DriverConfigLoader.fromClasspath("application.conf");
		try {
			this.session = CqlSession.builder().withConfigLoader(loader).withKeyspace("proj_1").build();
			
		} catch(Exception e) {
			log.error("Method threw exception: "+e);
			for(StackTraceElement s: e.getStackTrace()) {
				log.warn(s);
			}
			throw e;
		}
	}
	
	public static synchronized CassUtil getInstance() {
		if(instance == null) {
			log.trace("Created Cassandra Instance");
			instance = new CassUtil();
		}
		log.trace("Returning Cassandra Instance: "+instance.toString());
		return instance;
	}
	
	public CqlSession getSession() {
		log.trace("Returning Cassandra Session: "+ session.toString());
		return session;
	}
}
