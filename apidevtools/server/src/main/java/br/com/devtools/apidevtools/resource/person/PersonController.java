package br.com.devtools.apidevtools.resource.person;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.crypto.Crypto;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.resource.person.acess.artifact.Acess;
import br.com.devtools.apidevtools.resource.person.acess.artifact.AcessStatus;

@Path("person")
public class PersonController extends Controller<Person>{

	@Override
	public Class<Person> getClasse() {
		return Person.class;
	}
	
	@POST
	@Path("{id}/acess")
	public void createAcess(@PathParam("id") Long id, Acess acess) throws RestException {
		try {
			
			Crypto crypto = new Crypto();
			LocalDateTime now = LocalDateTime.now();
			Person person = this.get(id);
			
			TypedQuery<Acess> query = this.getSessao().getEm().createQuery(
					" select a from Acess a " +
					" where a.person = :person " +
					" and a.status = :status ", Acess.class);
			query.setParameter("person", person);
			query.setParameter("status", AcessStatus.ACTIVE);
			
			List<Acess> list = query.getResultList();
			list.forEach(old -> {
				old.setStatus(AcessStatus.INACTIVE);
				old.setDeath(now);
				this.getSessao().getEm().merge(old);
			});
			
			acess.setPerson(person);
			acess.setCreation(now);
			acess.setStatus(AcessStatus.ACTIVE);
			acess.setHash(crypto.criptografar(acess.getName())+crypto.criptografar(acess.getPassword()));
			
			this.getSessao().getEm().persist(acess);
			
			this.getSessao().commit();
			
		} catch (Exception e) {
			throw new RestException(e);
		}
	}

}
