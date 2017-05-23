package br.com.devtools.apidevtools.core.rest.filters;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import br.com.devtools.apidevtools.core.rest.RestSessao;

@Provider
public class ResponseFilter implements ContainerResponseFilter {

	@Inject
	RestSessao sessao;
	
	@Override
	public void filter(final ContainerRequestContext creq, final ContainerResponseContext cres)throws IOException {
		
		
		try {
			sessao.close();
		} catch (Exception e) {
		}
		
		cres.getHeaders().add("Access-Control-Allow-Origin", "*");
		cres.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept"); //authorization, user-token, session-token
		cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
		cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		cres.getHeaders().add("Access-Control-Max-Age", "1209600");
		
		System.out.println("ResponseFilter");
		
	}

}
