package br.com.devtools.apidevtools.core.rest.filters;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import br.com.devtools.apidevtools.core.rest.RestSessao;
import br.com.devtools.apidevtools.resource.person.acess.artifact.AcessStatus;
import br.com.devtools.apidevtools.resource.person.acess.artifact.Session;

@Provider
public class RequestFilter implements ContainerRequestFilter {

	@Inject
	RestSessao sessao;
	
	@Context
	HttpServletRequest context;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		try {
				
			String method = requestContext.getMethod();
			String path = requestContext.getUriInfo().getAbsolutePath().getPath();
			
			if (method.equalsIgnoreCase("GET") && (path.indexOf("/help")>=0 || path.indexOf("/hash/download/")>=0)) {
				return;
			}
			
			//String userToken = context.getHeader("user-token");
			
			Session session = new Session();
			session.setCreation(LocalDateTime.now());
			session.setIp(context.getRemoteHost()+"-"+context.getRemoteAddr());
			session.setUseragent(context.getHeader("User-Agent"));
			
			//UserToken token = new UserToken();
			//token.split(userToken, sessao);
			
			sessao.setSession(session);
			
			if (method.equalsIgnoreCase("POST") && path.indexOf("/login")>=0) {
				return;
			}
			
			String sessionToken = context.getHeader("session-token");
			
			TypedQuery<Session> query = sessao.getEm().createQuery(
					" select s from Session s " +
					" inner join s.acess a " +
					" where a.status = :status " +
					" and s.hash = :hash " +
					" and s.ip = :ip " +
					" and s.useragent = :useragent "
					, Session.class);
			
			try {
				query.setParameter("status", AcessStatus.ACTIVE);
				//query.setParameter("userhash", token.userCrypto(sessao));
				query.setParameter("hash", sessionToken);
				query.setParameter("ip", session.getIp());
				query.setParameter("useragent", session.getUseragent());
			} catch (Exception e) {
				throw new IOException(e);
			}
			
			Session s = query.getSingleResult();
			
			if (
				s==null ||
				!s.getUseragent().equals(session.getUseragent()) ||
				!s.getIp().equals(session.getIp())
			) {
				throw new IOException();
			}
			
			sessao.setSession(s);

		} catch (Exception e) {
			throw new IOException("Acesso negado");
		}
		
	}
	
}