package br.com.devtools.apidevtools.resource.person.acess;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
import br.com.devtools.apidevtools.resource.person.acess.artifact.Session;
import br.com.devtools.apidevtools.resource.person.rules.UserToken;

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
	@Produces(MediaType.TEXT_HTML+";charset=UTF-8")
	public String help() {
		
		String description = 
				"Utilizar NomeDeAcesso e Senha concatenado com um ponto e virgula(;) e convertido para Base64:<br>" +
				"Ex.:admin;admin <br> convertendo para Base64 fica \"YWRtaW47YWRtaW4=\"<br><br>"+
				"Enviar-lo como Header da requisição com o nome user-token.<br><br>" +
				"O Método ira devolver o session-token.<br>" +
				"Ambos os dados devem ser enviados em todos os métodos como Header:<br>" +
				"Ex.:<br>" +
				"user-token : YWRtaW47YWRtaW4=<br>"+
				"session-token : e23ece7940281f4c79d81c1568ee713cd263731850948a9e90d3e5943c79690c8663c442639a514b9341523b834ffe383ad5eb8aeecb22b87e707cb9f1b5f155";
		
		return new HelpGenerator().help(this.getClass(), Acess.class, description);
	}

	@POST
	@Path("login")
	public String login(@HeaderParam("user-token") String token) throws RestException {
		
		try {
			
			Crypto crypto = new Crypto();
			String hash = new UserToken().userCrypto(this.getSessao());
			
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
			this.getSessao().commit();
			
			return session.getHash();
			
		} catch (Exception e) {
			throw new RestException("Nome ou Senha inválidos");
		}
		
	}
	
	private void errorLogin() throws RestException {
		throw new RestException("Nome ou Senha inválidos");
	}

}