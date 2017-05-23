package br.com.devtools.apidevtools.core.rest;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;

import br.com.devtools.apidevtools.core.database.EntityManagerUtil;

@RequestScoped
public class RestSessao {
	
	private EntityManager em = null;

	public EntityManager getEm() {
		if (this.em==null) {
			this.em = EntityManagerUtil.getEntityManager();
			this.em.getTransaction().begin();
		}
		return em;
	}
	
	public void commit() {
		if (this.em!=null) {
			this.em.getTransaction().commit();
			this.close();
		}
	}
	
	public void rollback() {
		if (this.em!=null) {
			this.em.getTransaction().rollback();
			this.close();
		}
	}
	
	public void close() {
		if (this.em!=null) {
			this.em.close();
			this.em = null;
		}
	}
	
}