package br.com.devtools.apidevtools.resource.privilege;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.List;

import javax.ws.rs.Path;

import org.junit.Test;

import br.com.devtools.apidevtools.core.searchclass.SearchClass;

public class TestPrivilege {

	@Test
	public void teste() {
		
		try {
			
			List<Class<?>> listClass = new SearchClass("br.com.devtools.apidevtools.resource").byAnnotation(Path.class);
			
			System.out.println(listClass.size());
			
			for (Class<?> classe : listClass) {
				System.out.println(classe.getName());
				
				Path pathClasse = classe.getAnnotation(Path.class);
				
				Method[] declaredMethods = classe.getDeclaredMethods();
				for (Method method : declaredMethods) {
					Path path = method.getAnnotation(Path.class);
					if (path!=null) {
						System.out.println("--"+method.getName() + " - " + pathClasse.value()+ "/" + path.value());
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(true);
		
	}
	
}
