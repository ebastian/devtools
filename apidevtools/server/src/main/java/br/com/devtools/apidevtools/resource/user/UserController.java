package br.com.devtools.apidevtools.resource.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.crypto.Crypto;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.resource.user.acess.Acess;
import br.com.devtools.apidevtools.resource.user.acess.AcessStatus;

@Path("user")
public class UserController extends Controller<User>{

	@Override
	protected Class<User> getClasse() {
		return User.class;
	}
	
	@POST
	@Path("{id}/acess")
	public void post(@PathParam("id") Long id, Acess acess) throws RestException {
		try {
			
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
				old.setStatus(AcessStatus.INATIVE);
				old.setDeath(now);
				this.getSessao().getEm().merge(old);
			});
			
			acess.setUser(user);
			acess.setCreation(now);
			acess.setStatus(AcessStatus.ACTIVE);
			acess.setHash(
				new Crypto().criptografar(acess.getName()+";"+acess.getDeath().toString()) +
				new Crypto().criptografar(acess.getPassword()+";"+acess.getDeath().toString())
			);
			
			this.getSessao().getEm().persist(acess);
			
			this.getSessao().commit();
			
		} catch (Exception e) {
			throw new RestException(e);
		}
	}

}
