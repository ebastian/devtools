package br.com.devtools.apidevtools.core.controller;

import java.lang.reflect.Field;

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

import br.com.devtools.apidevtools.core.database.EntityManagerUtil;

@Produces("application/json;charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON)
public abstract class Controller<Model> {
	
	protected abstract Class<Model> getClasse();
	
	@Context
	ServletContext context;
	
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
	public Model get(@PathParam("id") Long id) {
		
		EntityManager em = null;
		
		try {
			
			em = EntityManagerUtil.getEntityManager();
			Model obj = em.find(this.getClasse(), id);
			em.close();
			
			return obj;
			
		} catch (Exception e) {
			if (em!=null) {
				em.close();
			}
			throw e;
		}
		
		
	}
	
	@POST
	public Model post(Model obj) {
		
		EntityManager em = null;
		
		try {
			
			em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(obj);
			em.getTransaction().commit();
			em.close();
			
		} catch (Exception e) {
			if (em!=null) {
				em.close();
			}
			throw e;
		}
		
		return obj;
		
	}
	
	@PUT
	@Path("{id}")
	public Model put(@PathParam("id") Long id, Model obj) throws Exception {
		
		EntityManager em = null;
		
		try {
			
			em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			this.setId(obj, id);
			em.merge(obj);
			em.getTransaction().commit();
			em.close();
			
		} catch (Exception e) {
			if (em!=null) {
				em.close();
			}
			throw e;
		}
		
		return obj;
		
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Long id) {
		
		EntityManager em = null;
		
		try {
			
			em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			Model obj = em.find(this.getClasse(), id);
			em.remove(obj);
			em.getTransaction().commit();
			em.close();
			
			return Response.ok().build();
			
		} catch (Exception e) {
			if (em!=null) {
				em.close();
			}
			throw e;
		}
		
	}
	
}
