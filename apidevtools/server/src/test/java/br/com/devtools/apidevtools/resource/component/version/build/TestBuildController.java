package br.com.devtools.apidevtools.resource.component.version.build;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
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
import br.com.devtools.apidevtools.resource.component.version.build.hash.BuildHash;
import br.com.devtools.apidevtools.resource.component.version.build.rules.exceptions.BuildUniqueException;
import br.com.devtools.apidevtools.resource.component.version.build.rules.exceptions.build.BuildBuildInvalidException;
import br.com.devtools.apidevtools.resource.component.version.build.rules.exceptions.build.BuildBuildRequiredException;
import br.com.devtools.apidevtools.resource.component.version.build.rules.exceptions.creation.BuildCreationGreaterNowException;
import br.com.devtools.apidevtools.resource.component.version.build.rules.exceptions.creation.BuildCreationRequiredException;
import dao.TestEntityManagerUtil;

public class TestBuildController {

	private BuildController controller;
	private ComponentController cController;
	private VersionController vController;
	private Component c;
	private Version v;
	
	@Before
	public void before() {
		
		this.cController = new ComponentController();
		this.vController = new VersionController();
		this.controller = new BuildController();
		
		RestSessao sess = new RestSessao();
		try {
			sess.setEntityMangerUtil(new TestEntityManagerUtil());
		} catch (Throwable e) {
			e.printStackTrace();
			assertTrue(false);
		}
		
		this.cController.setSessao(sess);
		this.vController.setSessao(sess);
		this.controller.setSessao(sess);
		
		try {
			this.c = this.cController.post(new TestComponentFactory().createValid().get());
			this.vController.setComponentId(this.c.getId());
			this.v = this.vController.post(new TestVersionFactory().createValid().get());
			this.controller.setComponentId(this.c.getId());
			this.controller.setVersionId(this.v.getId());
		} catch (RestException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		
	}
	
	@After
	public void after() {
		try {
			//this.controller.getSessao().close();
		} catch (Exception e) {
		}
	}
	
	/**********************
	 * Creation
	 **********************/
	
	@Test(expected=BuildCreationRequiredException.class)
	public void postCreationRequired() throws Exception {
		this.controller.post(new TestBuildFactory().createValid().creationNull().get());
	}
	@Test(expected=BuildCreationRequiredException.class)
	public void putCreationRequired() throws Exception {
		this.controller.put(1l, new TestBuildFactory().createValid().creationNull().get());
	}
	
	@Test(expected=BuildCreationGreaterNowException.class)
	public void postCreationGreaterNow() throws Exception {
		this.controller.post(new TestBuildFactory().createValid().creationGreaterNow().get());
	}
	@Test(expected=BuildCreationGreaterNowException.class)
	public void putCreationGreaterNow() throws Exception {
		this.controller.put(1l, new TestBuildFactory().createValid().creationGreaterNow().get());
	}
	
	/**********************
	 * Build
	 **********************/
	
	@Test(expected=BuildBuildRequiredException.class)
	public void postBuildRequired() throws Exception {
		this.controller.post(new TestBuildFactory().createValid().buildNull().get());
	}
	@Test(expected=BuildBuildRequiredException.class)
	public void putBuildRequired() throws Exception {
		this.controller.put(1l, new TestBuildFactory().createValid().buildNull().get());
	}
	

	@Test(expected=BuildBuildInvalidException.class)
	public void postBuildInvalid() throws Exception {
		this.controller.post(new TestBuildFactory().createValid().buildInvalid().get());
	}
	@Test(expected=BuildBuildInvalidException.class)
	public void putBuildInvalid() throws Exception {
		this.controller.put(1l, new TestBuildFactory().createValid().buildInvalid().get());
	}
	
	@Test(expected=BuildUniqueException.class)
	public void postUnique() throws Exception {
		this.controller.post(new TestBuildFactory().createValid().get());
		this.controller.post(new TestBuildFactory().createValid().get());
	}
	@Test(expected=BuildUniqueException.class)
	public void putUnique() throws Exception {
		this.controller.post(new TestBuildFactory().createValid().get());
		Build b = new TestBuildFactory().createValid().get();
		b.setBuild(2);
		b = this.controller.post(b);
		b.setBuild(1);
		this.controller.put(b.getId(), b);
	}
	
	/**********************
	 * Rules Build
	 **********************/
	
	@Test
	public void crud() throws Exception {
		
		Build b1 = this.controller.post(new TestBuildFactory().createValid().get());
		assertNotNull(b1);
		assertNotNull(b1.getId());
		
		b1.setNotes("Teste2");
		Build b2 = this.controller.put(b1.getId(), b1);
		assertNotNull(b2);
		assertEquals(b1.getNotes(), b2.getNotes());
		
		Build b3 = this.controller.get(b1.getId());
		assertNotNull(b3);
		assertEquals(b2.getNotes(), b3.getNotes());
		
		this.controller.delete(b1.getId());
		Build b4 = this.controller.get(b1.getId());
		assertNull(b4);
		
		assertTrue(true);
		
	}
	
	@Test
	public void uploadBytes() throws Exception {
		
		Build b1 = this.controller.post(new TestBuildFactory().createValid().get());
		assertNotNull(b1);
		assertNotNull(b1.getId());
		
		String filename = "pom.xml";
		File file = new File(filename);
		
		try (FileInputStream fis = new FileInputStream(file)) {
			
			byte[] bytes = new byte[fis.available()];
			fis.read(bytes);
			
			Response upload = this.controller.upload(bytes, b1.getId());
			
			assertNotNull(upload);
			assertEquals(Status.OK.getStatusCode(), upload.getStatus());
			
			byte[] download = this.controller.download(b1.getId());
			assertNotNull(download);
			assertEquals(new String(bytes), new String(download));
			
		} catch (IOException e) {
			assertTrue("Erro", false);
		}
		
	}
	

	@Test
	public void uploadMultipart() throws Exception {
		
		Build b1 = this.controller.post(new TestBuildFactory().createValid().get());
		assertNotNull(b1);
		assertNotNull(b1.getId());
		
		String filename = "pom.xml";
		File file = new File(filename);
		InputStream is = new FileInputStream(file);
		
		MultipartFormDataInput newForm = mock(MultipartFormDataInput.class);
	    InputPart uploadedFile = mock(InputPart.class);

	    Map<String, List<InputPart>> paramsMap = new HashMap<>();
	    paramsMap.put("uploadedFile", Arrays.asList(uploadedFile));        

	    when(newForm.getFormDataMap()).thenReturn(paramsMap);
	    when(uploadedFile.getBody(InputStream.class, null)).thenReturn(is);
		
	    Response upload = this.controller.upload(newForm, b1.getId());

		assertNotNull(upload);
		assertEquals(Status.OK.getStatusCode(), upload.getStatus());
		
		try (FileInputStream fis = new FileInputStream(file)) {
			
			byte[] bytes = new byte[fis.available()];
			fis.read(bytes);
			
			byte[] download = this.controller.download(b1.getId(), "Teste.txt");
			assertNotNull(download);
			assertEquals(new String(bytes), new String(download));
			
		} catch (IOException e) {
			assertTrue("Erro", false);
		}
		
	}
	
	@Test
	public void uploadMultipartComBuild() throws Exception {
		
		//Build b1 = this.controller.post(new TestBuildFactory().createValid().get());
		//assertNotNull(b1);
		//assertNotNull(b1.getId());
		
		String filename = "pom.xml";
		File file = new File(filename);
		InputStream is = new FileInputStream(file);
		
		Build b1 = new TestBuildFactory().createValid().get();
		
		MultipartFormDataInput newForm = mock(MultipartFormDataInput.class);
	    InputPart uploadedFile = mock(InputPart.class);
	    InputPart build = mock(InputPart.class);

	    Map<String, List<InputPart>> paramsMap = new HashMap<>();
	    paramsMap.put("uploadedFile", Arrays.asList(uploadedFile));        
	    paramsMap.put("build", Arrays.asList(build));

	    when(newForm.getFormDataMap()).thenReturn(paramsMap);
	    when(uploadedFile.getBody(InputStream.class, null)).thenReturn(is);
	    when(build.getBody(Build.class, null)).thenReturn(b1);
		
	    Response upload = this.controller.createBuildAndUpload(newForm);

		assertNotNull(upload);
		assertEquals(Status.OK.getStatusCode(), upload.getStatus());
		
		try (FileInputStream fis = new FileInputStream(file)) {
			
			byte[] bytes = new byte[fis.available()];
			fis.read(bytes);
			
			byte[] download = this.controller.download(b1.getId(), "Teste.txt");
			assertNotNull(download);
			assertEquals(new String(bytes), new String(download));
			
		} catch (IOException e) {
			assertTrue("Erro", false);
		}
		
	}
	
	@Test
	public void uploadJson() throws Exception {
		
		Build b1 = this.controller.post(new TestBuildFactory().createValid().get());
		assertNotNull(b1);
		assertNotNull(b1.getId());
		
		String filename = "pom.xml";
		File file = new File(filename);
		
		try (FileInputStream fis = new FileInputStream(file)) {
			
			byte[] bytes = new byte[fis.available()];
			fis.read(bytes);
			
			Upload upload = new Upload();
			upload.setBytes(bytes);
			
			Response response = this.controller.upload(upload, b1.getId());
			
			assertNotNull(response);
			assertEquals(Status.OK.getStatusCode(), response.getStatus());
			
			byte[] download = this.controller.download(b1.getId());
			assertNotNull(download);
			assertEquals(new String(bytes), new String(download));
			
		} catch (IOException e) {
			assertTrue("Erro", false);
		}
		
	}
	

	@Test
	public void hashForDownload() throws Exception {
		
		Build b1 = this.controller.post(new TestBuildFactory().createValid().get());
		assertNotNull(b1);
		assertNotNull(b1.getId());
		
		String filename = "pom.xml";
		File file = new File(filename);
		
		try (FileInputStream fis = new FileInputStream(file)) {
			
			byte[] bytes = new byte[fis.available()];
			fis.read(bytes);
			
			Upload upload = new Upload();
			upload.setBytes(bytes);
			
			Response response = this.controller.upload(upload, b1.getId());
			
			assertNotNull(response);
			assertEquals(Status.OK.getStatusCode(), response.getStatus());
			
			BuildHash hashForDownload = this.controller.hashForDownload(b1.getId());
			assertNotNull(hashForDownload);
			
			byte[] download = this.controller.downloadByhashAndName(b1.getId(), hashForDownload.getHash(), "Teste.txt");
			assertNotNull(download);
			assertEquals(new String(bytes), new String(download));
			
		} catch (IOException e) {
			assertTrue("Erro", false);
		}
		
	}
	
	
}
