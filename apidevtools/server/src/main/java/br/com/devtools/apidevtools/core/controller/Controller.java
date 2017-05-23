package br.com.devtools.apidevtools.core.controller;

import java.lang.reflect.Field;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rest.RestSessao;

@Produces("application/json;charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON)
public abstract class Controller<Model> {
	
	protected abstract Class<Model> getClasse();
	
	@Context
	ServletContext context;
	
	@Inject
	RestSessao sessao;
	
	private EntityManager getEm() {
		return this.sessao.getEm();
	}
	
	private void setId(Model entidade, Long id) throws Exception {
		
		Field[] campos = getClasse().getDeclaredFields();
		for (Field field : campos) {
			
			Id pk = field.getAnnotation(Id.class);
			if (pk!=null) {
				field.setAccessible(true);
				field.set(entidade, id);
			}
			
		}
		
	}
	
	@GET
	@Path("{id}")
	public Model get(@PathParam("id") Long id) throws RestException {
		
		try {
			
			Model obj = this.getEm().find(this.getClasse(), id);
			return obj;
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
		
	}
	
	@POST
	public Model post(Model obj) throws RestException {
		
		try {
			
			this.getEm().persist(obj);
			this.sessao.commit();
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
		return obj;
		
	}
	
	@PUT
	@Path("{id}")
	public Model put(@PathParam("id") Long id, Model obj) throws RestException {
		
		try {
			
			this.setId(obj, id);
			this.getEm().merge(obj);
			this.sessao.commit();
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
		return obj;
		
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Long id) throws RestException {
		
		try {
			
			Model obj = this.getEm().find(this.getClasse(), id);
			this.getEm().remove(obj);
			this.sessao.commit();
			
			return Response.ok().build();
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}
	
}
