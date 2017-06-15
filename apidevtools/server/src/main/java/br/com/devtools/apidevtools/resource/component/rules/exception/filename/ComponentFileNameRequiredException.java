package br.com.devtools.apidevtools.resource.component.rules.exception.filename;

import br.com.devtools.apidevtools.core.rest.RestException;

public class ComponentFileNameRequiredException extends RestException {

	private static final long serialVersionUID = 1L;

	public ComponentFileNameRequiredException() {
		super("Nome do Arquivo: campo obrigatório");
	}
	
}
