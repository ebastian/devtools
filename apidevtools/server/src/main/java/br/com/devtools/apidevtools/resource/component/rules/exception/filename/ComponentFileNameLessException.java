package br.com.devtools.apidevtools.resource.component.rules.exception.filename;

import br.com.devtools.apidevtools.core.rest.RestException;

public class ComponentFileNameLessException extends RestException {

	private static final long serialVersionUID = 1L;

	public ComponentFileNameLessException() {
		super("Nome do Arquivo: tamanho deve ser maior que três");
	}
	
}
