package br.com.devtools.apidevtools.resource.component.version;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.controller.Filter;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.resource.component.Component;
import br.com.devtools.apidevtools.resource.component.ComponentController;

@Path("component/{componentId}/version")
public class VersionController extends Controller<Version> {

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
				throw new RestException("Comnonent com código "+this.componentId+" não encontrado.");
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
			throw new RestException("Esta Version não pertence ao Component "+this.getComponent().getId()+" - "+this.getComponent().getName()+".");
		}
	}
	
	@PUT
	@Path("{id}/kill")
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
	
}
