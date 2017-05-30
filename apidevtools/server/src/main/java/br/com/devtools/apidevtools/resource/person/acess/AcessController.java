package br.com.devtools.apidevtools.resource.person.acess;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.devtools.apidevtools.core.crypto.Crypto;
import br.com.devtools.apidevtools.core.help.HelpGenerator;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rest.RestSessao;
import br.com.devtools.apidevtools.resource.person.acess.artifact.Acess;
import br.com.devtools.apidevtools.resource.person.acess.artifact.AcessStatus;
import br.com.devtools.apidevtools.resource.person.acess.artifact.Login;
import br.com.devtools.apidevtools.resource.person.acess.artifact.Session;

@Path("acess")
@Produces("application/json;charset=UTF-8")
@Consumes("application/json;charset=UTF-8")
public class AcessController {
	
	@Inject
	private
	RestSessao sessao;
	
	public RestSessao getSessao() {
		return sessao;
	}

	public void setSessao(RestSessao sessao) {
		this.sessao = sessao;
	}
	
	@GET
	@Path("help")
	@Produces(MediaType.TEXT_HTML)
	public String help() {
		return new HelpGenerator().help(this.getClass(), Acess.class);
	}

	@POST
	@Path("login")
	public String login(Login login) throws RestException {
		
		try {
			
			/*
			System.out.println("Session: " + sessao.getSession());
			if (sessao.getSession()!=null) {
				System.out.println("getCreation: "+sessao.getSession().getCreation());
				System.out.println("getUseragent: "+sessao.getSession().getUseragent());
				System.out.println("getIp: "+sessao.getSession().getIp());
			}
			*/
			
			Crypto crypto = new Crypto();
			String hash = crypto.criptografar(login.getLogin() + login.getPassword()) + crypto.criptografar(login.getPassword() + login.getLogin());
			
			TypedQuery<Acess> query = this.getSessao().getEm().createQuery(
					" select a from Acess a " +
					" where a.hash = :hash " +
					" and a.status = :status ", Acess.class);
			query.setParameter("hash", hash);
			query.setParameter("status", AcessStatus.ACTIVE);
			
			Acess acess = query.getSingleResult();
			if (acess==null) {
				this.errorLogin();
			}
			
			Session session = getSessao().getSession();
			session.setHash(
				crypto.criptografar(session.getId()+";"+session.getUseragent()+";"+session.getCreation().toString())+
				crypto.criptografar(session.getId()+";"+session.getIp()+";"+session.getCreation().toString())
			);
			
			session.setAcess(acess);
			
			this.getSessao().getEm().persist(session);
			
			return session.getHash();
			
		} catch (Exception e) {
			throw new RestException("Nome ou Senha inválidos");
		}
		
	}
	
	private void errorLogin() throws RestException {
		throw new RestException("Nome ou Senha inválidos");
	}

}
