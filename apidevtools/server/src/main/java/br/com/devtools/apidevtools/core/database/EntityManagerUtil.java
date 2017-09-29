package br.com.devtools.apidevtools.core.database;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.devtools.apidevtools.core.searchclass.SearchClass;

public class EntityManagerUtil implements IEntityMangerUtil {

	private static SessionFactory sessions = null;
	
	static {
		
		Configuration cfg = new Configuration()
			    
			.setProperty("hibernate.connection.datasource", "java:jboss/datasources/ApiDevTools")
			
			.setProperty("org.hibernate.envers.default_schema", "aud")
			//.setProperty("org.hibernate.envers.store_data_at_delete", "true")
			.setProperty("org.hibernate.envers.audit_table_suffix", "_aud")
			
			
			.setProperty("hibernate.hbm2ddl.auto", "update")
			.setProperty("hibernate.format_sql", "false")
			.setProperty("hibernate.show_sql", "false");
		
		try {
			List<Class<?>> entitys = new SearchClass("br.com.devtools.apidevtools.resource").byAnnotation(Entity.class);
			for (Class<?> entity : entitys) {
				cfg.addAnnotatedClass(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sessions = cfg.buildSessionFactory();
		
	}

	public EntityManager getEntityManager() {
		return sessions.createEntityManager();
	}
	
}