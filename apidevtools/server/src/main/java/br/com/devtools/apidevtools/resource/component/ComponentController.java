package br.com.devtools.apidevtools.resource.component;

import java.time.LocalDate;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.rest.RestException;

@Path("component")
public class ComponentController extends Controller<Component> {

	@Override
	protected Class<Component> getClasse() {
		return Component.class;
	}
	
	@PUT
	@Path("{id}/kill")
	public boolean kill(@PathParam("id") Long id) throws Exception {
		
		try {
			
			Component component = this.get(id);
			component.setDeath(LocalDate.now());
			this.put(id, component);
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
		return true;
		
	}
	
	@PUT
	@Path("{id}/revive")
	public boolean revive(@PathParam("id") Long id) throws Exception {
		
		try {
			
			Component component = this.get(id);
			component.setDeath(null);
			this.put(id, component);
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
		return true;
		
	}

}
