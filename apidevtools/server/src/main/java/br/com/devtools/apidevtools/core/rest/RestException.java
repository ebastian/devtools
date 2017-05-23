package br.com.devtools.apidevtools.core.rest;

public class RestException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public RestException() {
	}
	
	public RestException(Throwable e) {
		super(e.getMessage());
	}
	public RestException(Exception e) {
		super(e.getMessage());
	}
	
	public RestException(String erro) {
		super(erro);
	}

}
