package br.com.devtools.apidevtools.resource.component.rules.exception.filename;

import br.com.devtools.apidevtools.core.rest.RestException;

public class ComponentFileNameInvalidException extends RestException {

	private static final long serialVersionUID = 1L;

	public ComponentFileNameInvalidException() {
		super("Nome do Arquivo: formatação inválida. Ex.: Teste.txt");
	}
	
}
