package br.com.devtools.apidevtools.resource.component.rules.exception;

import br.com.devtools.apidevtools.core.rest.RestException;

public class ComponentNameLessException extends RestException {

	private static final long serialVersionUID = 1L;

	public ComponentNameLessException() {
		super("Nome: tamanho deve ser maior que três");
	}
	
}
