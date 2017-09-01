package br.com.devtools.apidevtools.resource.component;

import static br.com.devtools.apidevtools.core.permission.PermissionMethod.Type.*;

import java.time.LocalDateTime;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.permission.PermissionClass;
import br.com.devtools.apidevtools.core.permission.PermissionMethod;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.resource.componentlast.ComponentLast;
import br.com.devtools.apidevtools.resource.componentlast.ComponentLastResource;

@Path("component")
@PermissionClass(description="Componente")
public class ComponentController extends Controller<Component> {

	public ComponentController() {
	}
	
	@Override
	public Class<Component> getClasse() {
		return Component.class;
	}
	
	@PUT
	@Path("{id}/kill")
	@PermissionMethod(types=PUT, description="Desativar Componente")
	public Component kill(@PathParam("id") Long id) throws Exception {
		
		try {
			
			Component component = this.get(id);
			component.setDeath(LocalDateTime.now());
			return this.put(id, component);
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}
	
	@PUT
	@Path("{id}/revive")
	@PermissionMethod(types=PUT, description="Reativar Componente")
	public Component revive(@PathParam("id") Long id) throws Exception {
		
		try {
			
			Component component = this.get(id);
			component.setDeath(null);
			return this.put(id, component);
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}
	
	@GET
	@Path("{id}/last")
	@PermissionMethod(types={GET, PUT, DELETE}, description="Buscar Último")
	public ComponentLast last(@PathParam("id") Long id) throws Exception {
		
		return new ComponentLastResource(this.getEm()).last(id);
		
	}
	
}