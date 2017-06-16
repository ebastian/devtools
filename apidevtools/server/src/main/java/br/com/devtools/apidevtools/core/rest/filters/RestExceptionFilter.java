package br.com.devtools.apidevtools.core.rest.filters;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.devtools.apidevtools.core.rest.RestException;

@Provider
public class RestExceptionFilter implements ExceptionMapper<RestException> {
	
	@Override
	public Response toResponse(RestException e) {
		
		//System.out.println("ExceptionFilter");
		e.printStackTrace();
		return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		
	}
	
}