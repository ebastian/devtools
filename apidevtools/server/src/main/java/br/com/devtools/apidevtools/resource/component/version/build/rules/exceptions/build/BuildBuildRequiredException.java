package br.com.devtools.apidevtools.resource.component.version.build.rules.exceptions.build;

import br.com.devtools.apidevtools.core.rest.RestException;

public class BuildBuildRequiredException extends RestException {

	private static final long serialVersionUID = 1L;

	public BuildBuildRequiredException() {
		super("Build: campo obrigatório");
	}
	
}
