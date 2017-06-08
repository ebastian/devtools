package br.com.devtools.apidevtools.resource.component;

import org.junit.Before;
import org.junit.Test;

import br.com.devtools.apidevtools.core.rest.RestSessao;
import br.com.devtools.apidevtools.resource.component.rules.exception.ComponentNameLargerException;
import br.com.devtools.apidevtools.resource.component.rules.exception.ComponentNameLessException;
import br.com.devtools.apidevtools.resource.component.rules.exception.ComponentNameRequiredException;
import dao.TestEntityManagerUtil;

public class TestComponentController {

	private ComponentController controller;
	
	@Before
	public void before() {
		
		this.controller = new ComponentController();
		
		RestSessao sess = new RestSessao();
		try {
			sess.setEm(TestEntityManagerUtil.getEntityManager());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		this.controller.setSessao(sess);
		
	}
	
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

}
