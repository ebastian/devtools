package br.com.devtools.apidevtools.core.rest.filters;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import br.com.devtools.apidevtools.core.rest.RestSessao;
import br.com.devtools.apidevtools.resource.person.acess.artifact.Session;

@Provider
public class RequestFilter implements ContainerRequestFilter {

	@Inject
	RestSessao sessao;
	
	@Context
	HttpServletRequest context;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		Session session = new Session();
		session.setCreation(LocalDateTime.now());
		session.setIp(context.getRemoteHost()+"-"+context.getRemoteAddr());
		session.setUseragent(context.getHeader("User-Agent"));
		
		sessao.setSession(session);
		
		
		//System.out.println("RequestFilter.filter");
		
	}

}