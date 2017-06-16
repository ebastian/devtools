package br.com.devtools.apidevtools.resource.component.version.rules.exception.major;

import br.com.devtools.apidevtools.core.rest.RestException;

public class VersionMajorRequiredException extends RestException {

	private static final long serialVersionUID = 1L;

	public VersionMajorRequiredException() {
		super("Major: campo obrigatório");
	}
	
}
