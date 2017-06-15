package br.com.devtools.apidevtools.resource.component;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import br.com.devtools.apidevtools.core.rest.RestSessao;
import br.com.devtools.apidevtools.resource.component.rules.exception.creation.ComponentCreationGreaterNowException;
import br.com.devtools.apidevtools.resource.component.rules.exception.creation.ComponentCreationRequiredException;
import br.com.devtools.apidevtools.resource.component.rules.exception.filename.ComponentFileNameInvalidException;
import br.com.devtools.apidevtools.resource.component.rules.exception.filename.ComponentFileNameLargerException;
import br.com.devtools.apidevtools.resource.component.rules.exception.filename.ComponentFileNameLessException;
import br.com.devtools.apidevtools.resource.component.rules.exception.filename.ComponentFileNameRequiredException;
import br.com.devtools.apidevtools.resource.component.rules.exception.name.ComponentNameLargerException;
import br.com.devtools.apidevtools.resource.component.rules.exception.name.ComponentNameLessException;
import br.com.devtools.apidevtools.resource.component.rules.exception.name.ComponentNameRequiredException;
import dao.TestEntityManagerUtil;

public class TestComponentController {

	private ComponentController controller;
	
	@Before
	public void before() {
		
		this.controller = new ComponentController();
		
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
	
	/**********************
	 * Post Name
	 **********************/
	
	@Test(expected=ComponentNameRequiredException.class)
	public void postNameRequired() throws Exception {
		this.controller.post(new TestComponentFactory().createValid().nameNull().get());
	}
	
	@Test(expected=ComponentNameLessException.class)
	public void postNameLess3() throws Exception {
		this.controller.post(new TestComponentFactory().createValid().nameLess().get());
	}
	
	@Test(expected=ComponentNameLargerException.class)
	public void postNameLarger() throws Exception {
		this.controller.post(new TestComponentFactory().createValid().nameLager().get());
	}
	
	/**********************
	 * Put Name
	 **********************/
	
	@Test(expected=ComponentNameRequiredException.class)
	public void putNameRequired() throws Exception {
		this.controller.put(1l, new TestComponentFactory().createValid().nameNull().get());
	}
	
	@Test(expected=ComponentNameLessException.class)
	public void putNameLess3() throws Exception {
		this.controller.put(1l, new TestComponentFactory().createValid().nameLess().get());
	}
	
	@Test(expected=ComponentNameLargerException.class)
	public void putNameLarger() throws Exception {
		this.controller.put(1l, new TestComponentFactory().createValid().nameLager().get());
	}
	

	/**********************
	 * Post File Name
	 **********************/
	
	@Test(expected=ComponentFileNameRequiredException.class)
	public void postFileNameRequired() throws Exception {
		this.controller.post(new TestComponentFactory().createValid().fileNameNull().get());
	}
	
	@Test(expected=ComponentFileNameLessException.class)
	public void postFileNameLess3() throws Exception {
		this.controller.post(new TestComponentFactory().createValid().fileNameLess().get());
	}
	
	@Test(expected=ComponentFileNameLargerException.class)
	public void postFileNameLarger() throws Exception {
		this.controller.post(new TestComponentFactory().createValid().fileNameLager().get());
	}

	@Test(expected=ComponentFileNameInvalidException.class)
	public void postFileNameInvalid1() throws Exception {
		this.controller.post(new TestComponentFactory().createValid().fileNameInvalid1().get());
	}
	
	@Test(expected=ComponentFileNameInvalidException.class)
	public void postFileNameInvalid2() throws Exception {
		this.controller.post(new TestComponentFactory().createValid().fileNameInvalid2().get());
	}
	
	@Test(expected=ComponentFileNameInvalidException.class)
	public void postFileNameInvalid3() throws Exception {
		this.controller.post(new TestComponentFactory().createValid().fileNameInvalid3().get());
	}
	
	/**********************
	 * Put File Name
	 **********************/
	
	@Test(expected=ComponentFileNameRequiredException.class)
	public void putFileNameRequired() throws Exception {
		this.controller.put(1l, new TestComponentFactory().createValid().fileNameNull().get());
	}
	
	@Test(expected=ComponentFileNameLessException.class)
	public void putFileNameLess3() throws Exception {
		this.controller.put(1l, new TestComponentFactory().createValid().fileNameLess().get());
	}
	
	@Test(expected=ComponentFileNameLargerException.class)
	public void putFileNameLarger() throws Exception {
		this.controller.put(1l, new TestComponentFactory().createValid().fileNameLager().get());
	}

	@Test(expected=ComponentFileNameInvalidException.class)
	public void putFileNameInvalid1() throws Exception {
		this.controller.put(1l, new TestComponentFactory().createValid().fileNameInvalid1().get());
	}
	
	@Test(expected=ComponentFileNameInvalidException.class)
	public void putFileNameInvalid2() throws Exception {
		this.controller.put(1l, new TestComponentFactory().createValid().fileNameInvalid2().get());
	}
	
	@Test(expected=ComponentFileNameInvalidException.class)
	public void putFileNameInvalid3() throws Exception {
		this.controller.put(1l, new TestComponentFactory().createValid().fileNameInvalid3().get());
	}

	/**********************
	 * Post Creation
	 **********************/
	
	@Test(expected=ComponentCreationRequiredException.class)
	public void postCreationRequired() throws Exception {
		this.controller.post(new TestComponentFactory().createValid().creationNull().get());
	}
	
	@Test(expected=ComponentCreationGreaterNowException.class)
	public void postCreationGreaterNow() throws Exception {
		this.controller.post(new TestComponentFactory().createValid().creationGreaterNow().get());
	}
	
	/**********************
	 * Put Creation
	 **********************/
	
	@Test(expected=ComponentCreationRequiredException.class)
	public void putCreationRequired() throws Exception {
		this.controller.put(1l, new TestComponentFactory().createValid().creationNull().get());
	}
	
	@Test(expected=ComponentCreationGreaterNowException.class)
	public void putCreationGreaterNow() throws Exception {
		this.controller.put(1l, new TestComponentFactory().createValid().creationGreaterNow().get());
	}
	
	/**********************
	 * CRUD Compnent
	 **********************/
	@Test
	public void crud() throws Exception {
		
		Component c1 = this.controller.post(new TestComponentFactory().createValid().get());
		assertNotNull(c1);
		assertNotNull(c1.getId());
		
		c1.setName("Teste2");
		Component c2 = this.controller.put(c1.getId(), c1);
		assertNotNull(c2);
		assertEquals(c1.getName(), c2.getName());
		
		Component c3 = this.controller.get(c1.getId());
		assertNotNull(c3);
		assertEquals(c2.getName(), c3.getName());
		
		Component c4 = this.controller.kill(c1.getId());
		assertNotNull(c4);
		assertNotNull(c4.getDeath());
		
		Component c5 = this.controller.revive(c1.getId());
		assertNotNull(c5);
		assertNull(c5.getDeath());
		
		this.controller.delete(c1.getId());
		Component c6 = this.controller.get(c1.getId());
		assertNull(c6);
		
		assertTrue(true);
		
	}

}
