package br.com.devtools.apidevtools.core.rest;

public class MessageError {

	private String mensagem;

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public MessageError() {
	}

	public MessageError(String mensagem) {
		super();
		this.mensagem = mensagem;
	}
	
}
