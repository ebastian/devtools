package br.com.devtools.apidevtools.core.database;

import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.devtools.apidevtools.resource.component.Component;

public class EntityManagerUtil {

	private static SessionFactory sessions = null;
	
	static {
		
		Configuration cfg = new Configuration()
			    
			.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
			
			.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
			.setProperty("hibernate.connection.username", "postgres")
			.setProperty("hibernate.connection.password", "ids0207")
			.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/devtools")
			
			.setProperty("hibernate.c3p0.min_size", "5")
			.setProperty("hibernate.c3p0.max_size", "20")
			.setProperty("hibernate.c3p0.timeout", "1800")
			.setProperty("hibernate.c3p0.max_statements", "50")
			
			.setProperty("hibernate.hbm2ddl.auto", "update")
			.setProperty("hibernate.format_sql", "true")
			.setProperty("hibernate.show_sql", "false");
		
		cfg.addAnnotatedClass(Component.class);
		//cfg.addAnnotatedClass(Sessao.class);
		
		sessions = cfg.buildSessionFactory();
		
	}

	public static EntityManager getEntityManager() {
		return sessions.createEntityManager();
	}
	
}
