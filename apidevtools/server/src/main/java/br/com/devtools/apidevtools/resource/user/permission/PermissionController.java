package br.com.devtools.apidevtools.resource.user.permission;

import static br.com.devtools.apidevtools.core.permission.PermissionMethod.AUDIT;
import static br.com.devtools.apidevtools.core.permission.PermissionMethod.DELETE;
import static br.com.devtools.apidevtools.core.permission.PermissionMethod.GET;
import static br.com.devtools.apidevtools.core.permission.PermissionMethod.PUT;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.permission.PermissionClass;
import br.com.devtools.apidevtools.core.permission.PermissionMethod;
import br.com.devtools.apidevtools.core.rest.RestException;

@Path("permission")
@PermissionClass(description="Grupo de Permissões")
public class PermissionController extends Controller<PermissionGroup> {

	@Override
	public Class<PermissionGroup> getClasse() {
		return PermissionGroup.class;
	}

	@GET
	@Path("permissions")
	@PermissionMethod(types={AUDIT, DELETE, PUT, GET}, description="Busca Todas as Permissões")
	public List<Permission> permissions() throws RestException {
		
		try {
			return new PermissionBuild().getPermission();
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}
	
	@Override
	protected PermissionGroup afterGet(PermissionGroup model) throws RestException {
		return super.afterGet(model);
	}
	
	@Override
	protected PermissionGroup afterPost(PermissionGroup group) throws RestException {
		
		for (Permission p : group.getPermissions()) {
			p.setGroup(group);
			if (p.getCheck()!=null && p.getCheck()) {
				this.getEm().persist(p);
			}
		}
		
		return super.afterPost(group);
	}
	
	@Override
	protected void beforePut(PermissionGroup group) throws RestException {
		
		for (Permission p : group.getPermissions()) {
			if (p.getCheck()!=null && p.getCheck()) {
				if (p.getId()==null) {
					p.setGroup(group);
					this.getEm().persist(p);
				} else {
					p.setGroup(group);
					this.getEm().merge(p);
				}
			} else if (p.getId()!=null) {
				this.getEm().remove(p);
			}
		}
		
	}
	
	@Override
	protected void beforeRemove(PermissionGroup group) throws RestException {
		for (Permission p : group.getPermissions()) {
			if (p.getId()!=null) {
				this.getEm().remove(p);
			}
		}
		super.beforeRemove(group);
	}
	
}
