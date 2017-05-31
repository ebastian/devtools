package br.com.devtools.apidevtools.resource;

import javax.inject.Inject;

import org.hibernate.envers.RevisionListener;

import br.com.devtools.apidevtools.core.rest.RestSessao;
import br.com.devtools.apidevtools.core.rest.filters.RequestFilter;

public class UserRevisionListener implements RevisionListener {
	
	public final static String USERNAME = "Suay";
	
	@Inject
	RestSessao sessao;

	@Override
	public void newRevision(Object revisionEntity) {
		
		ZaudAcessRev exampleRevEntity = (ZaudAcessRev) revisionEntity;
		if (RequestFilter.sessao!=null && RequestFilter.sessao.getSession()!=null) {
			exampleRevEntity.setAcessId(sessao.getSession().getAcess().getId());
		} else {
			exampleRevEntity.setAcessId(0l);
		}
		
	}
	
}