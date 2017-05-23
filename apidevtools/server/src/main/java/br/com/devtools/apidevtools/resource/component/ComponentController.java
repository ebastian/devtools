package br.com.devtools.apidevtools.resource.component;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.com.devtools.apidevtools.core.controller.Controller;

@Path("component")
public class ComponentController extends Controller<Component> {

	@Override
	protected Class<Component> getClasse() {
		return Component.class;
	}
	
	@PUT
	@Path("{id}")
	public boolean kill(@PathParam("id") Long id) throws Exception {
		return true;
	}
	
	@PUT
	@Path("{id}")
	public boolean revive(@PathParam("id") Long id) throws Exception {
		return true;
	}

}
