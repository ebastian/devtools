package br.com.devtools.apidevtools.resource.component.version.build;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.controller.Filter;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.resource.component.version.Version;
import br.com.devtools.apidevtools.resource.component.version.VersionController;

@Path("component/{componentId}/version/{versionId}/build")
public class BuildController extends Controller<Build> {

	@PathParam("componentId") Long componentId;
	@PathParam("versionId") Long versionId;
	
	@Override
	protected Class<Build> getClasse() {
		return Build.class;
	}
	
	private Version version = null;
	
	public Version getVersion() throws RestException {
		
		if (this.version==null) {
			
			VersionController versionController = new VersionController();
			versionController.setSessao(this.getSessao());
			versionController.setContext(this.getContext());
			versionController.setComponentId(this.componentId);
			this.version = versionController.get(this.versionId);
			
			if (this.version==null) {
				throw new RestException("Version com código "+this.versionId+" não encontrado.");
			}
			
		}
		
		return this.version;
		
	}
	
	@Override
	protected List<Filter> getFilters() throws RestException {
		
		List<Filter> filters = new ArrayList<>();
		filters.add(new Filter("version", this.getVersion()));
		
		return filters;
		
	}
	
	@Override
	protected Build afterGet(Build model) throws RestException {
		if (model!=null && !this.getVersion().getId().equals(model.getVersion().getId())) {
			return null;
		}
		return model;
	}
	
	@Override
	protected void beforePost(Build model) throws RestException {
		model.setVersion(this.getVersion());
	}
	
	@Override
	protected void beforePut(Build model) throws RestException {
		model.setVersion(this.getVersion());
	}
	
	@Override
	protected void beforeRemove(Build model) throws RestException {
		if (model!=null && !this.getVersion().getId().equals(model.getVersion().getId())) {
			throw new RestException("Esta Build não pertence a Version "+this.getVersion().getId()+".");
		}
	}
	
	@GET
	@Path("{id}/parts")
	public List<Integer> parts() {
		
		return null;
		
	}
	
}
