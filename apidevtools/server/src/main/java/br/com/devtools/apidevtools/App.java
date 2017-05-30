package br.com.devtools.apidevtools;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import br.com.devtools.apidevtools.core.rest.RestSessao;
import br.com.devtools.apidevtools.resource.person.Person;
import br.com.devtools.apidevtools.resource.person.PersonController;
import br.com.devtools.apidevtools.resource.person.acess.artifact.Acess;

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
				
				RestSessao s = new RestSessao();
				PersonController pc = new PersonController();
				pc.setContext(context2);
				pc.setSessao(s);
				
				try {
					
					Person person = pc.get(1l);
					
					if (person==null) {
						
						person = new Person();
						person.setNome("Admin");
						person.setEmail("admin@admin.com");
						pc.post(person);

						Acess acess = new Acess();
						acess.setName("admin");
						acess.setPassword("admin");
						pc.createAcess(person.getId(), acess);
						
					}
					
				} catch (Exception e) {
					try {
						s.close();
					} catch (Exception e2) {
					}
				}
				
				
				//EntityManager em = EntityManagerUtil.getEntityManager();
				
				
				
				//em.close();
				
			}
			
			
			System.out.println("ApiDevTools");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.getSingletons();
	}
	
	public App() {
	}
	
}