package br.com.devtools.apidevtools.resource.component.version.rules.exception.release;

import br.com.devtools.apidevtools.core.rest.RestException;

public class VersionReleaseInvalidException extends RestException {

	private static final long serialVersionUID = 1L;

	public VersionReleaseInvalidException() {
		super("Release: valor inválido");
	}
	
}
