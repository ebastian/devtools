package br.com.devtools.apidevtools.resource.component.version.rules.exception.minor;

import br.com.devtools.apidevtools.core.rest.RestException;

public class VersionMinorRequiredException extends RestException {

	private static final long serialVersionUID = 1L;

	public VersionMinorRequiredException() {
		super("Minor: campo obrigatório");
	}
	
}
