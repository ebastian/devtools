package br.com.devtools.apidevtools.resource.component.version.rules.exception;

import br.com.devtools.apidevtools.core.rest.RestException;

public class VersionUniqueException extends RestException {

	private static final long serialVersionUID = 1L;

	public VersionUniqueException() {
		super("Já existe esta Versão para esse Componente.");
	}
	
}
