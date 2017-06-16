package br.com.devtools.apidevtools.resource.component.version;

import java.time.LocalDateTime;

public class TestVersionFactory {

private Version v;
	
	public TestVersionFactory() {
		v = new Version();
	}
	
	public TestVersionFactory createValid() {
		this.v.setMajor(1);
		this.v.setMinor(1);
		this.v.setRelease(1);
		this.v.setCreation(LocalDateTime.of(1986, 8, 17, 8, 0));
		this.v.setDescription("Teste de descrição");
		
		return this;
	}
	
	public TestVersionFactory creationNull() {
		v.setCreation(null);
		return this;
	}
	
	public TestVersionFactory creationGreaterNow() {
		v.setCreation(LocalDateTime.now().plusDays(1));
		return this;
	}
	
	public TestVersionFactory majorNull() {
		v.setMajor(null);
		return this;
	}

	public TestVersionFactory majorInvalid1() {
		v.setMajor(-1);
		return this;
	}

	public TestVersionFactory minorNull() {
		v.setMinor(null);
		return this;
	}

	public TestVersionFactory minorInvalid1() {
		v.setMinor(-1);
		return this;
	}
	
	public TestVersionFactory releaseNull() {
		v.setRelease(null);
		return this;
	}

	public TestVersionFactory releaseInvalid1() {
		v.setRelease(-1);
		return this;
	}
	
	
	public Version get() {
		return this.v;
	}
	
}
