package br.com.devtools.apidevtools.resource.component.version.build.rules.exceptions.creation;

import br.com.devtools.apidevtools.core.rest.RestException;

public class BuildCreationRequiredException extends RestException {

	private static final long serialVersionUID = 1L;

	public BuildCreationRequiredException() {
		super("Data de Criação: campo obrigatório");
	}
	
}
