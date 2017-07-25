package br.com.devtools.apidevtools.resource.component.version.build.condition;

import static br.com.devtools.apidevtools.resource.component.version.build.condition.BuildConditionStatus.DISAPPROVED;
import static br.com.devtools.apidevtools.resource.component.version.build.condition.BuildConditionStatus.UNRATED;
import static br.com.devtools.apidevtools.resource.component.version.build.condition.BuildConditionStatus.APPROVED;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rest.RestSessao;
import br.com.devtools.apidevtools.resource.component.Component;
import br.com.devtools.apidevtools.resource.component.ComponentController;
import br.com.devtools.apidevtools.resource.component.TestComponentFactory;
import br.com.devtools.apidevtools.resource.component.version.TestVersionFactory;
import br.com.devtools.apidevtools.resource.component.version.Version;
import br.com.devtools.apidevtools.resource.component.version.VersionController;
import br.com.devtools.apidevtools.resource.component.version.build.Build;
import br.com.devtools.apidevtools.resource.component.version.build.BuildController;
import br.com.devtools.apidevtools.resource.component.version.build.BuildStatus;
import br.com.devtools.apidevtools.resource.component.version.build.TestBuildFactory;
import dao.TestEntityManagerUtil;

public class TestBuildConditionController {

	private BuildConditionController controller;
	private BuildController bController;
	private ComponentController cController;
	private VersionController vController;
	
	private Component c;
	private Version v;
	private Build b;
	
