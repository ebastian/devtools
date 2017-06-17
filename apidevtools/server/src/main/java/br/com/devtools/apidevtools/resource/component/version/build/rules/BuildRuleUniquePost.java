package br.com.devtools.apidevtools.resource.component.version.build.rules;

import java.util.List;

import javax.persistence.TypedQuery;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePost;
import br.com.devtools.apidevtools.resource.component.version.build.Build;
import br.com.devtools.apidevtools.resource.component.version.build.BuildController;
import br.com.devtools.apidevtools.resource.component.version.build.rules.exceptions.BuildUniqueException;

public class BuildRuleUniquePost implements RulePost<Build> {

	@Override
	public void validate(Controller<Build> controller, Build model) throws RestException{
		
		try {
			
			BuildController bController = (BuildController) controller;
			TypedQuery<Build> query = bController.getEm().createQuery(
					" from Build b "
					+ " where b.version.id = :versionId "
					+ " and b.build = :build ", Build.class);
			
			query.setParameter("versionId", bController.getVersionId());
			query.setParameter("build", model.getBuild());
			
			List<Build> resultList = query.getResultList();
			if (resultList!=null && resultList.size()>0) {
				throw new BuildUniqueException();
			}
			
		} catch (RestException e) {
			throw e;
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}

}