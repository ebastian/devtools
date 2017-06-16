package br.com.devtools.apidevtools.resource.component.version.rules;

import java.time.LocalDateTime;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePost;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePut;
import br.com.devtools.apidevtools.resource.component.version.Version;
import br.com.devtools.apidevtools.resource.component.version.rules.exception.creation.VersionCreationGreaterNowException;
import br.com.devtools.apidevtools.resource.component.version.rules.exception.creation.VersionCreationRequiredException;

public class VersionRuleCreation implements RulePost<Version>, RulePut<Version> {

	@Override
	public void validate(Controller<Version> controller, Version model) throws RestException{
		
		if (model.getCreation()==null) {
			throw new VersionCreationRequiredException();
		}
		
		if (LocalDateTime.now().isBefore(model.getCreation())) {
			throw new VersionCreationGreaterNowException();
		}
		
	}


}
