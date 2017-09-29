package br.com.devtools.apidevtools.resource.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.crypto.Crypto;
import br.com.devtools.apidevtools.core.permission.PermissionClass;
import br.com.devtools.apidevtools.core.permission.PermissionMethod;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.resource.user.acess.artifact.Acess;
import br.com.devtools.apidevtools.resource.user.acess.artifact.AcessStatus;
import br.com.devtools.apidevtools.resource.user.permission.Permission;
import br.com.devtools.apidevtools.resource.user.permission.PermissionBuild;
import br.com.devtools.apidevtools.resource.user.privilege.Privilege;

@Path("user")
@PermissionClass(description = "Usuário")
public class UserController extends Controller<User> {

	private static final String ALTERAACESSO = "ALTERAACESSO";
	private static final String ALTERAPROVILEGIO = "ALTERAPROVILEGIO";
	private static final String ALTERAPERMISSAO = "ALTERAPERMISSAO";

	@Override
	public Class<User> getClasse() {
		return User.class;
	}

	@POST
	@Path("{id}/acess")
	@PermissionMethod(types = ALTERAACESSO, description = "Altera Acesso ao Sistema")
	public void createAcess(@PathParam("id") Long id, Acess acess) throws RestException {
		try {

			Crypto crypto = new Crypto();
			LocalDateTime now = LocalDateTime.now();
			User user = this.get(id);

			TypedQuery<Acess> query = this.getSessao().getEm().createQuery(
					" select a from Acess a " + " where a.user = :user " + " and a.status = :status ", Acess.class);
			query.setParameter("user", user);
			query.setParameter("status", AcessStatus.ACTIVE);

			List<Acess> list = query.getResultList();
			list.forEach(old -> {
				old.setStatus(AcessStatus.INACTIVE);
				old.setDeath(now);
				this.getSessao().getEm().merge(old);
			});

			acess.setUser(user);
			acess.setCreation(now);
			acess.setStatus(AcessStatus.ACTIVE);
			acess.setHash(crypto.criptografar(acess.getName()) + crypto.criptografar(acess.getPassword()));

			this.getSessao().getEm().persist(acess);

			this.getSessao().commit();

		} catch (Exception e) {
			throw new RestException(e);
		}
	}

	@POST
	@Path("{id}/privilege")
	@PermissionMethod(types = ALTERAPROVILEGIO, description = "Altera Acesso ao Sistema")
	public void createPrivilege(@PathParam("id") Long id, Privilege p) throws RestException {

		try {

			User user = this.get(id);
			Privilege privilege = null;

			try {
				
				TypedQuery<Privilege> query = this.getSessao().getEm().createQuery(
						" select a from Privilege a where a.user = :user ", Privilege.class);
				query.setParameter("user", user);
				
				privilege = query.getSingleResult();
				
			} catch (NoResultException e) {
			}

			if (privilege == null) {
				privilege = new Privilege();
				privilege.setId(id);
				privilege.setUser(this.get(id));
			}

			privilege.setType(p.getType());

			this.getSessao().getEm().persist(privilege);
			this.getSessao().commit();

		} catch (Exception e) {
			throw new RestException(e);
		}

	}

	@GET
	@Path("{id}/permission")
	@PermissionMethod(types = ALTERAPERMISSAO, description = "Busca Permissão ao Sistema")
	public List<Permission> getPermission(@PathParam("id") Long id) throws RestException {
		
		try {
			
			User user = this.get(id);
			
			List<Permission> permissions = new PermissionBuild().getPermission();
			//permission.setUser(user);
			
			for (Permission permission : permissions) {
				
				try {
					
					TypedQuery<Permission> query = this.getSessao().getEm().createQuery(
							" select p from Permission p "
							+ " where p.user = :user "
							+ " and p.className = :className "
							+ " and p.authorize = :authorize ",
							Permission.class);
					
					query.setParameter("user", user);
					query.setParameter("className", permission.getClassName());
					query.setParameter("authorize", permission.getAuthorize());
					
					Permission p = query.getSingleResult();
					
					if (p!=null) {
						permission.setId(p.getId());
						permission.setCheck(true);
					}
					
				} catch (NoResultException e) {
				}
				
			}
			
			return permissions;
			
		} catch (Exception e) {
			throw new RestException(e);
		}
	}

}
