package br.com.devtools.apidevtools.resource.component.rules;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rulemanager.rules.Rule;
import br.com.devtools.apidevtools.resource.component.Component;

public class ComponentRuleFileName implements Rule<Component> {

	@Override
	public void validate(Controller<Component> controller, Component model) throws RestException{
		
		if (model.getFileName()==null) {
			throw new RestException("Nome do Arquivo: campo obrigatório");
		}
		
		model.setName(model.getName().trim());
		
		if (model.getFileName().length()<=3) {
			throw new RestException("Nome do Arquivo: tamanho deve ser maior que três");
		}
		
		if (model.getFileName().length()>100) {
			throw new RestException("Nome do Arquivo: tamanho deve ser menor que cem");
		}

		if (!model.getFileName().matches("^[a-zA-Z0-9]+\\.[a-zA-Z0-9]{1,50}$")) {
			throw new RestException("Nome do Arquivo: formatação inválida. Ex.: Teste.txt");
		}
		
	}


}
