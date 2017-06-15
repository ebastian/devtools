package br.com.devtools.apidevtools.resource.component.rules.exception.creation;

import br.com.devtools.apidevtools.core.rest.RestException;

public class ComponentCreationGreaterNowException extends RestException {

	private static final long serialVersionUID = 1L;

	public ComponentCreationGreaterNowException() {
		super("Data de Criação: deve ser menor que data atual.");
	}
	
}
