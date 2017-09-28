package br.com.devtools.apidevtools.resource.user.permission;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.controller.Filter;
import br.com.devtools.apidevtools.core.permission.PermissionClass;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.resource.user.User;
import br.com.devtools.apidevtools.resource.user.UserController;

@Path("user/{userId}/permission")
@PermissionClass(description="Permissões")
public class PermissionController extends Controller<Permission> {

	@PathParam("userId") Long userId;
	
	private User user = null;
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public User getUser() throws RestException {
		
		if (this.user==null) {
			
			UserController userController = new UserController();
			userController.setSessao(this.getSessao());
			userController.setContext(this.getContext());
			this.user = userController.get(this.userId);
			
			if (this.userId==null) {
				throw new RestException("Usuário com código "+this.userId+" não encontrado.");
			}
			
		}
		
		return this.user;
		
	}
	
	@Override
	public Class<Permission> getClasse() {
		return Permission.class;
	}
	
	@Override
	protected void beforePost(Permission model) throws RestException {
		model.setUser(this.getUser());
	}
	
	@Override
	protected void beforePut(Permission model) throws RestException {
		model.setUser(this.getUser());
	}
	
	@Override
	protected List<Filter> getFilters() throws RestException {
		
		List<Filter> filters = new ArrayList<>();
		filters.add(new Filter("user", this.getUser()));
		
		return filters;
		
	}
	
	@Override
	protected Permission afterGet(Permission model) throws RestException {
		if (model!=null && !this.getUser().getId().equals(model.getUser().getId())) {
			return null;
		}
		return model;
	}
	
}
