package br.com.devtools.apidevtools.resource.permission;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rest.RestSessao;
import br.com.devtools.apidevtools.resource.user.permission.Permission;
import br.com.devtools.apidevtools.resource.user.permission.PermissionBuild;
import br.com.devtools.apidevtools.resource.user.permission.PermissionController;
import br.com.devtools.apidevtools.resource.user.permission.PermissionGroup;
import dao.TestEntityManagerUtil;

public class TestPermissionController {

	private PermissionController controller;
	
	@Before
	public void before() {
		
		this.controller = new PermissionController();
		
		RestSessao sess = new RestSessao();
		try {
			sess.setEntityMangerUtil(new TestEntityManagerUtil());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		this.controller.setSessao(sess);
		
	}
	
	@After
	public void after() {
		try {
			this.controller.getSessao().close();
		} catch (Exception e) {
		}
	}
	
	
	@Test
	public void cud() {
		
		try {
			
			PermissionGroup grupo = new PermissionGroup();
			
			Permission p1 = new Permission();
			p1.setClassName("Teste2");
			p1.setAuthorize("aqui");
			p1.setCheck(true);
			
			List<Permission> permissions = new ArrayList<>();
			
			permissions.add(p1);
			
			grupo.setName("Teste2");
			grupo.setPermissions(permissions);
			
			this.controller.post(grupo);
			
			p1.setCheck(false);
			
			Permission p2 = new Permission();
			p2.setClassName("Teste3");
			p2.setAuthorize("aqui3");
			p2.setCheck(true);
			
			Permission p3 = new Permission();
			p3.setClassName("Teste4");
			p3.setAuthorize("aqui4");
			p3.setCheck(true);
			
			permissions.add(p2);
			permissions.add(p3);
			
			this.controller.put(grupo.getId(), grupo);
			
			//this.controller.delete(grupo.getId());
			
			Assert.assertTrue(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(e + ": " + e.getMessage(), false);
		}
		
	}
	
	
}
