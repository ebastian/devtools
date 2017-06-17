package br.com.devtools.apidevtools.resource.component.version.build.rules.exceptions;

import br.com.devtools.apidevtools.core.rest.RestException;

public class BuildUniqueException extends RestException {

	private static final long serialVersionUID = 1L;

	public BuildUniqueException() {
		super("Já existe essa Build para esta Versão.");
	}
	
}
