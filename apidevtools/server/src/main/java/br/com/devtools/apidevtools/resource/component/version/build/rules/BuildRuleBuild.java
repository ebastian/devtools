package br.com.devtools.apidevtools.resource.component.version.build.rules;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePost;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePut;
import br.com.devtools.apidevtools.resource.component.version.build.Build;
import br.com.devtools.apidevtools.resource.component.version.build.rules.exceptions.build.BuildBuildInvalidException;
import br.com.devtools.apidevtools.resource.component.version.build.rules.exceptions.build.BuildBuildRequiredException;

public class BuildRuleBuild implements RulePost<Build>, RulePut<Build> {

	@Override
	public void validate(Controller<Build> controller, Build model) throws RestException{
		
		if (model.getBuild()==null) {
			throw new BuildBuildRequiredException();
		}
		
		if (model.getBuild()<0) {
			throw new BuildBuildInvalidException();
		}
		
	}

}