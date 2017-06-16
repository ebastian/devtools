package br.com.devtools.apidevtools.resource.component.version;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rest.RestSessao;
import br.com.devtools.apidevtools.resource.component.Component;
import br.com.devtools.apidevtools.resource.component.ComponentController;
import br.com.devtools.apidevtools.resource.component.TestComponentFactory;
import br.com.devtools.apidevtools.resource.component.version.rules.exception.VersionUniqueException;
import br.com.devtools.apidevtools.resource.component.version.rules.exception.creation.VersionCreationGreaterNowException;
import br.com.devtools.apidevtools.resource.component.version.rules.exception.creation.VersionCreationRequiredException;
import br.com.devtools.apidevtools.resource.component.version.rules.exception.major.VersionMajorInvalidException;
import br.com.devtools.apidevtools.resource.component.version.rules.exception.major.VersionMajorRequiredException;
import br.com.devtools.apidevtools.resource.component.version.rules.exception.minor.VersionMinorInvalidException;
import br.com.devtools.apidevtools.resource.component.version.rules.exception.minor.VersionMinorRequiredException;
import br.com.devtools.apidevtools.resource.component.version.rules.exception.release.VersionReleaseInvalidException;
import br.com.devtools.apidevtools.resource.component.version.rules.exception.release.VersionReleaseRequiredException;
import dao.TestEntityManagerUtil;

public class TestVersionController {

	private ComponentController componentController;
	private VersionController controller;
	private Component c;
	
	@Before
	public void before() {
		
		this.controller = new VersionController();
		this.componentController = new ComponentController();
		
		RestSessao sess = new RestSessao();
		try {
			sess.setEntityMangerUtil(new TestEntityManagerUtil());
		} catch (Throwable e) {
			e.printStackTrace();
			assertTrue(false);
		}
		
		this.controller.setSessao(sess);
		this.componentController.setSessao(sess);
		
		try {
			this.c = this.componentController.post(new TestComponentFactory().createValid().get());
			this.controller.setComponentId(c.getId());
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
	
	/**********************
	 * Creation
	 **********************/
	
	@Test(expected=VersionCreationRequiredException.class)
	public void postCreationRequired() throws Exception {
		this.controller.post(new TestVersionFactory().createValid().creationNull().get());
	}
	@Test(expected=VersionCreationRequiredException.class)
	public void putCreationRequired() throws Exception {
		this.controller.put(1l, new TestVersionFactory().createValid().creationNull().get());
	}
	
	@Test(expected=VersionCreationGreaterNowException.class)
	public void postCreationGreaterNow() throws Exception {
		this.controller.post(new TestVersionFactory().createValid().creationGreaterNow().get());
	}
	@Test(expected=VersionCreationGreaterNowException.class)
	public void putCreationGreaterNow() throws Exception {
		this.controller.put(1l, new TestVersionFactory().createValid().creationGreaterNow().get());
	}
	
	/**********************
	 * Major
	 **********************/

	@Test(expected=VersionMajorRequiredException.class)
	public void postMajorRequired() throws Exception {
		this.controller.post(new TestVersionFactory().createValid().majorNull().get());
	}
	@Test(expected=VersionMajorRequiredException.class)
	public void putMajorRequired() throws Exception {
		this.controller.put(1l, new TestVersionFactory().createValid().majorNull().get());
	}
	
	@Test(expected=VersionMajorInvalidException.class)
	public void postMajorInvalid() throws Exception {
		this.controller.post(new TestVersionFactory().createValid().majorInvalid1().get());
	}
	@Test(expected=VersionMajorInvalidException.class)
	public void putMajorInvalid() throws Exception {
		this.controller.put(1l, new TestVersionFactory().createValid().majorInvalid1().get());
	}
	
	/**********************
	 * Minor
	 **********************/

	@Test(expected=VersionMinorRequiredException.class)
	public void postMinorRequired() throws Exception {
		this.controller.post(new TestVersionFactory().createValid().minorNull().get());
	}
	@Test(expected=VersionMinorRequiredException.class)
	public void putMinorRequired() throws Exception {
		this.controller.put(1l, new TestVersionFactory().createValid().minorNull().get());
	}
	
	@Test(expected=VersionMinorInvalidException.class)
	public void postMinorInvalid() throws Exception {
		this.controller.post(new TestVersionFactory().createValid().minorInvalid1().get());
	}
	@Test(expected=VersionMinorInvalidException.class)
	public void putMinorInvalid() throws Exception {
		this.controller.put(1l, new TestVersionFactory().createValid().minorInvalid1().get());
	}
	

	/**********************
	 * Release
	 **********************/

	@Test(expected=VersionReleaseRequiredException.class)
	public void postReleaseRequired() throws Exception {
		this.controller.post(new TestVersionFactory().createValid().releaseNull().get());
	}
	@Test(expected=VersionReleaseRequiredException.class)
	public void putReleaseRequired() throws Exception {
		this.controller.put(1l, new TestVersionFactory().createValid().releaseNull().get());
	}
	
	@Test(expected=VersionReleaseInvalidException.class)
	public void postReleaseInvalid() throws Exception {
		this.controller.post(new TestVersionFactory().createValid().releaseInvalid1().get());
	}
	@Test(expected=VersionReleaseInvalidException.class)
	public void putReleaseInvalid() throws Exception {
		this.controller.put(1l, new TestVersionFactory().createValid().releaseInvalid1().get());
	}
	
	/**********************
	 * Rules Version
	 **********************/
	@Test(expected=VersionUniqueException.class)
	public void postUnique() throws Exception {
		this.controller.post(new TestVersionFactory().createValid().get());
		this.controller.post(new TestVersionFactory().createValid().get());
	}
	@Test(expected=VersionUniqueException.class)
	public void putUnique() throws Exception {
		this.controller.post(new TestVersionFactory().createValid().get());
		Version version = new TestVersionFactory().createValid().get();
		version.setRelease(99);
		Version v2 = this.controller.post(version);
		v2.setRelease(1);
		this.controller.put(v2.getId(), v2);
		
	}
	
	@Test
	public void crud() throws Exception {
		
		Version v1 = this.controller.post(new TestVersionFactory().createValid().get());
		assertNotNull(v1);
		assertNotNull(v1.getId());
		
		v1.setDescription("Teste2");
		Version v2 = this.controller.put(v1.getId(), v1);
		assertNotNull(v2);
		assertEquals(v1.getDescription(), v2.getDescription());
		
		Version v3 = this.controller.get(v1.getId());
		assertNotNull(v3);
		assertEquals(v2.getDescription(), v3.getDescription());
		
		this.controller.delete(v1.getId());
		Version v4 = this.controller.get(v1.getId());
		assertNull(v4);
		
		assertTrue(true);
		
	}

	@Test
	public void kill() throws Exception {
		
		Version v1 = this.controller.post(new TestVersionFactory().createValid().get());
		this.controller.kill(v1.getId());
		
		Version v2 = this.controller.get(v1.getId());
		assertNotNull(v2);
		assertNotNull(v2.getDeath());
		
	}
	
	@Test
	public void revive() throws Exception {
		
		Version v1 = this.controller.post(new TestVersionFactory().createValid().get());
		this.controller.kill(v1.getId());
		this.controller.revive(v1.getId());
		
		Version v2 = this.controller.get(v1.getId());
		assertNotNull(v2);
		assertNull(v2.getDeath());
		
	}

	
}
