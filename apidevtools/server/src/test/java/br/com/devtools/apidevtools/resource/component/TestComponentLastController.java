package br.com.devtools.apidevtools.resource.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.devtools.apidevtools.core.rest.RestSessao;
import br.com.devtools.apidevtools.resource.component.version.TestVersionFactory;
import br.com.devtools.apidevtools.resource.component.version.Version;
import br.com.devtools.apidevtools.resource.component.version.VersionController;
import br.com.devtools.apidevtools.resource.component.version.build.Build;
import br.com.devtools.apidevtools.resource.component.version.build.BuildController;
import br.com.devtools.apidevtools.resource.component.version.build.TestBuildFactory;
import br.com.devtools.apidevtools.resource.componentlast.ComponentLast;
import dao.TestEntityManagerUtil;

public class TestComponentLastController {

	private BuildController bController;
	private ComponentController cController;
	private VersionController vController;
	
	@Before
	public void before() {
		
		this.cController = new ComponentController();
		this.vController = new VersionController();
		this.bController = new BuildController();
		
		RestSessao sess = new RestSessao();
		try {
			sess.setEntityMangerUtil(new TestEntityManagerUtil());
		} catch (Throwable e) {
			e.printStackTrace();
			assertTrue(false);
		}
		
		this.cController.setSessao(sess);
		this.vController.setSessao(sess);
		this.bController.setSessao(sess);
		
	}
	
	@After
	public void after() {
		try {
			this.bController.getSessao().close();
		} catch (Exception e) {
		}
	}
	
	/**********************
	 * Creation
	 **********************/
	
	@Test
	public void lastComponent() throws Exception {
		
		Component c = this.cController.post(new TestComponentFactory().createValid().get());
		ComponentLast compLast = this.cController.last(c.getId());
		assertNotNull(compLast);
		assertNotNull(compLast.getComponent());
		assertNull(compLast.getVersion());
		assertNull(compLast.getBuild());
		
	}
	
	@Test
	public void lastCompnentVersion() throws Exception {
		
		Component c = this.cController.post(new TestComponentFactory().createValid().get());
		
		this.vController.setComponentId(c.getId());
		Version v = this.vController.post(new TestVersionFactory().createValid().get());
		
		this.bController.setComponentId(c.getId());
		this.bController.setVersionId(v.getId());
		
		ComponentLast compLast = this.cController.last(c.getId());
		
		assertNotNull(compLast);
		assertNotNull(compLast.getComponent());
		assertNotNull(compLast.getVersion());
		assertNull(compLast.getBuild());
		
	}
	
	@Test
	public void lastCompnentVersionBuild() throws Exception {
		
		Component c = this.cController.post(new TestComponentFactory().createValid().get());
		
		this.vController.setComponentId(c.getId());
		Version v = this.vController.post(new TestVersionFactory().createValid().get());
		
		this.bController.setComponentId(c.getId());
		this.bController.setVersionId(v.getId());
		this.bController.post(new TestBuildFactory().createValid().get());
		
		ComponentLast compLast = this.cController.last(c.getId());
		
		assertNotNull(compLast);
		assertNotNull(compLast.getComponent());
		assertNotNull(compLast.getVersion());
		assertNotNull(compLast.getBuild());
		
	}
	
	@Test
	public void lastCompnentVersion2() throws Exception {
		
		Component c = this.cController.post(new TestComponentFactory().createValid().get());
		
		this.vController.setComponentId(c.getId());
		Version v1 = this.vController.post(new TestVersionFactory().createValid().get());
		Version v2 = new TestVersionFactory().createValid().get();
		v2.setRelease(v1.getRelease()+1);
		this.vController.post(v2);
		
		this.bController.setComponentId(c.getId());
		this.bController.setVersionId(v1.getId());
		this.bController.post(new TestBuildFactory().createValid().get());
		
		ComponentLast compLast = this.cController.last(c.getId());
		
		assertNotNull(compLast);
		assertNotNull(compLast.getComponent());
		assertNotNull(compLast.getVersion());
		assertEquals(compLast.getVersion().getId(), v2.getId());
		assertNull(compLast.getBuild());
		
	}
	
	@Test
	public void lastCompnentVersion2Build2() throws Exception {
		
		Component c = this.cController.post(new TestComponentFactory().createValid().get());
		
		this.vController.setComponentId(c.getId());
		Version v1 = this.vController.post(new TestVersionFactory().createValid().get());
		Version v2 = new TestVersionFactory().createValid().get();
		v2.setMajor(v1.getMajor()+1);
		this.vController.post(v2);
		
		this.bController.setComponentId(c.getId());
		this.bController.setVersionId(v2.getId());
		Build b1 = this.bController.post(new TestBuildFactory().createValid().get());
		Build b2 = new TestBuildFactory().createValid().get();
		b2.setBuild(b1.getBuild()+1);
		this.bController.post(b2);
		
		ComponentLast compLast = this.cController.last(c.getId());
		
		assertNotNull(compLast);
		assertNotNull(compLast.getComponent());
		assertNotNull(compLast.getVersion());
		assertEquals(compLast.getVersion().getId(), v2.getId());
		assertNotNull(compLast.getBuild());
		assertEquals(compLast.getBuild().getId(), b2.getId());
		
	}
	

	@Test
	public void lastCompnentVersion2Build1() throws Exception {
		
		Component c = this.cController.post(new TestComponentFactory().createValid().get());
		
		this.vController.setComponentId(c.getId());
		Version v1 = this.vController.post(new TestVersionFactory().createValid().get());
		Version v2 = new TestVersionFactory().createValid().get();
		v2.setRelease(v1.getRelease()+1);
		this.vController.post(v2);
		
		this.bController.setComponentId(c.getId());
		this.bController.setVersionId(v1.getId());
		Build b1 = this.bController.post(new TestBuildFactory().createValid().get());
		
		ComponentLast compLast = this.vController.last(v1.getId());
		
		assertNotNull(compLast);
		assertNotNull(compLast.getComponent());
		assertNotNull(compLast.getVersion());
		assertEquals(compLast.getVersion().getId(), v1.getId());
		assertNotNull(compLast.getBuild());
		assertEquals(compLast.getBuild().getId(), b1.getId());
		
	}
	
	
}
