package br.com.devtools.apidevtools.resource.component.rules.exception.filename;

import br.com.devtools.apidevtools.core.rest.RestException;

public class ComponentFileNameLargerException extends RestException {

	private static final long serialVersionUID = 1L;

	public ComponentFileNameLargerException() {
		super("Nome do Arquivo: tamanho deve ser menor que cem");
	}
	
}
