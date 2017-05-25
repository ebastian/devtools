package integration.teste;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

public class TestBuild {
	
	private final String url = "http://localhost:8080/apidevtools/api/component/3/version/1/build/1/";
	
	//@Test
	public void fileUpload1() {
		
		try {
		
			String filename = "pom.xml";
			File filePath = new File(filename);
			
			MultipartFormDataOutput multipartFormDataOutput = new MultipartFormDataOutput();
	        multipartFormDataOutput.addFormData("uploadedFile", new FileInputStream(filePath), MediaType.MULTIPART_FORM_DATA_TYPE, filename);
			
	        Response response = ClientBuilder.newClient().target(url+"upload").request().post(Entity.entity(multipartFormDataOutput, MediaType.MULTIPART_FORM_DATA));
	        
	        assertNotNull(response);
			
		} catch (Exception e) {
			assertTrue(false);
		}
		
	}
	
	//@Test
	public void fileUpload2() {
		
		try {
		
			String filename = "teste1.pdf";
			File filePath = new File("C:/Users/lrmar/Downloads/"+filename);
			
			MultipartFormDataOutput multipartFormDataOutput = new MultipartFormDataOutput();
	        multipartFormDataOutput.addFormData("uploadedFile", new FileInputStream(filePath), MediaType.MULTIPART_FORM_DATA_TYPE, filename);
			
	        Response response = ClientBuilder.newClient().target(url+"upload").request().post(Entity.entity(multipartFormDataOutput, MediaType.MULTIPART_FORM_DATA));
	        
	        assertNotNull(response);
			
		} catch (Exception e) {
			assertTrue(false);
		}
		
	}
	
	//@Test
	public void fileUpload3() {
		
		try {
		
			String filename = "teste3.mp4";
			File filePath = new File("C:/Users/lrmar/Downloads/"+filename);
			
			MultipartFormDataOutput multipartFormDataOutput = new MultipartFormDataOutput();
	        multipartFormDataOutput.addFormData("uploadedFile", new FileInputStream(filePath), MediaType.MULTIPART_FORM_DATA_TYPE, filename);
			
	        Response response = ClientBuilder.newClient().target(url+"upload").request().post(Entity.entity(multipartFormDataOutput, MediaType.MULTIPART_FORM_DATA));
	        
	        assertNotNull(response);
			
		} catch (Exception e) {
			assertTrue(false);
		}
		
	}
	
	//@Test
	public void fileUpload4() {
		
		try {
			
			String filename = "teste6.mp4";
			File filePath = new File("C:/Users/lrmar/Downloads/"+filename);
			
			MultipartFormDataOutput multipartFormDataOutput = new MultipartFormDataOutput();
			multipartFormDataOutput.addFormData("uploadedFile", new FileInputStream(filePath), MediaType.MULTIPART_FORM_DATA_TYPE, filename);
			
			Response response = ClientBuilder.newClient().target(url+"upload").request().post(Entity.entity(multipartFormDataOutput, MediaType.MULTIPART_FORM_DATA));
			
			assertNotNull(response);
			
		} catch (Exception e) {
			assertTrue(false);
		}
		
	}
	
	//@Test
	public void fileUpload5() {
		
		try {
			
			String filename = "teste1.mkv";
			File filePath = new File("C:/Users/lrmar/Downloads/"+filename);
			
			MultipartFormDataOutput multipartFormDataOutput = new MultipartFormDataOutput();
			multipartFormDataOutput.addFormData("uploadedFile", new FileInputStream(filePath), MediaType.MULTIPART_FORM_DATA_TYPE, filename);
			
			Response response = ClientBuilder.newClient().target(url+"upload").request().post(Entity.entity(multipartFormDataOutput, MediaType.MULTIPART_FORM_DATA));
			
			assertNotNull(response);
			
		} catch (Exception e) {
			assertTrue(false);
		}
		
	}
		
	
}
