package br.com.devtools.apidevtools.core.rest;

import java.sql.Connection;
import java.sql.SQLException;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import br.com.devtools.apidevtools.core.database.EntityManagerUtil;

@RequestScoped
public class RestSessao {

	private EntityManager em = null;

	public EntityManager getEm() {
		if (this.em == null) {
			this.em = EntityManagerUtil.getEntityManager();
			this.em.getTransaction().begin();
		}
		return em;
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
		
        Session session = this.getEm().unwrap(Session.class);
        MyWork myWork = new MyWork();
        session.doWork(myWork);
        return myWork.getConnection();
        
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