package br.com.devtools.apidevtools.resource.component.version;

import static br.com.devtools.apidevtools.core.permission.PermissionMethod.DELETE;
import static br.com.devtools.apidevtools.core.permission.PermissionMethod.GET;
import static br.com.devtools.apidevtools.core.permission.PermissionMethod.PUT;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.controller.Filter;
import br.com.devtools.apidevtools.core.permission.PermissionClass;
import br.com.devtools.apidevtools.core.permission.PermissionMethod;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.resource.component.Component;
import br.com.devtools.apidevtools.resource.component.ComponentController;
import br.com.devtools.apidevtools.resource.componentlast.ComponentLast;
import br.com.devtools.apidevtools.resource.componentlast.ComponentLastResource;

@Path("component/{componentId}/version")
@PermissionClass(description="Versão")
public class VersionController extends Controller<Version> {

	private static final String ATIVACAO = "ATIVACAO"; 
	
	@PathParam("componentId") Long componentId;
	
	private Component component = null;
	
	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}
	
	public Component getComponent() throws RestException {
		
		if (this.component==null) {
			
			ComponentController componentController = new ComponentController();
			componentController.setSessao(this.getSessao());
			componentController.setContext(this.getContext());
			this.component = componentController.get(this.componentId);
			
			if (this.component==null) {
				throw new RestException("Componente com código "+this.componentId+" não encontrado.");
			}
			
		}
		
		return this.component;
		
	}
	
	
	@Override
	public Class<Version> getClasse() {
		return Version.class;
	}
	
	@Override
	protected List<Filter> getFilters() throws RestException {
		
		List<Filter> filters = new ArrayList<>();
		filters.add(new Filter("component", this.getComponent()));
		
		return filters;
		
	}
	
	@Override
	protected Version afterGet(Version model) throws RestException {
		if (model!=null && !this.getComponent().getId().equals(model.getComponent().getId())) {
			return null;
		}
		return model;
	}
	
	@Override
	protected void beforePost(Version model) throws RestException {
		model.setComponent(this.getComponent());
	}
	
	@Override
	protected void beforePut(Version model) throws RestException {
		model.setComponent(this.getComponent());
	}
	
	@Override
	protected void beforeRemove(Version model) throws RestException {
		if (!this.getComponent().getId().equals(model.getComponent().getId())) {
			throw new RestException("Esta Versão não pertence ao Componente "+this.getComponent().getId()+" - "+this.getComponent().getName()+".");
		}
	}
	
	@PUT
	@Path("{id}/kill")
	@PermissionMethod(types=ATIVACAO, description="Desativa Versão")
	public Version kill(@PathParam("id") Long id) throws Exception {
		
		try {
			
			Version version = this.get(id);
			this.beforeRemove(version);
			version.setDeath(LocalDateTime.now());
			return this.put(id, version);
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}
	
	@PUT
	@Path("{id}/revive")
	@PermissionMethod(types=ATIVACAO, description="Reativa Versão")
	public Version revive(@PathParam("id") Long id) throws Exception {
		
		try {
			
			Version version = this.get(id);
			this.beforeRemove(version);
			version.setDeath(null);
			return this.put(id, version);
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}
	
	@GET
	@Path("{id}/last")
	@PermissionMethod(types={GET, PUT, DELETE}, description="Busca Último")
	public ComponentLast last(@PathParam("id") Long versionId) throws Exception {
		return new ComponentLastResource(this.getEm()).last(this.componentId, versionId);
	}
	
}
