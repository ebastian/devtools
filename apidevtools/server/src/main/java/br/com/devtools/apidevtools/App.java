package br.com.devtools.apidevtools;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

@ApplicationPath("/api")
public class App extends Application {
	
	private static int count = 0;
	
	@Context
	public static ServletContext context;
	
	@Override
	public Set<Object> getSingletons() {
		
		try {
			//EntityManagerUtil.getEntityManager();
			System.out.println("ApiDevTools");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.getSingletons();
	}
	
	public App() {
	}
	
}