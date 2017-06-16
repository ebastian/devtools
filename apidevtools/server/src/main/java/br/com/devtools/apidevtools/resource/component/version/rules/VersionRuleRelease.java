package br.com.devtools.apidevtools.resource.component.version.rules;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePost;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePut;
import br.com.devtools.apidevtools.resource.component.version.Version;
import br.com.devtools.apidevtools.resource.component.version.rules.exception.release.VersionReleaseInvalidException;
import br.com.devtools.apidevtools.resource.component.version.rules.exception.release.VersionReleaseRequiredException;

public class VersionRuleRelease implements RulePost<Version>, RulePut<Version> {

	@Override
	public void validate(Controller<Version> controller, Version model) throws RestException{
		
		if (model.getRelease()==null) {
			throw new VersionReleaseRequiredException();
		}
		
		if (model.getRelease()<0) {
			throw new VersionReleaseInvalidException();
		}
		
	}

}