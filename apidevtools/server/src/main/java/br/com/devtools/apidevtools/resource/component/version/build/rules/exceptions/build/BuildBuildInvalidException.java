package br.com.devtools.apidevtools.resource.component.version.build.rules.exceptions.build;

import br.com.devtools.apidevtools.core.rest.RestException;

public class BuildBuildInvalidException extends RestException {

	private static final long serialVersionUID = 1L;

	public BuildBuildInvalidException() {
		super("Build: valor inválido");
	}
	
}
