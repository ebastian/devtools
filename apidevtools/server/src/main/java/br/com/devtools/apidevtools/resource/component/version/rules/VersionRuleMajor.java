package br.com.devtools.apidevtools.resource.component.version.rules;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePost;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePut;
import br.com.devtools.apidevtools.resource.component.version.Version;
import br.com.devtools.apidevtools.resource.component.version.rules.exception.major.VersionMajorInvalidException;
import br.com.devtools.apidevtools.resource.component.version.rules.exception.major.VersionMajorRequiredException;

public class VersionRuleMajor implements RulePost<Version>, RulePut<Version> {

	@Override
	public void validate(Controller<Version> controller, Version model) throws RestException{
		
		if (model.getMajor()==null) {
			throw new VersionMajorRequiredException();
		}
		
		if (model.getMajor()<0) {
			throw new VersionMajorInvalidException();
		}
		
	}


}
