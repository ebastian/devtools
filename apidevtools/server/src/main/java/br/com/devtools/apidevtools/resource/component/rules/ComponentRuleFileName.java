package br.com.devtools.apidevtools.resource.component.rules;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePost;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePut;
import br.com.devtools.apidevtools.resource.component.Component;
import br.com.devtools.apidevtools.resource.component.rules.exception.filename.ComponentFileNameInvalidException;
import br.com.devtools.apidevtools.resource.component.rules.exception.filename.ComponentFileNameLargerException;
import br.com.devtools.apidevtools.resource.component.rules.exception.filename.ComponentFileNameLessException;
import br.com.devtools.apidevtools.resource.component.rules.exception.filename.ComponentFileNameRequiredException;

public class ComponentRuleFileName implements RulePost<Component>, RulePut<Component> {

	@Override
	public void validate(Controller<Component> controller, Component model) throws RestException {
		
		if (model.getFileName()==null) {
			throw new ComponentFileNameRequiredException();
		}
		
		model.setFileName(model.getFileName().trim());
		
		if (model.getFileName().length()<=3) {
			throw new ComponentFileNameLessException();
		}
		
		if (model.getFileName().length()>100) {
			throw new ComponentFileNameLargerException();
		}

		if (!model.getFileName().matches("^[a-zA-Z0-9]+\\.[a-zA-Z0-9]{1,50}$")) {
			throw new ComponentFileNameInvalidException();
		}
		
	}


}
