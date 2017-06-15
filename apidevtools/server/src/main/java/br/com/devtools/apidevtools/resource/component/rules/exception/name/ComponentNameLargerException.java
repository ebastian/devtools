package br.com.devtools.apidevtools.resource.component.rules.exception.name;

import br.com.devtools.apidevtools.core.rest.RestException;

public class ComponentNameLargerException extends RestException {

	private static final long serialVersionUID = 1L;

	public ComponentNameLargerException() {
		super("Nome: tamanho deve ser menor que cem");
	}
	
}
