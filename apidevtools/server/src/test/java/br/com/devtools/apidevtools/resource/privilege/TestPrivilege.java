package br.com.devtools.apidevtools.resource.privilege;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Path;

import org.junit.Test;

import br.com.devtools.apidevtools.core.permission.PermissionClass;
import br.com.devtools.apidevtools.core.permission.PermissionMethod;
import br.com.devtools.apidevtools.core.permission.PermissionMethod.Type;

import static br.com.devtools.apidevtools.core.permission.PermissionMethod.Type.*;
import br.com.devtools.apidevtools.core.searchclass.SearchClass;

public class TestPrivilege {

	@Test
	public void teste() {
		
		class PriAce {
			public String tipo;
			public Set<String> descricoes = new HashSet<>();
		}
		
		try {
			
			List<Class<?>> listClass = new SearchClass("br.com.devtools.apidevtools.resource").byAnnotation(PermissionClass.class);
			
			System.out.println(listClass.size());
			
			for (Class<?> classe : listClass) {
				
				PermissionClass permissionClass = classe.getAnnotation(PermissionClass.class);
				
				System.out.println(permissionClass.description());
				
				Map<String, PriAce> map = new HashMap<>();
				
				Method[] declaredMethods = classe.getMethods();
				for (Method method : declaredMethods) {
					PermissionMethod permission = method.getAnnotation(PermissionMethod.class);
					if (permission!=null) {
						
						if (permission.custom().length>0) {
							for (int i = 0; i < permission.custom().length; i++) {
								String string = permission.custom()[i];
								if (string.length()>0) {
									PriAce priAce = map.get(string);
									if (priAce==null) {
										priAce = new PriAce();
									}
									priAce.descricoes.add(permission.description());
									map.put(string, priAce);
								}
							}
							
						}
						
						if (permission.types().length!=1 || (permission.types()[0]!=NONE && permission.types()[0]!=ALL)) {
							
							for (Type type : permission.types()) {
								PriAce priAce = map.get(type.toString());
								if (priAce==null) {
									priAce = new PriAce();
								}
								priAce.descricoes.add(permission.description());
								map.put(type.toString(), priAce);
							}
							
						}
						
					}
					
				}
				
				map.forEach((k,v)->System.out.println("    " + k + " - " + v.descricoes));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(true);
		
	}
	
}
