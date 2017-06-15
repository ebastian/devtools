package br.com.devtools.apidevtools.core.rest;

import java.sql.Connection;
import java.sql.SQLException;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;

import org.hibernate.jdbc.Work;

import br.com.devtools.apidevtools.core.database.EntityManagerUtil;
import br.com.devtools.apidevtools.core.database.IEntityMangerUtil;
import br.com.devtools.apidevtools.resource.person.acess.artifact.Session;

@RequestScoped
public class RestSessao {
	
	private EntityManager em = null;
	private Session session;
	private String nameAcess;
	private String password;
	
	private IEntityMangerUtil entityMangerUtil;
	
	public EntityManager getEm() {
		if (this.em == null) {
			this.em = this.getEntityMangerUtil().getEntityManager();
			this.em.getTransaction().begin();
		}
		return em;
	}
	
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void commit() {
		if (this.em != null) {
			this.em.getTransaction().commit();
			this.close();
		}
	}

	public void rollback() {
		if (this.em != null) {
			this.em.getTransaction().rollback();
			this.close();
		}
	}

	public void close() {
		if (this.em != null) {
			this.em.close();
			this.em = null;
		}
	}

	public Connection getConnection() {
		
		org.hibernate.Session session = this.getEm().unwrap(org.hibernate.Session.class);
        MyWork myWork = new MyWork();
        session.doWork(myWork);
        return myWork.getConnection();
        
	}

	public String getNameAcess() {
		return nameAcess;
	}

	public void setNameAcess(String nameAcess) {
		this.nameAcess = nameAcess;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public IEntityMangerUtil getEntityMangerUtil() {
		if (this.entityMangerUtil==null) {
			this.entityMangerUtil = new EntityManagerUtil();
		}
		return entityMangerUtil;
	}

	public void setEntityMangerUtil(IEntityMangerUtil entityMangerUtil) {
		this.entityMangerUtil = entityMangerUtil;
	}

}

class MyWork implements Work {

    Connection conn;

    @Override
    public void execute(Connection arg0) throws SQLException {
        this.conn = arg0;
    }

    Connection getConnection() {
        return conn;
    }

}