package dao;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.devtools.apidevtools.core.database.IEntityMangerUtil;
import br.com.devtools.apidevtools.core.searchclass.SearchClass;
import br.com.devtools.apidevtools.resource.revinfo.RevInfo;

public class TestEntityManagerUtil implements IEntityMangerUtil {

	private static SessionFactory sessions = null;
	
	static {
		
		Configuration cfg = new Configuration()
			    
				.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
				
				.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
				.setProperty("hibernate.connection.username", "postgres")
				.setProperty("hibernate.connection.password", "ids0207")
				.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/apptoolstest")
				
				.setProperty("hibernate.c3p0.min_size", "5")
				.setProperty("hibernate.c3p0.max_size", "20")
				.setProperty("hibernate.c3p0.timeout", "1800")
				.setProperty("hibernate.c3p0.max_statements", "50")
				
				//.setProperty("org.hibernate.envers.default_schema", "public")
				.setProperty("org.hibernate.envers.audit_table_suffix", "_aud")
				
				.setProperty("hibernate.hbm2ddl.auto", "create")
				.setProperty("hibernate.format_sql", "true")
				.setProperty("hibernate.show_sql", "false");
		
		try {
			List<Class<?>> entitys = new SearchClass("br.com.devtools.apidevtools.resource").byAnnotation(Entity.class);
			for (Class<?> entity : entitys) {
				if (!entity.equals(RevInfo.class)) {
					cfg.addAnnotatedClass(entity);
				}
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