	@Before
	public void before() {
		
		this.cController = new ComponentController();
		this.vController = new VersionController();
		this.bController = new BuildController();
		this.controller = new BuildConditionController();
		
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
		this.controller.setSessao(sess);
		
		try {
			
			this.c = this.cController.post(new TestComponentFactory().createValid().get());
			this.vController.setComponentId(this.c.getId());
			
			this.v = this.vController.post(new TestVersionFactory().createValid().get());
			this.bController.setComponentId(this.c.getId());
			this.bController.setVersionId(this.v.getId());
			
			this.b = this.bController.post(new TestBuildFactory().createValid().get());
			this.controller.setComponentId(this.c.getId());
			this.controller.setVersionId(this.v.getId());
			this.controller.setBuildId(this.b.getId());
			
		} catch (RestException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		
	}

	@After
	public void after() {
		try {
			this.controller.getSessao().close();
		} catch (Exception e) {
		}
	}
	
	
	@Test
	public void post() throws Exception {
		
		BuildCondition bc = new TestBuildConditionFactory().createValid().get();
		bc = this.controller.post(bc);
		assertNotNull(bc);
		assertNotNull(bc.getId());
		
		Build b = this.bController.get(bc.getBuild().getId());
		assertNotNull(b);
		assertNotNull(b.getId());
		
		assertEquals(BuildStatus.DISAPPROVED, b.getStatus());
		
		assertTrue(true);
		
	}
	
	@Test
	public void post2() throws Exception {
		
		this.controller.post(new TestBuildConditionFactory().createValid().get());
		this.controller.post(new TestBuildConditionFactory().createValid().get());
		this.controller.post(new TestBuildConditionFactory().createValid().get());
		
		Build b = this.bController.get(this.b.getId());
		assertNotNull(b);
		assertNotNull(b.getId());
		
		assertEquals(BuildStatus.DISAPPROVED, b.getStatus());
		
		assertTrue(true);
		
	}
	
	@Test
	public void putUnratedUnratedUnrated() throws Exception {
		
		BuildCondition bc1 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		BuildCondition bc2 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		BuildCondition bc3 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		
		bc1.setStatus(UNRATED);
		bc2.setStatus(UNRATED);
		bc3.setStatus(UNRATED);
		
		this.controller.put(bc1.getId(), bc1);
		this.controller.put(bc2.getId(), bc2);
		this.controller.put(bc3.getId(), bc3);
		
		Build b = this.bController.get(this.b.getId());
		assertNotNull(b);
		assertNotNull(b.getId());
		
		assertEquals(BuildStatus.DISAPPROVED, b.getStatus());
		
		assertTrue(true);
		
	}
	
	@Test
	public void putDisaprovedDisaprovedDisaproved() throws Exception {
		
		BuildCondition bc1 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		BuildCondition bc2 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		BuildCondition bc3 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		
		bc1.setStatus(DISAPPROVED);
		bc2.setStatus(DISAPPROVED);
		bc3.setStatus(DISAPPROVED);
		
		this.controller.put(bc1.getId(), bc1);
		this.controller.put(bc2.getId(), bc2);
		this.controller.put(bc3.getId(), bc3);
		
		Build b = this.bController.get(this.b.getId());
		assertNotNull(b);
		assertNotNull(b.getId());
		
		assertEquals(BuildStatus.DISAPPROVED, b.getStatus());
		
		assertTrue(true);
		
	}
	
	@Test
	public void putDisaprovedDisaprovedDisaproved2() throws Exception {
		
		BuildCondition bc1 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		BuildCondition bc2 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		BuildCondition bc3 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		
		this.controller.disapprove(bc1.getId());
		this.controller.disapprove(bc2.getId());
		this.controller.disapprove(bc3.getId());
		
		Build b = this.bController.get(this.b.getId());
		assertNotNull(b);
		assertNotNull(b.getId());
		
		assertEquals(BuildStatus.DISAPPROVED, b.getStatus());
		
		assertTrue(true);
		
	}

	@Test
	public void putAprovedAprovedAproved() throws Exception {
		
		BuildCondition bc1 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		BuildCondition bc2 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		BuildCondition bc3 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		
		bc1.setStatus(APPROVED);
		bc2.setStatus(APPROVED);
		bc3.setStatus(APPROVED);
		
		this.controller.put(bc1.getId(), bc1);
		this.controller.put(bc2.getId(), bc2);
		this.controller.put(bc3.getId(), bc3);
		
		Build b = this.bController.get(this.b.getId());
		assertNotNull(b);
		assertNotNull(b.getId());
		
		assertEquals(BuildStatus.APPROVED, b.getStatus());
		
		assertTrue(true);
		
	}
	
	@Test
	public void putAprovedAprovedAproved2() throws Exception {
		
		BuildCondition bc1 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		BuildCondition bc2 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		BuildCondition bc3 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		
		this.controller.approve(bc1.getId());
		this.controller.approve(bc2.getId());
		this.controller.approve(bc3.getId());
		
		Build b = this.bController.get(this.b.getId());
		assertNotNull(b);
		assertNotNull(b.getId());
		
		assertEquals(BuildStatus.APPROVED, b.getStatus());
		
		assertTrue(true);
		
	}
	
	@Test
	public void putUnratedDisaprovedAproved() throws Exception {
		
		BuildCondition bc1 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		BuildCondition bc2 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		BuildCondition bc3 = this.controller.post(new TestBuildConditionFactory().createValid().get());
		
		bc1.setStatus(UNRATED);
		bc2.setStatus(DISAPPROVED);
		bc3.setStatus(APPROVED);
		
		this.controller.put(bc1.getId(), bc1);
		this.controller.put(bc2.getId(), bc2);
		this.controller.put(bc3.getId(), bc3);
		
		Build b = this.bController.get(this.b.getId());
		assertNotNull(b);
		assertNotNull(b.getId());
		
		assertEquals(BuildStatus.DISAPPROVED, b.getStatus());
		
		assertTrue(true);
		
	}
	
	@Test
	public void remove() throws Exception {
		
		BuildCondition bc = new TestBuildConditionFactory().createValid().get();
		bc = this.controller.post(bc);
		assertNotNull(bc);
		assertNotNull(bc.getId());
		
		Build b = this.bController.get(bc.getBuild().getId());
		assertNotNull(b);
		assertNotNull(b.getId());
		
		assertEquals(BuildStatus.DISAPPROVED, b.getStatus());
		
		this.controller.delete(bc.getId());
		b = this.bController.get(bc.getBuild().getId());
		assertEquals(BuildStatus.APPROVED, b.getStatus());
		 
		assertTrue(true);
		
	}
	
}
