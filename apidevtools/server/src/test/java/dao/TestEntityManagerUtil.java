package dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Properties;

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
		
		String path = System.getProperty("user.home")+"/userpass.db";
		Properties prop = new Properties();
		
		try {
			
			File f = new File(path);
			if (f.exists()) {
				prop.load(new FileReader(path));
			} else {
				prop.setProperty("username", "");
				prop.setProperty("password", "");
				prop.store(new FileWriter(f), "Arquivo contém o usuário e senha do banco de teste.");
			}
			
			if (prop.getProperty("username")==null || prop.getProperty("username").equals("")
				|| prop.getProperty("password")==null || prop.getProperty("password").equals("")) {
				System.out.println("Informe um usuário e senha para o banco no arquivo: " + path);
			}
			
		} catch (Exception e) {
			System.out.println("Arquivo "+path+" não existe");
		}
		
		
		Configuration cfg = new Configuration()
			    
				.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
				
				.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
				.setProperty("hibernate.connection.username", prop.getProperty("username"))
				.setProperty("hibernate.connection.password", prop.getProperty("password"))
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