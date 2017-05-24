package br.com.devtools.apidevtools.core.controller;

public class Filter {

	private String name;
	private Object value;
	
	public Filter() {
	}
	
	public Filter(String name, Object value) {
		this.setName(name);
		this.setValue(value);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	
}
