package br.com.devtools.apidevtools.resource;

import java.time.LocalDateTime;

import javax.enterprise.inject.spi.CDI;

import org.hibernate.envers.RevisionListener;

import br.com.devtools.apidevtools.core.rest.RestSessao;

public class UserRevisionListener implements RevisionListener {
	
	@Override
	public void newRevision(Object revisionEntity) {
		
		ZaudAcessRev exampleRevEntity = (ZaudAcessRev) revisionEntity;
		exampleRevEntity.setPersonId(0l);
		try {
			RestSessao sessao = CDI.current().select(RestSessao.class).get();
			exampleRevEntity.setPersonId(sessao.getSession().getAcess().getPerson().getId());
			exampleRevEntity.setAcessId(sessao.getSession().getAcess().getId());
			exampleRevEntity.setIp(sessao.getSession().getIp());
			exampleRevEntity.setDate(LocalDateTime.now());
		} catch (Exception e) {
		}
		
	}
	
}