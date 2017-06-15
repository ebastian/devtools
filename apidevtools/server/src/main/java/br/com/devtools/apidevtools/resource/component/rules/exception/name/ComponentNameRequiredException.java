package br.com.devtools.apidevtools.resource.component.rules.exception.name;

import br.com.devtools.apidevtools.core.rest.RestException;

public class ComponentNameRequiredException extends RestException {

	private static final long serialVersionUID = 1L;

	public ComponentNameRequiredException() {
		super("Nome: campo obrigatório");
	}
	
}
