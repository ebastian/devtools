package br.com.devtools.apidevtools.core.rulemanager.rules;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.rest.RestException;

public interface Rule<Model> {
	
	void validate(Controller<Model> controller, Model model) throws RestException;
	
}
