package br.com.devtools.apidevtools.resource.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.crypto.Crypto;
import br.com.devtools.apidevtools.core.permission.PermissionClass;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.resource.user.acess.artifact.Acess;
import br.com.devtools.apidevtools.resource.user.acess.artifact.AcessStatus;
import br.com.devtools.apidevtools.resource.user.privilege.Privilege;

@Path("user")
@PermissionClass(description="Usuário")
public class UserController extends Controller<User>{

	@Override
	public Class<User> getClasse() {
		return User.class;
	}
	
	@POST
	@Path("{id}/acess")
	public void createAcess(@PathParam("id") Long id, Acess acess) throws RestException {
		try {
			
			Crypto crypto = new Crypto();
			LocalDateTime now = LocalDateTime.now();
			User user = this.get(id);
			
			TypedQuery<Acess> query = this.getSessao().getEm().createQuery(
					" select a from Acess a " +
					" where a.user = :user " +
					" and a.status = :status ", Acess.class);
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
			acess.setHash(crypto.criptografar(acess.getName())+crypto.criptografar(acess.getPassword()));
			
			this.getSessao().getEm().persist(acess);
			
			this.getSessao().commit();
			
		} catch (Exception e) {
			throw new RestException(e);
		}
	}
	
	@POST
	@Path("{id}/privilege")
	public void createPrivilege(@PathParam("id") Long id, Privilege p) throws RestException {
		
		try {
			
			Privilege privilege = null;
			
			try {
				privilege = this.getEm().find(Privilege.class, id);
			} catch (NoResultException e) {
			}
			
			if (privilege==null) {
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

}
