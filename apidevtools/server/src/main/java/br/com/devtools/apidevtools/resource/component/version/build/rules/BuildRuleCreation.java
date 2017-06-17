package br.com.devtools.apidevtools.resource.component.version.build.rules;

import java.time.LocalDateTime;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePost;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePut;
import br.com.devtools.apidevtools.resource.component.version.build.Build;
import br.com.devtools.apidevtools.resource.component.version.build.rules.exceptions.creation.BuildCreationGreaterNowException;
import br.com.devtools.apidevtools.resource.component.version.build.rules.exceptions.creation.BuildCreationRequiredException;

public class BuildRuleCreation implements RulePost<Build>, RulePut<Build> {

	@Override
	public void validate(Controller<Build> controller, Build model) throws RestException{
		
		if (model.getCreation()==null) {
			throw new BuildCreationRequiredException();
		}
		
		if (LocalDateTime.now().isBefore(model.getCreation())) {
			throw new BuildCreationGreaterNowException();
		}
		
	}

}