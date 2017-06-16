package br.com.devtools.apidevtools.resource.component.version.rules.exception.minor;

import br.com.devtools.apidevtools.core.rest.RestException;

public class VersionMinorInvalidException extends RestException {

	private static final long serialVersionUID = 1L;

	public VersionMinorInvalidException() {
		super("Minor: valor inválido");
	}
	
}
