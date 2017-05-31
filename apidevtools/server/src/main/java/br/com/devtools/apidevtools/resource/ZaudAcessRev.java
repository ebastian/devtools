package br.com.devtools.apidevtools.resource;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import br.com.devtools.apidevtools.core.rest.RestSessao;

@Entity
@RevisionEntity(UserRevisionListener.class)
public class ZaudAcessRev extends DefaultRevisionEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Transient
	@Inject
	RestSessao sessao;
	
	private Long acessId;

	public Long getAcessId() {
		return acessId;
	}

	public void setAcessId(Long acessId) {
		this.acessId = acessId;
	}
	
}

