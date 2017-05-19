package integration.teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.junit.Test;

import br.com.devtools.apidevtools.resource.teste.Teste;

public class TestTeste {
	
	private final String url = "http://localhost:8080/apidevtools/api/teste/";
	
	@Test
	public void helloworld() {
		
		String helloworld = ClientBuilder.newClient().target(url+"helloworld").request().get(String.class);
		assertNotNull("Hello World Not Null", helloworld);
		assertEquals(new Teste().msgHelloWorld, helloworld);
		
	}
	
	@Test
	public void ping() {
		
		String ping = ClientBuilder.newClient().target(url+"ping").request().get(String.class);
		assertNotNull("Ping Not Null", ping);
		assertTrue("Contém Ping", ping.indexOf("ping")>=0);
		
	}
	
	@Test
	public void objectGet() {
		
		Teste teste = ClientBuilder.newClient().target(url+"object").request().get(Teste.class);
		assertNotNull("Teste Not Null", teste);
		assertEquals("Id equals: ", new Long(2l), teste.getId());
		assertEquals("Description equals: ", "Test", teste.getDescription());
		
	}

	@Test
	public void objectPost() {
		
		Teste req = new Teste();
		req.setId(3l);
		req.setDescription("123");
		Teste res = ClientBuilder.newClient().target(url+"object").request().post(Entity.entity(req, MediaType.APPLICATION_JSON), Teste.class);
		assertNotNull("Teste Not Null", res);
		assertEquals("Id equals: ", new Long(req.getId()+1), res.getId());
		assertEquals("Description equals: ", req.getDescription()+"2", res.getDescription());
		
	}
	
	@Test
	public void bytesGet() {
		
		byte[] bytes = ClientBuilder.newClient().target(url+"bytes").request().get(byte[].class);
		assertNotNull("Teste Not Null", bytes);
		assertEquals("Teste Not Equals", "Teste", new String(bytes));
		
	}
	
	@Test
	public void fileUpload() {
		
		File file = new File("pom.xml");
		assertNotNull("File Not Null", file);
		assertTrue("File Not Exists", file.exists());
		
		try (FileInputStream fis = new FileInputStream(file)) {
			
			byte[] bytes = new byte[fis.available()];
			fis.read(bytes);
			
			String res = ClientBuilder.newClient().target(url+"bytes").request().post(Entity.entity(bytes, MediaType.APPLICATION_OCTET_STREAM), String.class);
			assertEquals(new String(bytes), res);

		} catch (IOException e) {
			assertTrue("Erro", false);
		}
		
	}
	
}
