package br.com.devtools.apidevtools.resource.component.rules;

import java.time.LocalDateTime;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePost;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePut;
import br.com.devtools.apidevtools.resource.component.Component;
import br.com.devtools.apidevtools.resource.component.rules.exception.creation.ComponentCreationGreaterNowException;
import br.com.devtools.apidevtools.resource.component.rules.exception.creation.ComponentCreationRequiredException;

public class ComponentRuleCreation implements RulePost<Component>, RulePut<Component> {

	@Override
	public void validate(Controller<Component> controller, Component model) throws RestException{
		
		if (model.getCreation()==null) {
			throw new ComponentCreationRequiredException();
		}
		
		if (LocalDateTime.now().isBefore(model.getCreation())) {
			throw new ComponentCreationGreaterNowException();
		}
		
	}


}
