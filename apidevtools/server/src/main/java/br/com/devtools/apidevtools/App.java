package br.com.devtools.apidevtools;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import br.com.devtools.apidevtools.core.database.EntityManagerUtilNoUpdate;
import br.com.devtools.apidevtools.core.rest.RestSessao;
import br.com.devtools.apidevtools.resource.user.User;
import br.com.devtools.apidevtools.resource.user.UserController;
import br.com.devtools.apidevtools.resource.user.UserType;
import br.com.devtools.apidevtools.resource.user.acess.artifact.Acess;

@ApplicationPath("/api")
public class App extends Application {
	
	private static int count = 0;
	
	@Context
	public static ServletContext context;
	
	@Context
	public static HttpServletRequest context2;
	
	@Override
	public Set<Object> getSingletons() {
		
		try {
			
			count++;
			
			if (count==1) {
				
				try (Connection connection = EntityManagerUtilNoUpdate.getConnection();) {
					
					connection.setAutoCommit(true);
					connection.prepareStatement("CREATE SCHEMA aud").executeUpdate();
					
				} catch (SQLException e) {
					if (!"42P06".equals(e.getSQLState())) {
						throw e;
					}
				} catch (Exception e) {
					throw e;
				}
				
				RestSessao s = new RestSessao();
				
				UserController pc = new UserController();
				pc.setContext(context2);
				pc.setSessao(s);
				
				try {
					
					User user = pc.get(1l);
					
					if (user==null) {
						
						user = new User();
						user.setName("Admin");
						user.setEmail("admin@admin.com");
						user.setType(UserType.ADMIN);
						pc.post(user);

						Acess acess = new Acess();
						acess.setName("admin");
						acess.setPassword("admin");
						pc.createAcess(user.getId(), acess);
						
					}
					
				} catch (Exception e) {
					throw e;
				} finally {
					try {
						s.close();
					} catch (Exception e2) {
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.getSingletons();
	}
	
	public App() {
	}
	
}