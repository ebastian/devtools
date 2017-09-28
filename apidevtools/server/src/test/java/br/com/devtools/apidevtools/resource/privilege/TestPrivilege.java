package br.com.devtools.apidevtools.resource.privilege;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.devtools.apidevtools.resource.user.permission.PermissionBuild;

public class TestPrivilege {

	@Test
	public void teste() {
		
		System.out.println(new PermissionBuild().build());
		
		assertTrue(true);
		
	}
	
}
