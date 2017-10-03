package br.com.devtools.apidevtools.resource.user.acess;

import static br.com.devtools.apidevtools.core.permission.PermissionMethod.ALL;

import java.util.Base64;

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
import br.com.devtools.apidevtools.core.permission.PermissionClass;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rest.RestSessao;
import br.com.devtools.apidevtools.resource.user.acess.artifact.Acess;
import br.com.devtools.apidevtools.resource.user.acess.artifact.AcessStatus;
import br.com.devtools.apidevtools.resource.user.acess.artifact.Login;
import br.com.devtools.apidevtools.resource.user.acess.artifact.LoginToken;
import br.com.devtools.apidevtools.resource.user.acess.artifact.Session;
import br.com.devtools.apidevtools.resource.user.rules.UserToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="Controle de Acesso", position=1)
@Path("acess")
@Produces("application/json;charset=UTF-8")
@Consumes("application/json;charset=UTF-8")
@PermissionClass(description="Controle de Acesso", allMethods=ALL)
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
				"Este devem ser enviado em todos os métodos como Header:<br>" +
				"Ex.:<br>" +
				//"user-token : YWRtaW47YWRtaW4=<br>"+
				"session-token : e23ece7940281f4c79d81c1568ee713cd263731850948a9e90d3e5943c79690c8663c442639a514b9341523b834ffe383ad5eb8aeecb22b87e707cb9f1b5f155";
		
		return new HelpGenerator().help(this.getClass(), Acess.class, description);
	}

	@POST
	@Path("loginbyuser")
	@ApiOperation(
			value="Login por usuário e senha",
			notes=	"Utilizar NomeDeAcesso e Senha concatenado <br>" +
					"Ex.:\n{\n" + 
					"\"login\": \"admin\",\n" + 
					"\"password\": \"admin\"\n" + 
					"} <br><br>"+
					"O Método ira devolver o session-token.<br>" +
					"Este deve ser enviado em todos os métodos como Header:<br>" +
					"Ex.:<br>" +
					"session-token : e23ece7940281f4c79d81c1568ee713cd263731850948a9e90d3e5943c79690c8663c442639a514b9341523b834ffe383ad5eb8aeecb22b87e707cb9f1b5f155")
	public LoginToken login(Login login) throws RestException {
		
		try {
			String token = Base64.getEncoder().encodeToString((login.getLogin()+";"+login.getPassword()).getBytes());
			return this.login(token);
		} catch (Exception e) {
			throw new RestException(e);
		}
	}
	
	@POST
	@Path("login")
	@ApiOperation(
			value="Login por Token",
			notes=	"Utilizar NomeDeAcesso e Senha concatenado com um ponto e virgula(;) e convertido para Base64:<br>" +
					"Ex.:admin;admin <br> convertendo para Base64 fica \"YWRtaW47YWRtaW4=\"<br><br>"+
					"Enviar-lo como Header da requisição com o nome user-token.<br><br>" +
					"O Método ira devolver o session-token.<br>" +
					"Ambos os dados devem ser enviados em todos os métodos como Header:<br>" +
					"Ex.:<br>" +
					"user-token : YWRtaW47YWRtaW4=<br>"+
					"session-token : e23ece7940281f4c79d81c1568ee713cd263731850948a9e90d3e5943c79690c8663c442639a514b9341523b834ffe383ad5eb8aeecb22b87e707cb9f1b5f155")
	public LoginToken login(@HeaderParam("user-token") String token) throws RestException {
		
		try {
			
			UserToken userToken = new UserToken();
			
			Crypto crypto = new Crypto();
			userToken.split(token, this.getSessao());
			
			String hash = userToken.userCrypto(this.getSessao());
			
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
			
			return new LoginToken(session.getHash());
			
		} catch (Exception e) {
			throw new RestException("Nome ou Senha inválidos");
		}
		
	}
	
	private void errorLogin() throws RestException {
		throw new RestException("Nome ou Senha inválidos");
	}

}
