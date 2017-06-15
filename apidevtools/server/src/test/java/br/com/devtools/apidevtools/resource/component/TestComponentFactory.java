package br.com.devtools.apidevtools.resource.component;

import java.time.LocalDateTime;

public class TestComponentFactory {
	
	private Component c;
	
	public TestComponentFactory() {
		c = new Component();
	}
	
	public TestComponentFactory createValid() {
		c.setName("Component Test");
		c.setCreation(LocalDateTime.of(1986, 8, 17, 8, 00));
		c.setDescription("Compnent Description Test");
		c.setFileName("ComponentTest.txt");
		return this;
	}
	
	public TestComponentFactory nameNull() {
		c.setName(null);
		return this;
	}
	
	public TestComponentFactory nameLess() {
		c.setName("a");
		return this;
	}
	
	public TestComponentFactory nameLager() {
		c.setName("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901");
		return this;
	}
	
	public TestComponentFactory fileNameNull() {
		c.setFileName(null);
		return this;
	}
	
	public TestComponentFactory fileNameLess() {
		c.setFileName("a");
		return this;
	}
	
	public TestComponentFactory fileNameLager() {
		c.setFileName("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901");
		return this;
	}
	
	public TestComponentFactory fileNameInvalid1() {
		c.setFileName("Teste");
		return this;
	}
	
	public TestComponentFactory fileNameInvalid2() {
		c.setFileName("Teste..txt");
		return this;
	}
	
	public TestComponentFactory fileNameInvalid3() {
		c.setFileName(".txt");
		return this;
	}
	
	public TestComponentFactory creationNull() {
		c.setCreation(null);
		return this;
	}
	
	public TestComponentFactory creationGreaterNow() {
		c.setCreation(LocalDateTime.now().plusDays(1));
		return this;
	}
	
	
	public Component get() {
		return this.c;
	}
	
}
