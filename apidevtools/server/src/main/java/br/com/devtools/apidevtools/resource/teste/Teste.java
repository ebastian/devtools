package br.com.devtools.apidevtools.resource.teste;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Teste {
	
	@JsonIgnore
	public final String msgHelloWorld = "Hello World. Welcome to the ApiDevTools";
	
	private Long id;
	private String description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
