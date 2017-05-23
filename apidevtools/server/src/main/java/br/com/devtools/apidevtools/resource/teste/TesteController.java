package br.com.devtools.apidevtools.resource.teste;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.devtools.apidevtools.core.rest.RestException;

@Path("teste")
public class TesteController {

	@GET
	@Path("helloworld")
	public String helloWorld() {
		return new Teste().msgHelloWorld;
	}
	
	@GET
	@Path("ping")
	public String ping() {
		return "ping: " + LocalDateTime.now();
	}
	
	@GET
	@Path("object")
	@Produces(MediaType.APPLICATION_JSON)
	public Teste object() {
		Teste obj = new Teste();
		obj.setId(2l);
		obj.setDescription("Test");
		return obj;
	}
	
	@POST
	@Path("object")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Teste objectPost(Teste obj) {
		obj.setId(obj.getId()+1);
		obj.setDescription(obj.getDescription()+"2");
		return obj;
	}
	
	@GET
	@Path("bytes")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public byte[] bytesGet() {
		byte[] bytes = "Teste".getBytes();
		return bytes;
	}
	
	@GET
	@Path("bytes/{nome}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public byte[] bytesGet(@PathParam("nome") String nome) throws Exception {
		
		File file = new File("C:/Users/lrmar/Downloads/" + nome);
		
		byte[] bytes = null;
		
		if (file.exists()) {
			
			try (FileInputStream fis = new FileInputStream(file)) {
				
				bytes = new byte[fis.available()];
				fis.read(bytes);
				
			} catch (Throwable e) {
				throw new RestException(e);
			}
			
		} else {
			bytes = "Teste".getBytes();
		}
		
		return bytes;
		
	}
	
	@GET
	@Path("error")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean error() throws RestException {
		
		try {
			
			int a = 0;
			int b = 2;
			
			int c = b/a;
			
			return c==0;
			
		} catch (Exception e) {
			throw new RestException("Teste de Erro");
		}
		
	}
	
	@POST
	@Path("bytes")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public String bytesPos(byte[] bytes) {
		String teste = new String(bytes);
		return teste;
	}
	
}
