package br.com.devtools.apidevtools.resource.component.rules.exception.creation;

import br.com.devtools.apidevtools.core.rest.RestException;

public class ComponentCreationRequiredException extends RestException {

	private static final long serialVersionUID = 1L;

	public ComponentCreationRequiredException() {
		super("Data de Criação: campo obrigatório");
	}
	
}
