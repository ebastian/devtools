package br.com.devtools.apidevtools.resource.user;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.devtools.apidevtools.core.rest.RestSessao;
import br.com.devtools.apidevtools.resource.user.permission.PermissionController;
import br.com.devtools.apidevtools.resource.user.permission.PermissionGroup;
import dao.TestEntityManagerUtil;

public class TestUserController {

	private UserController controller;
	private PermissionController pController;
	
	@Before
	public void before() {
		
		this.controller = new UserController();
		this.pController = new PermissionController();
		
		RestSessao sess = new RestSessao();
		try {
			sess.setEntityMangerUtil(new TestEntityManagerUtil());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		this.controller.setSessao(sess);
		this.pController.setSessao(sess);
		
	}
	
	@After
	public void after() {
		try {
			this.controller.getSessao().close();
			this.pController.getSessao().close();
		} catch (Exception e) {
		}
	}
	
	
	@Test
	public void buscaPermissions() {
		
		try {
			
			User user = new User();
			user.setName("Teste");
			user.setType(UserType.USER);
			
			this.controller.post(user);
			
			PermissionGroup pg = new PermissionGroup();
			pg.setName("Grupo Teste");
			
			this.pController.post(pg);
			
			this.controller.addPermission(user.getId(), pg.getId());
			
			List<PermissionGroup> permissions = this.controller.getPermission(user.getId());
			
			Assert.assertNotNull(permissions);
			Assert.assertEquals(1, permissions.size());
			Assert.assertEquals("Grupo Teste", permissions.get(0).getName());
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(e + ": " + e.getMessage(), false);
		}
		
	}
	
	
}
