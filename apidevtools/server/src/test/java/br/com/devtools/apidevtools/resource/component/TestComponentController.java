package br.com.devtools.apidevtools.resource.component;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.com.devtools.apidevtools.core.rest.RestSessao;

public class TestComponentController {

	private ComponentController controller;
	
	@Before
	public void before() {
		
		this.controller = new ComponentController();
		RestSessao sess = new RestSessao();
		
		this.controller.setSessao(sess);
		
	}
	
	@Test
	public void teste() throws Exception {
		
		//this.controller.get(1l);
		assertTrue(true);
		
		/*
		String resource = this.getClass().getClassLoader().getResource("").getPath();
		
		File direct = new File(resource.toString().replace("test-classes", "classes")+"br/com/devtools/apidevtools/resource/component");
		for (File file : direct.listFiles()) {
			System.out.println(file.getPath());
		}
		
		assertTrue("é diretorio",direct.exists());
		
		/*
		for (File file : direct.listFiles()) {
			System.out.println(file.getPath());
		}
		*/
		
		//assertEquals("ss", resource.toString().replace("test-classes", "classes"));
		
	}
	
}
