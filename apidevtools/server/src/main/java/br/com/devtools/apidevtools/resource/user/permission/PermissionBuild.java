package br.com.devtools.apidevtools.resource.user.permission;

import static br.com.devtools.apidevtools.core.permission.PermissionMethod.ALL;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Path;

import br.com.devtools.apidevtools.core.permission.PermissionClass;
import br.com.devtools.apidevtools.core.permission.PermissionMethod;
import br.com.devtools.apidevtools.core.searchclass.SearchClass;


class PriAce {
	public Set<String> descricoes = new HashSet<>();
}

class PriTela {
	public String name = "";
	public Map<String, PriAce> mapPrivilege = new HashMap<>();
}


public class PermissionBuild {

	public List<Permission> getPermission() throws Exception {
		
		try {
			
			List<Permission> returns = new ArrayList<>();
			
			List<Class<?>> listClass = new SearchClass("br.com.devtools.apidevtools.resource").byAnnotation(PermissionClass.class);
			
			Map<String, PriAce> mapPrivilege = new HashMap<>();
			
			for (Class<?> classe : listClass) {
				
				PermissionClass permissionClass = classe.getAnnotation(PermissionClass.class);
				
				Method[] declaredMethods = classe.getMethods();
				
				for (Method method : declaredMethods) {
					
					PermissionMethod permission = method.getAnnotation(PermissionMethod.class);
					if (permission!=null) {
						
						if (permission.types().length!=1 || permission.types()[0]!=ALL) {
							
							for (String type : permission.types()) {
								PriAce priAce = mapPrivilege.get(type.toString());
								if (priAce==null) {
									priAce = new PriAce();
								}
								priAce.descricoes.add(permission.description());
								mapPrivilege.put(type.toString(), priAce);
							}
							
						}
						
					} else if (permissionClass.allMethods().length()>0) {
						Path path = method.getAnnotation(Path.class);
						if (path!=null) {
							PriAce priAce = mapPrivilege.get(permissionClass.allMethods());
							if (priAce==null) {
								priAce = new PriAce();
							}
							priAce.descricoes.add(method.getName());
							mapPrivilege.put(permissionClass.allMethods(), priAce);
						}
					}
					
				}
				
				mapPrivilege.forEach((k,v)->{
					
					if (!k.equalsIgnoreCase("ALL")) {
						Permission p = new Permission();
						p.setClassName(classe.getCanonicalName());
						p.setClassDescription(permissionClass.description());
						p.setAuthorize(k);
						p.setAuthorizeDescription(v.descricoes.toString());
						p.setCheck(false);
						
						returns.add(p);
					}
					
				});
				
			}
			
			return returns;
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public Object build() {
		
		List<PriTela> list = new ArrayList<>();
		
		
		try {
			
			List<Class<?>> listClass = new SearchClass("br.com.devtools.apidevtools.resource").byAnnotation(PermissionClass.class);
			
			//System.out.println(listClass.size());
			
			for (Class<?> classe : listClass) {
				
				PermissionClass permissionClass = classe.getAnnotation(PermissionClass.class);
				
				PriTela tela = new PriTela();
				tela.name = permissionClass.description();
				
				Method[] declaredMethods = classe.getMethods();
				for (Method method : declaredMethods) {
					PermissionMethod permission = method.getAnnotation(PermissionMethod.class);
					if (permission!=null) {
						
						if (permission.types().length!=1 || permission.types()[0]!=ALL) {
							
							for (String type : permission.types()) {
								PriAce priAce = tela.mapPrivilege.get(type.toString());
								if (priAce==null) {
									priAce = new PriAce();
								}
								priAce.descricoes.add(permission.description());
								tela.mapPrivilege.put(type.toString(), priAce);
							}
							
						}
						
					} else if (permissionClass.allMethods().length()>0) {
						Path path = method.getAnnotation(Path.class);
						if (path!=null) {
							PriAce priAce = tela.mapPrivilege.get(permissionClass.allMethods());
							if (priAce==null) {
								priAce = new PriAce();
							}
							priAce.descricoes.add(method.getName());
							tela.mapPrivilege.put(permissionClass.allMethods(), priAce);
						}
					}
					
				}
				
				list.add(tela);
				//map.forEach((k,v)->retorno+=("\n    " + k + " - " + v.descricoes));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
	
}
