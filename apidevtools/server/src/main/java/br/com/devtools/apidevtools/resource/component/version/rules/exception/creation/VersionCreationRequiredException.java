package br.com.devtools.apidevtools.resource.component.version.rules.exception.creation;

import br.com.devtools.apidevtools.core.rest.RestException;

public class VersionCreationRequiredException extends RestException {

	private static final long serialVersionUID = 1L;

	public VersionCreationRequiredException() {
		super("Data de Criação: campo obrigatório");
	}
	
}
