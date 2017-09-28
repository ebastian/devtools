package br.com.devtools.apidevtools.resource.teste;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.devtools.apidevtools.core.help.HelpGenerator;
import br.com.devtools.apidevtools.core.permission.PermissionClass;
import br.com.devtools.apidevtools.core.rest.RestException;

@Path("teste")
@PermissionClass(description="Testes", allMethods="ALL")
public class TesteController {

	@GET
	@Path("help")
	@Produces(MediaType.TEXT_HTML)
	public String help() {
		return new HelpGenerator().help(this.getClass(), Teste.class);
	}
	
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
	public Response bytesGet(@PathParam("nome") String nome) throws Exception {
		
		InputStream is = null;
		File file = new File("C:/Users/lrmar/Downloads/" + nome);
		
		if (file.exists()) {
			
			is = new FileInputStream(file);
			return Response.ok().entity(is).build();
			
		} else {
			
			byte[] bytes = "Teste".getBytes();
			return Response.ok().entity(bytes).build();
			
		}
		
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
