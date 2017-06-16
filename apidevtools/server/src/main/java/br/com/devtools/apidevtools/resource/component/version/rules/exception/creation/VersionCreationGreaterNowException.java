package br.com.devtools.apidevtools.resource.component.version.rules.exception.creation;

import br.com.devtools.apidevtools.core.rest.RestException;

public class VersionCreationGreaterNowException extends RestException {

	private static final long serialVersionUID = 1L;

	public VersionCreationGreaterNowException() {
		super("Data de Criação: deve ser menor que data atual.");
	}
	
}
