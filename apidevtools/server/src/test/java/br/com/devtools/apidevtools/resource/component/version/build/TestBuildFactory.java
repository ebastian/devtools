package br.com.devtools.apidevtools.resource.component.version.build;

import java.time.LocalDateTime;

public class TestBuildFactory {

private Build b;
	
	public TestBuildFactory() {
		b = new Build();
	}
	
	public TestBuildFactory createValid() {
		
		this.b.setCreation(LocalDateTime.of(1986, 8, 17, 8, 0));
		this.b.setBuild(1);
		this.b.setNotes("Teste de descrição");
		
		return this;
	}
	
	public TestBuildFactory creationNull() {
		b.setCreation(null);
		return this;
	}
	
	public TestBuildFactory creationGreaterNow() {
		b.setCreation(LocalDateTime.now().plusDays(1));
		return this;
	}
	
	public TestBuildFactory buildNull() {
		b.setBuild(null);
		return this;
	}

	public TestBuildFactory buildInvalid() {
		b.setBuild(-1);
		return this;
	}
	
	public Build get() {
		return this.b;
	}
	
}
