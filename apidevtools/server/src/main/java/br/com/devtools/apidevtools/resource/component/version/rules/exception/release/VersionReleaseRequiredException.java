package br.com.devtools.apidevtools.resource.component.version.rules.exception.release;

import br.com.devtools.apidevtools.core.rest.RestException;

public class VersionReleaseRequiredException extends RestException {

	private static final long serialVersionUID = 1L;

	public VersionReleaseRequiredException() {
		super("Release: campo obrigatório");
	}
	
}
