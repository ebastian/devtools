package br.com.devtools.apidevtools.resource.component.version.build.rules.exceptions.creation;

import br.com.devtools.apidevtools.core.rest.RestException;

public class BuildCreationGreaterNowException extends RestException {

	private static final long serialVersionUID = 1L;

	public BuildCreationGreaterNowException() {
		super("Data de Criação: deve ser menor que data atual.");
	}
	
}
