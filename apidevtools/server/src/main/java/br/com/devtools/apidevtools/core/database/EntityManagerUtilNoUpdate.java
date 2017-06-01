package br.com.devtools.apidevtools.core.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;

public class EntityManagerUtilNoUpdate {

	private static SessionFactory sessions = null;
	
	static {
		
		Configuration cfg = new Configuration()
			    
			.setProperty("hibernate.connection.datasource", "java:jboss/datasources/ApiDevTools")
			.setProperty("hibernate.hbm2ddl.auto", "validate")
			.setProperty("hibernate.format_sql", "true")
			.setProperty("hibernate.show_sql", "true");
		
		sessions = cfg.buildSessionFactory();
		
	}

	public static EntityManager getEntityManager() {
		return sessions.createEntityManager();
	}
	

	public static Connection getConnection() {
		
		org.hibernate.Session session = getEntityManager().unwrap(org.hibernate.Session.class);
        MyWork myWork = new MyWork();
        session.doWork(myWork);
        return myWork.getConnection();
        
	}
	
}

class MyWork implements Work {

    Connection conn;

    @Override
    public void execute(Connection arg0) throws SQLException {
        this.conn = arg0;
    }

    Connection getConnection() {
        return conn;
    }

}