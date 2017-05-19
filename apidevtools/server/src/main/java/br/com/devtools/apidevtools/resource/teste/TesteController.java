package br.com.devtools.apidevtools.resource.teste;

import java.time.LocalDateTime;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("teste")
public class TesteController {

	@GET
	@Path("helloworld")
	public String helloworld() {
		return "Hello World. Welcome to the ApiDevTools";
	}
	
	@GET
	@Path("ping")
	public String ping() {
		return "ping: " + LocalDateTime.now();
	}
	
}
