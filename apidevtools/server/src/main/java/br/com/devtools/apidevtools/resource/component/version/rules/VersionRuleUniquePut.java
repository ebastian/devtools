package br.com.devtools.apidevtools.resource.component.version.rules;

import java.util.List;

import javax.persistence.TypedQuery;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePut;
import br.com.devtools.apidevtools.resource.component.version.Version;
import br.com.devtools.apidevtools.resource.component.version.VersionController;
import br.com.devtools.apidevtools.resource.component.version.rules.exception.VersionUniqueException;

public class VersionRuleUniquePut implements RulePut<Version> {

	@Override
	public void validate(Controller<Version> controller, Version model) throws RestException{
		
		try {
			
			VersionController vController = (VersionController) controller;
			TypedQuery<Version> query = vController.getEm().createQuery(
					" from Version v "
					+ " where v.id <> :id "
					+ " and v.component.id = :componentId"
					+ " and v.major = :major "
					+ " and v.minor = :minor "
					+ " and v.release = :release ", Version.class);
			
			query.setParameter("id", model.getId());
			query.setParameter("componentId", vController.getComponent().getId());
			query.setParameter("major", model.getMajor());
			query.setParameter("minor", model.getMinor());
			query.setParameter("release", model.getRelease());
			
			List<Version> resultList = query.getResultList();
			if (resultList!=null && resultList.size()>0) {
				throw new VersionUniqueException();
			}
			
		} catch (RestException e) {
			throw e;
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}

}