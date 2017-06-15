package br.com.devtools.apidevtools.resource.component.rules;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePost;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePut;
import br.com.devtools.apidevtools.resource.component.Component;
import br.com.devtools.apidevtools.resource.component.rules.exception.name.ComponentNameLargerException;
import br.com.devtools.apidevtools.resource.component.rules.exception.name.ComponentNameLessException;
import br.com.devtools.apidevtools.resource.component.rules.exception.name.ComponentNameRequiredException;

public class ComponentRuleName implements RulePost<Component>, RulePut<Component> {

	@Override
	public void validate(Controller<Component> controller, Component model) throws RestException{
		
		if (model.getName()==null) {
			throw new ComponentNameRequiredException();
		}
		
		model.setName(model.getName().trim());
		
		if (model.getName().length()<=3) {
			throw new ComponentNameLessException();
		}
		
		if (model.getName().length()>100) {
			throw new ComponentNameLargerException();
		}

	}


}
